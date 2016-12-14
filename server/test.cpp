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
  cout<<"\nBeginning team_class tests...\n"<<endl;
  cout<<"Creating first test team with the following number of players: ";
  //Create a test team
  team testTeamOne(4);
  cout<<"\nCreating second test team with the following number of players: ";
  team testTeamTwo(4);
  // Create a two test players
  cout<<"\nCreating four test players\n"<<endl;
  player_t *testPlayerOne = new player_t;
  player_t *testPlayerTwo = new player_t;
  player_t *testPlayerThree = new player_t;
  player_t *testPlayerFour = new player_t;
  testPlayerOne->id = 1;
  testPlayerOne->points = 0;
  testPlayerOne->baseN = -1;
  testPlayerTwo->id = 2;
  testPlayerTwo->points = 0;
  testPlayerTwo->baseN = -1; // Is this right?
  testPlayerThree->id = 3;
  testPlayerThree->points = 0;
  testPlayerThree->baseN = -1; // Is this right?
  testPlayerFour->id = 4;
  testPlayerFour->points = 0;
  testPlayerFour->baseN = -1; // Is this right?
  // Add test player one to test team one
  cout<<"Adding first test player to first test team and verifying"<<endl;
  testTeamOne.addPlayer(*testPlayerOne);
  if (testTeamOne.getNumPlayers() != 1) {
    int result = testTeamOne.getNumPlayers();
    cout<<"ERROR: getNumPlayers of testTeamOne returned incorrect value."<<endl;
    cout<<"    Expected: "<<1;
    cout<<", Actual: "<<result<<endl;
    exit(1);
  }
  // Add the rest of the players to teams
  cout<<"\nAdding remaining test players amongst the two test teams and verifying"<<endl;
  testTeamTwo.addPlayer(*testPlayerTwo);
  testTeamOne.addPlayer(*testPlayerThree);
  testTeamTwo.addPlayer(*testPlayerFour);
  if (testTeamOne.getNumPlayers() != 2) {
    int result = testTeamOne.getNumPlayers();
    cout<<"ERROR: getNumPlayers of testTeamOne returned incorrect value"<<endl;
    cout<<"    Expected: "<<2;
    cout<<", Actual: "<<result<<endl;
    exit(1);
  }
  if (testTeamTwo.getNumPlayers() != 2) {
    int result = testTeamOne.getNumPlayers();
    cout<<"ERROR: getNumPlayers of testTeamTwo returned incorrect value"<<endl;
    cout<<"    Expected: "<<2;
    cout<<", Actual: "<<result<<endl;
    exit(1);
  }
  cout<<"\nDeleting test players from memory\n"<<endl;
  delete testPlayerOne;
  delete testPlayerTwo;
  delete testPlayerThree;
  delete testPlayerFour;
  cout<<"SUCCESS"<<endl;
}
