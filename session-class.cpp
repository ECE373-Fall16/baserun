#ifndef session_class_h
#include "session-class.h"
#define session_class_h
#endif


#ifndef session_structs_h
#include "session-structs.h"
#define session_structs_h
#endif

#ifndef math_h
#include <cmath>
#define session_structs_h
#endif

#ifndef io_h
#include <iostream>
#define io_h
#endif

void session::generateLocationArray(){
	baseArr = new base_t[numBases];	
	base_t b;
	b.color='n';
	b.score=0;
	double theta = 2*3.1415/numBases;
	int i;
	for(i=0; i<numBases; i++){
		b.x = start_x+(i*radius*1.0/numBases)*cos(i*theta);			
		b.y = start_y+(i*radius*1.0/numBases)*sin(i*theta);
		addBase(b);			
	}
}

void session::addBase(base_t b){
	static int baseIndex=0;
	baseArr[baseIndex++] = b;
}

void session::conquerBase(int tmNum, long int id, double x, double y){
	
	team *tm;
	if(team1->getTeamNum()==tmNum){
		tm = team1;
	}else{tm = team2;}

	int i;
	for(i=0;i<numBases;i++){
		if(baseArr[i].x==x && baseArr[i].y==y){
			if(tmNum==1){
				if(baseArr[i].score == 0){
					baseArr[i].color = tm->getColor();
					tm->playerScore(id);					
				}	
				baseArr[i].score++;	
			}else{			
				if(baseArr[i].score == 0){
					baseArr[i].color = tm->getColor();
					tm->playerScore(id);
				}
				baseArr[i].score--;
			}	
		}
	}

}

void session::init_Player(long int pid){
	player_t pl;
	pl.id=pid;
	pl.points=0;
	if(numPlayers<maxGameSize){
		if(numPlayers%2==0){
			team1->addPlayer(pl);
		}else{
			team2->addPlayer(pl);
		}
	}
}

void session::setRadius(double rad){
	radius = rad;	
}

void session::setBaseRadius(double rad){
	baseRadius = rad;
}

int session::getNumPlayers(){return numPlayers;}

void session::setMaxPlayerSize(int n){maxGameSize=n;}

base_t* session::getBases(){return baseArr;}

team session::getTeam(int tmNum){
	if(team1->getTeamNum()==tmNum){return *team1;
	}else{
		return *team2;
	}
}

void session::restartGame(){}

