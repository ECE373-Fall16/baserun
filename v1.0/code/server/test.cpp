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
  cout<<"  Verifying that game has correct number of bases"<<endl;
  int numBases = game->getNumBases();
  if (numBases != 8) {
    cout<<"  ERROR: The returned number of bases for the game is incorrect\n"<<endl;
    cout<<"    Expected: "<<8;
    cout<<", Actual: "<<numBases<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  cout<<"\n  Verifying that game has correct gameplay radius"<<endl;
  double radius = game->getRadius();
  if (radius != 0.25) {
    cout<<"  ERROR: The returned radius for the game is incorrect\n"<<endl;
    cout<<"    Expected: "<<0.25;
    cout<<", Actual: "<<radius<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  // Verify that first player was added on create
  cout<<"\n  Verifying that player 1 was added to team 1 on game creation"<<endl;
  int teamNumOne = game->getPTeamNum(0);
  if (teamNumOne != 1) {
    cout<<"  ERROR: Failed to create and add player 1 to team 1\n"<<endl;
    cout<<"    Expected: "<<1;
    cout<<", Actual: "<<teamNumOne<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  // Add 3 more players to game
  cout<<"\n  Adding three more players to the game\n"<<endl;
  game->init_Player(1);
  game->init_Player(2);
  game->init_Player(3);
  cout<<"  Getting the two teams from the current game\n"<<endl;
  // Get teams from game
  team* teamOne = game->getTeam(teamNumOne);
  team* teamTwo = game->getTeam(teamNumOne+1);

  cout<<"  Verifying that team 1 only has two players"<<endl;
  int numPlayersTeamOne = teamOne->getNumPlayers();
  if (numPlayersTeamOne != 2) {
    cout<<"  ERROR: Failed to add player 3 to the team 1\n"<<endl;
    cout<<"    Expected: "<<2;
    cout<<", Actual: "<<numPlayersTeamOne<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  cout<<"\n  Verifying that team 2 only has two players"<<endl;
  int numPlayersTeamTwo = teamTwo->getNumPlayers();
  if (numPlayersTeamTwo != 2) {
    cout<<"  ERROR: Failed to add players 2 and 4 to team 2\n"<<endl;
    cout<<"    Expected: "<<2;
    cout<<", Actual: "<<numPlayersTeamTwo<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }

  // Have player one capture a base
  cout<<"\n  Capturing first base with player 1"<<endl;
  int capture = game->conquerBase(teamNumOne, 0, 0.000284279, 0.000284279);
  if (capture != 1) {
    cout<<"  ERROR: Failed to capture first base with exact coordinates\n"<<endl;
    cout<<"    Expected: "<<1;
    cout<<", Actual: "<<capture<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  cout<<"\n  Getting team 1's score and verifying"<<endl;
  int score = teamOne->getTeamScore();
  if (score != 1) {
    cout<<"  ERROR: Failed to update team's score\n"<<endl;
    cout<<"    Expected: "<<1;
    cout<<", Actual: "<<score<<endl;
    cout<<"FAILURE"<<endl;
    exit(1);
  }
  cout<<"\nSUCCESS"<<endl;
}
