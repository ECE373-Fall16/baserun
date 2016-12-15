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
#include "sqdata.h"
#include <string>
using namespace std;

// tests the functions in the header file to make sure they are working correctly
int main(){
   return_structure fire;
   array_struct tease;
   fire = query(4);
   tease = convert(fire);
  // test that the first struct is returned properly in the correct format by printing to the command line the contents
   for(int j = 0; j < fire.count_done; j++) {
        cout << "BASE_NUMBER = " << fire.number[j] << endl;
       cout << "NORMALIZED_RADIUS = " << fire.radii[j] << endl;
       cout << "ANGLE = " << fire.angle[j] << endl;
     } 

// test that the first struct is properly converted properly and returned in the correct format by printing to the command line the contents
     for(int i = 0; i < tease.count_done1; i++) {
        cout << "BASE_NUMBER = " << tease.mapnumber[i] << endl;
       cout << "NORMALIZED_RADIUS = " << tease.radius[i] << endl;
       cout << "ANGLE = " << tease.ANGLE[i] << endl;
     } 
}