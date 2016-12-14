//header w/o gaurd
#include <cassert>
#include <stdexcept>
#include <iostream>
#include <unistd.h>
#include <string>
#include <iomanip>

//header gaurds
#ifndef sessionList_class_h
#define sessionList_class_h
#include "sessionList-class.h"
#endif

#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

#ifndef session_structs_h
#define sessions_structs_h
#include "session-structs.h"
#endif

#ifndef team_class_h
#define team_class_h
#include "team-class.h"
#endif

#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

using namespace std;

int main () {
  cout<<"Beginning team_class tests..."<<endl;
  cout<<"Creating team with the following number of players: ";
  team testTeam(4);
  player_t *pl = new player_t;
  pl->id = 1;
  pl->points = 0;
  pl->baseN = -1;

  testTeam.addPlayer(*pl);

  int testOne = testTeam.getNumPlayers();
  if (testOne != 1) {
    cout<<"getNumPlayers returned incrorrect value."<<endl;
    cout<<"    Expected: "<<1<<", Actual: "<<testOne<<endl;
  }
  delete pl;
  cout<<"SUCCESS: team_class tests finished."<<endl;
}
