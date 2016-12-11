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

static int callback(void* data, int argc, char **argv, char **azColName) 
{
   for(int i=0; i<argc; i++){
      printf("%s = %s\n", azColName[i], argv[i]);
   }
   printf("\n");

   return 0;
}

int main(int argc, char* argv[]){
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

   rc = sqlite3_open("baselayouts.db", &db);
   if( rc ){
      fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
      exit(0);
   }else{
      fprintf(stderr, "Opened database successfully\n");
   }

   sql = "CREATE TABLE BASELAYOUTS("  \
         "BASE_NUMBER INT NOT NULL," \
         "NORMALIZED_RADIUS DOUBLE NOT NULL," \
	 	   "ANGLE DOUBLE NOT NULL)"; 

	rc = sqlite3_exec(db, sql, NULL, 0, &zErrMsg);
   if( rc != SQLITE_OK ){
   fprintf(stderr, "SQL error: %s\n", zErrMsg);
      sqlite3_free(zErrMsg);
   }else{
      fprintf(stdout, "Table created successfully\n");
   }

// baselayout 1-5 for 4 bases
   
   ostringstream convert;
   ostringstream s;
   ostringstream t;
   anglescalar = 360/4;
   for (int i = 1; i < 6; i++){
      ang = i*10;
      for (int j = 0; j <4; j++){
         randomnumber = rand() % 10000000;
         y = (double) randomnumber * .0000001;
         if ((ang+anglescalar) >= 360){
         ang = ang+anglescalar-360;
   }
   else ang = ang+anglescalar;
         //cout << y << " " << ang << endl;
         convert << y;
         radius = convert.str();
         s << ang;
         angle = s.str();
         //cout << radius << " " << angle << endl;
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
         //cout << basedata << endl;
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

//baselayout 6-11 for 8 bases
   anglescalar = 360/8;
   for (int i = 6; i < 12; i++){
      ang = i*10;
      for (int j = 0; j <8; j++){
         randomnumber = rand() % 10000000;
         y = (double) randomnumber * .0000001;
         if ((ang+anglescalar) >= 360){
         ang = ang+anglescalar-360;
   }
   else ang = ang+anglescalar;
         //cout << y << " " << ang << endl;
         convert << y;
         radius = convert.str();
         s << ang;
         angle = s.str();
         //cout << radius << " " << angle << endl;
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
         //cout << basedata << endl;
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
   sqlite3_close(db);
}