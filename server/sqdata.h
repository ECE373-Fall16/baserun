//sqdata.h file
#ifndef _sqdata_h
#define _sqdata_h
#endif

#include <string>

struct return_structure {
  std::string number[2048];
  std::string radii[2048];
  std::string angle[2048];  
  int temp_row_count;
  int count_done;
};

struct array_struct{
double mapnumber[2048];   
double radius[2048];
double ANGLE[2048];
int temp_row_count1;
int count_done1;
};

struct array_struct convert(return_structure);
static int callback(void*, int, char **, char **);
static int select_callback(void*, int, char **, char **);
static int count_callback(void*, int, char **, char **);
struct return_structure query(int);


