#include "team-class.h"

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

player* team::getPlayer(long int pid){
	int i;
	for(i=0;i<numPlayers;i++){
		if (playerArr[i].id == pid){
			return &(playerArr[i])
		}
	}
	return NULL;
}

char team::getColor(){return color;}

player_t* team::getPlayerArr(){
	return playerArr
}

int team::getNumPlayers(){return numPlayers;}

void team::setMaxPlayers(int maxN){
	maxPlayers = maxN;
}

void team::setColor(char c){color = c;}

void team::setTeamNum(int n){tmNum = n;}

int team::getTeamNum(){return tmNum;}

void team::playerScore(long int pid){
	int i;
	for(i=0;i<numPlayers;i++){
		if(playerArr[i]==pid){
			playerArr[i].points++;
		}
	}
}
