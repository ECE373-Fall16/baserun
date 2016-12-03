#include <iostream>

#ifndef team_class_h
#include "team-class.h"
#define team_class_h
#endif

using namespace std;

//constructor for team with no input parameters
team::team(){
	char color = 'n';
	numPlayers = 0;
	maxPlayers = 0;
}

//constructor for team with parameter max players
team::team(int maxP){
	cout<<maxP<<endl;
	char color = 'n';
	numPlayers = 0;
	maxPlayers = maxP;
	playerArr = new player_t[maxPlayers];
}

//destructor
team::~team(){}

//adds a player to the team
void team::addPlayer(player_t p){
	if (numPlayers<maxPlayers){
		playerArr[numPlayers++] = p;
	} 
}

//returns total score of the team
int team::getTeamScore(){
	return teamScore;	
}

//returns player when given their player id
player_t* team::getPlayer(long int pid){
	int i;
	for(i=0;i<numPlayers;i++){
		if (playerArr[i].id == pid){
			return &(playerArr[i]);
		}
	}
	//if not found, returns nullptr
	return nullptr;
}

//returns the team color
char team::getColor(){return color;}

//returns ptr to first player in the team
player_t* team::getPlayerArr(){
	return playerArr;
}

//returns number of players
int team::getNumPlayers(){return numPlayers;}

//set maximum # of players
void team::setMaxPlayers(int maxN){
	maxPlayers = maxN;
}

//sets team color
void team::setColor(char c){color = c;}

//sets team number
void team::setTeamNum(int n){tmNum = n;}

//returns team number
int team::getTeamNum(){return tmNum;}

//checks if player exists
bool team::playerThere(long int pid){
	for(int i=0; i<numPlayers; i++){
		if(playerArr[i].id == pid){
			return true;
		}
	}
	return false;
}

//adds a point to the player whose pid is passed
void team::playerScore(long int pid){
	int i;
	for(i=0;i<numPlayers;i++){
		if(playerArr[i].id==pid){
			playerArr[i].points++;
		}
	}
}
