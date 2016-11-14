#ifndef team_class_h
#include "team-class.h"
#define team_class_h
#endif

team::team(){
	char color = 'n';
	numPlayers = 0;
	maxPlayers = 0;
}

team::team(int maxP){
	char color = 'n';
	numPlayers = 0;
	maxPlayers = maxP;
}

team::~team(){}

void team::addPlayer(player_t p){
	if (numPlayers<maxPlayers){
		playerArr[numPlayers++] = p;
	} 
}

int team::getTeamScore(){
	return teamScore;	
}

player_t* team::getPlayer(long int pid){
	int i;
	for(i=0;i<numPlayers;i++){
		if (playerArr[i].id == pid){
			return &(playerArr[i]);
		}
	}
//	return nullptr;
	return &playerArr[0];

}

char team::getColor(){return color;}

player_t* team::getPlayerArr(){
	return playerArr;
}

int team::getNumPlayers(){return numPlayers;}

void team::setMaxPlayers(int maxN){
	maxPlayers = maxN;
}

void team::setColor(char c){color = c;}

void team::setTeamNum(int n){tmNum = n;}

int team::getTeamNum(){return tmNum;}

bool team::playerThere(long int pid){
	for(int i=0; i<numPlayers; i++){
		if(playerArr[i].id == pid){
			return true;
		}
	}
	return false;
}

void team::playerScore(long int pid){
	int i;
	for(i=0;i<numPlayers;i++){
		if(playerArr[i].id==pid){
			playerArr[i].points++;
		}
	}
}