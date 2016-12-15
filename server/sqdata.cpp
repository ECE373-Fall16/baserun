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


// function that converts the returned struct to a struct containing array that can be used by the server easily
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
   return_value->temp_row_count++;
   pthread_mutex_unlock(&callback_mutex);

   return 0;
}
 //callback function designed to return the number of columns so that we know how many times we need to call the select callback function
static int count_callback(void* data, int argc, char **argv, char **azColName) 
{
   pthread_mutex_lock(&count_mutex);
   return_structure* return_value = (return_structure*)data;
   return_value->count_done = atoi(argv[0]);
   pthread_mutex_unlock(&count_mutex);

   return 0;
 }
// function that is passed the map number and returns associated map
return_structure query(int here){

// Initializing the mutex locks
   pthread_mutex_init(&count_mutex, NULL);
   pthread_mutex_init(&callback_mutex, NULL);


return_structure return_value;

   sqlite3 *db;
   char *zErrMsg = 0;
   int rc;
   const char* sql;
   string sql_string;  
   const char* data = "Callback function called";
// takes the integer that was passed and converts it into a string that can be converted to a character array so that it can be executed as sql statements
   ostringstream z;
   z << here;
   string c = z.str();
   string d = "SELECT * from BASELAYOUTS where BASE_NUMBER = " + c;
   c = "SELECT COUNT(*) from BASELAYOUTS where BASE_NUMBER = " + c;
   sql = c.c_str();
 //   open the datatbase
rc = sqlite3_open("baselayouts.db", &db);
   if( rc ){
      fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
      exit(0);
   }else{
      fprintf(stderr, "Opened database successfully\n");
   }
// figure out how many times we need to query from the database
   return_value.count_done = -1;
   rc = sqlite3_exec(db, sql, count_callback, (void*)&return_value, &zErrMsg);
   if( rc != SQLITE_OK ){
      fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }
// Wait until the count_callback function from above is finished executing. We 
   // loop until count_done is not -1.  count_done stores the total number of rows we
   // expect from our SQL query. Note here that we need to hold the lock, since the 
   // callback function is also modifying count_done.
   while (1) {
     pthread_mutex_lock(&count_mutex);
     if (return_value.count_done != -1) {
   break;
     }
     pthread_mutex_unlock(&count_mutex);
   }
// We now execute our SQL query from above. We give it a separate callback function, and pass in
   // a pointer to the same structure as before. Below, we wait until the callback function has been
   // called for each row expected by the query.
return_value.temp_row_count = 0;
   sql = d.c_str();
   rc = sqlite3_exec(db, sql, select_callback, (void*)&return_value, &zErrMsg);
   if( rc != SQLITE_OK ){
      fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }
// The select_callback function above increments temp_row_count each time it is called. Once temp_row_count
   // equals count_done, we know that the select_callback function has been called the expected number of 
   // times, and we can move on. We need to hold the lock here, since the callback functions are modifying
   // temp_row_count.
while (1) {
     pthread_mutex_lock(&callback_mutex);
     if (return_value.temp_row_count == return_value.count_done) {
   break;
     }
     pthread_mutex_unlock(&callback_mutex);
   }

//we close the database then return our struct to be converted and used by the server
   sqlite3_close(db);
   return return_value;
}



