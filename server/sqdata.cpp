#include <iostream>
#include <cstdlib>
#include <cstring>
#include <sstream>
#include <cstddef>
#include <stdio.h>
#include <stdlib.h>
#include <sqlite3.h> 
#include <pthread.h>
#include <time.h>
#include <float.h>
#include <string>
#include "sqdata.h"

using namespace std;

pthread_mutex_t count_mutex;
pthread_mutex_t callback_mutex;

array_struct convert(return_structure fun){
      array_struct water;
      water.temp_row_count1 = fun.temp_row_count;
      water.count_done1 = fun.count_done;
 for (int k = 0; k<water.count_done1; k++){
      stringstream ab(fun.number[k]);
      stringstream ac(fun.radii[k]);
      stringstream ad(fun.angle[k]);
      ab >> water.mapnumber[k]; 
      ac >> water.radius[k]; 
      ad >> water.ANGLE[k]; 
 }
 return water;
}

//callback function
static int callback(void* data, int argc, char **argv, char **azColName) 
{
   for(int i=0; i<argc; i++){
      printf("%s = %s\n", azColName[i], argv[i]);
   }
   printf("\n");

   return 0;
}
// a callback for a SELECT SQL query 
// Here, we pass in the structure above, and get the column values for basenumber, radius
// and angle and put them in the arrays that we want to return. 
// We also increment the temp_row_count by 1 because we need to keep track of the number of times
// this function has been called; in the main thread, we wait until the function
// has been called once for each row we expect. We need to hold a lock here, since
// each callback is on a separate thread, and each thread will modify these shared
// variables.
static int select_callback(void* data, int argc, char **argv, char **azColName) 
{
   pthread_mutex_lock(&callback_mutex);
   return_structure* return_value = (return_structure*)data;
   for(int i=0; i<argc; i++){
     // printf("%s = %s\n", azColName[i], argv[i]);
      if (strncmp(azColName[i],"BASE_NUMBER",11) == 0) {
   return_value->number[return_value->temp_row_count]=argv[i];  
      }
      if (strncmp(azColName[i],"NORMALIZED_RADIUS",11) == 0) {
   return_value->radii[return_value->temp_row_count]=argv[i];  
      }
      if (strncmp(azColName[i],"ANGLE",5) == 0) {
   return_value->angle[return_value->temp_row_count]=argv[i]; 
      }
   }
  // printf("\n");
   return_value->temp_row_count++;
   pthread_mutex_unlock(&callback_mutex);

   return 0;
}

static int count_callback(void* data, int argc, char **argv, char **azColName) 
{
   pthread_mutex_lock(&count_mutex);
   return_structure* return_value = (return_structure*)data;
   return_value->count_done = atoi(argv[0]);
   pthread_mutex_unlock(&count_mutex);

   return 0;
 }

return_structure query(int here){


return_structure return_value;

   sqlite3 *db;
   char *zErrMsg = 0;
   int rc;
   const char* sql;
   string sql_string;  
   const char* data = "Callback function called";

   ostringstream z;
   z << here;
   string c = z.str();
   string d = "SELECT * from BASELAYOUTS where BASE_NUMBER = " + c;
   c = "SELECT COUNT(*) from BASELAYOUTS where BASE_NUMBER = " + c;
   sql = c.c_str();
   
rc = sqlite3_open("baselayouts.db", &db);
   if( rc ){
      fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
      exit(0);
   }else{
      fprintf(stderr, "Opened database successfully\n");
   }

   return_value.count_done = -1;
   //sql = "SELECT COUNT(*) from BASELAYOUTS WHERE BASE_NUMBER = 4";
   rc = sqlite3_exec(db, sql, count_callback, (void*)&return_value, &zErrMsg);
   if( rc != SQLITE_OK ){
      fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }

   while (1) {
     pthread_mutex_lock(&count_mutex);
     //cout << return_value.temp_row_count << endl;
     if (return_value.count_done != -1) {
   break;
     }
     pthread_mutex_unlock(&count_mutex);
   }

return_value.temp_row_count = 0;
   sql = d.c_str();
   //sql = "SELECT * from BASELAYOUTS WHERE BASE_NUMBER = 4";
   rc = sqlite3_exec(db, sql, select_callback, (void*)&return_value, &zErrMsg);
   if( rc != SQLITE_OK ){
      fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }

while (1) {
     pthread_mutex_lock(&callback_mutex);
     if (return_value.temp_row_count == return_value.count_done) {
   break;
     }
     pthread_mutex_unlock(&callback_mutex);
   }

    // Here, we just print out the results from the query that we stored in the select_callback function.
   // for(int i = 0; i < return_value.count_done; i++) {
   //    cout << "BASE_NUMBER = " << return_value.number[i] << endl;
   //   cout << "NORMALIZED_RADIUS = " << return_value.radii[i] << endl;
   //   cout << "ANGLE = " << return_value.angle[i] << endl;
   // } 

   sqlite3_close(db);
   return return_value;
}




/*
   ostringstream z;
   z << 6;
   string c = z.str();
   c = "SELECT * from BASELAYOUTS where BASE_NUMBER = " + c;
   sql = c.c_str();
  Execute SQL statement 
   rc = sqlite3_exec(db, sql, callback, (void*)data, &zErrMsg);
   if( rc != SQLITE_OK ){
      fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }else{
      fprintf(stdout, "Operation done successfully\n");
   }
   sqlite3_close(db);
   return 0;

}
*/

