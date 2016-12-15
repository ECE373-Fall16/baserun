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

using namespace std;

//callback function; prints out each column value for
// the row associated with this callback function
// void* data = pointer from the sql statement that identifies this callback
// int argc = the number of columns in each row
// char** argv = array of strings, one for each column field value
// char** azColName = array of strings, one for each column name

static int callback(void* data, int argc, char **argv, char **azColName) 
{
   for(int i=0; i<argc; i++){
      printf("%s = %s\n", azColName[i], argv[i]);
   }
   printf("\n");

   return 0;
}

int main(int argc, char* argv[]){
// variables needed to create our maps full of bases
int randomnumber;
double y;
double anglescalar;
double ang = 0;
double rad = 0;
string angle;
string radius;
string basedata;
string layout;

   srand ( time(NULL) );
  

   sqlite3 *db;
   char *zErrMsg = 0;
   int rc;
   const char* sql;
   string sql_string;  
   const char* data = "Callback function called";

//here we open the database in order to insert our values
   rc = sqlite3_open("baselayouts.db", &db);
   if( rc ){
      fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
      exit(0);
   }else{
      fprintf(stderr, "Opened database successfully\n");
   }
//sql statement that creates the table
   sql = "CREATE TABLE BASELAYOUTS("  \
         "BASE_NUMBER INT NOT NULL," \
         "NORMALIZED_RADIUS DOUBLE NOT NULL," \
	 	   "ANGLE DOUBLE NOT NULL)"; 
// executes the sql statement
	rc = sqlite3_exec(db, sql, NULL, 0, &zErrMsg);
   if( rc != SQLITE_OK ){
   fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }else{
      fprintf(stdout, "Table created successfully\n");
   }

// baselayout 1-5 for 4 bases
// randomly generates the maps using a unit circle as a guide for the map
   
   ostringstream convert;
   ostringstream s;
   ostringstream t;
   anglescalar = 360/4;
   // iterates to make 5 maps
   for (int i = 1; i < 6; i++){
      ang = i*10;
      // iterates to make 4 bases
      for (int j = 0; j <4; j++){
         randomnumber = rand() % 10000000;
         y = (double) randomnumber * .0000001;
          // makes sure angle is between 0 and 360
         if ((ang+anglescalar) >= 360){
         ang = ang+anglescalar-360;
   }
   else ang = ang+anglescalar;
   //makes the sql command a character array from a string so that it can be inserted
         convert << y;
         radius = convert.str();
         s << ang;
         angle = s.str();
         t << i;
         layout = t.str();
         basedata = layout + ", " + radius + ", " + angle + ");";
         basedata = "INSERT INTO BASELAYOUTS VALUES (" + basedata;
         convert.str("");
         s.str("");
         t.str("");
         convert.clear();
         s.clear();
         t.clear();

         // insert the base we randomly created
   sql = basedata.c_str();
   rc = sqlite3_exec(db, sql, callback, 0, &zErrMsg);
   if( rc != SQLITE_OK ){
     fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }else{
      fprintf(stdout, "Records created successfully\n");
   }

      }

   }

//baselayout 6-11 for 8 bases using same formula only with 8 bases now
   anglescalar = 360/8;
   //iterates to make 5 maps
   for (int i = 6; i < 12; i++){
      ang = i*10;
      //iterates to make 8 bases
      for (int j = 0; j <8; j++){
         randomnumber = rand() % 10000000;
         y = (double) randomnumber * .0000001;
         // makes sure angle is between 0 and 360
         if ((ang+anglescalar) >= 360){
         ang = ang+anglescalar-360;
   }
   else ang = ang+anglescalar;
   //makes the sql command a character array from a string so that it can be inserted
         convert << y;
         radius = convert.str();
         s << ang;
         angle = s.str();
         t << i;
         layout = t.str();
         basedata = layout + ", " + radius + ", " + angle + ");";
         basedata = "INSERT INTO BASELAYOUTS VALUES (" + basedata;
         convert.str("");
         s.str("");
         t.str("");
         convert.clear();
         s.clear();
         t.clear();
// insert each base
   sql = basedata.c_str();
   rc = sqlite3_exec(db, sql, callback, 0, &zErrMsg);
   if( rc != SQLITE_OK ){
     fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }else{
      fprintf(stdout, "Records created successfully\n");
   }

      }

   }
   // close the database
   sqlite3_close(db);
}