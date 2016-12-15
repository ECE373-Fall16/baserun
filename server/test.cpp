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
  cout<<"\nBeginning server tests...\n"<<endl;
  cout<<"  Creating a new sessionList and adding a new session with a 4-player maximum\n"<<endl;
  sessionList testSessionList;
  int GID = testSessionList.addSession(0,4);
  if (GID != 100) {
    cout<<""<<endl;
    exit(1);
  }
  cout<<"  Retrieving newly created session and verifying"<<endl;
  session *game = testSessionList.getSession(GID);

  if (game != nullptr) {
    cout<<"\n  Retrieved valid game session, setting radius, base number, start position, and base locations\n"<<endl;
    game->setRadius(0.25);
    game->setNumBases(8);
    game->setStart(0.0,0.0);
    game->generateLocationArray();
  }
  else {
    cout<<"  ERROR: Failed to create and retrieve a valid game session\n"<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  int numBases = game->getNumBases();
  if (numBases != 8) {
    cout<<"  ERROR: The returned number of bases for the game is incorrect\n"<<endl;
    cout<<"    Expected: "<<8;
    cout<<", Actual: "<<numBases<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  double radius = game->getRadius();
  if (radius != 0.25) {
    cout<<"  ERROR: The returned radius for the game is incorrect\n"<<endl;
    cout<<"    Expected: "<<0.25;
    cout<<", Actual: "<<radius<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  // Verify that first player was added on create
  int teamNumOne = game->getPTeamNum(0);
  if (teamNumOne != 1) {
    cout<<"  ERROR: Failed to create and add initial player to first team\n"<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  // Add 3 more players to game
  game->init_Player(1);
  game->init_Player(2);
  game->init_Player(3);
  // Get teams from game
  team* teamOne = game->getTeam(teamNumOne);
  team* teamTwo = game->getTeam(teamNumOne+1);

  int numPlayersTeamOne = teamOne->getNumPlayers();
  if (numPlayersTeamOne != 2) {
    cout<<"  ERROR: Failed to add the third player to team one\n"<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  int numPlayersTeamTwo = teamTwo->getNumPlayers();
  if (numPlayersTeamTwo != 2) {
    cout<<"  ERROR: Failed to add the second and fourth players to team two\n"<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }

  // if (testTeamOne.getNumPlayers() != 1) {
  //   int result = testTeamOne.getNumPlayers();
  //   cout<<"  ERROR: getNumPlayers of testTeamOne returned incorrect value."<<endl;
  //   cout<<"    Expected: "<<1;
  //   cout<<", Actual: "<<result<<endl;
  //   cout<<"\nFAILURE"<<endl;
  //   exit(1);
  // }
  // // Add the rest of the players to teams
  // cout<<"\n  Adding remaining test players amongst the two test teams and verifying"<<endl;
  //
  // if (testTeamOne.getNumPlayers() != 2) {
  //   int result = testTeamOne.getNumPlayers();
  //   cout<<"  ERROR: getNumPlayers of testTeamOne returned incorrect value"<<endl;
  //   cout<<"    Expected: "<<2;
  //   cout<<", Actual: "<<result<<endl;
  //   cout<<"\nFAILURE"<<endl;
  //   exit(1);
  // }
  // if (testTeamTwo.getNumPlayers() != 2) {
  //   int result = testTeamOne.getNumPlayers();
  //   cout<<"  ERROR: getNumPlayers of testTeamTwo returned incorrect value"<<endl;
  //   cout<<"    Expected: "<<2;
  //   cout<<", Actual: "<<result<<endl;
  //   cout<<"\nFAILURE"<<endl;
  //   exit(1);
  // }

  cout<<"SUCCESS"<<endl;
}
