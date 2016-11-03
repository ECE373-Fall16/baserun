#include "session-class.h"
#include "session-structs.h"
#include <cmath>
#include <iostream>

void session::generateLocationArray(){
	baseArr = new base_t[numBases];	
	double x,y;
	double theta = 2*3.1415/numBases;
	int i;
	for(i=0; i<numBases; i++){
		x = start_x+(i*radius*1.0/numBases)*cos(i*theta);			
		y = start_y+(i*radius*1.0/numBases)*sin(i*theta);
		addBase(base_t(x,y,'n',0));			
	}
}

void session::addBase(base_t b){
	static int baseIndex=0;
	baseArr[baseIndex++] = b;
}

void session::conquerBase(int tmNum, long int id, double x, double y){
	
	team *tm;
	if(team1.getTeamNum()==tmNum){
		tm = &team1;
	}else{tm = &team2;}

	int i;
	for(i=0;i<numBases;i++){
		if(baseArr[i].x==x && baseArr[i].y==y){
			if(tmNum==1){
				if(baseArr[i].score == 0){
					baseArr[i].setColor(tm->getColor());
					tm->playerScore(id);					
				}	
				baseArr[i].score++;	
			}else{			
				if(baseArr[i].score == 0){
					baseArr[i].setColor(tm->getColor());
					tm->playerScore(id);
				}
				baseArr[i].score--;
			}	
		}
	}

}

void session::init_Player(long int pid){
	if(numPlayers<maxGameSize){
		if(numPlayers%2==0){
			team1.addPlayer(pid);
		}else{
			team2.addPlayer(pid);
		}
	}
}

void session::setRadius(double rad){
	radius = rad;	
}

void session::setBaseRadius(double rad){
	baseRadius = rad;
}

int session::getNumPlayer(){return numPlayers;}

void session::setMaxPlayerSize(int n){maxGameSize=n;}

base_t* session::getBases(){return baseArr;}

team session::getTeam(int tmNum){
	if(team1.getTeamNum()==tmNum){return team1;
	}else{
		return team2;
	}
}

void session::restartGame(){}

