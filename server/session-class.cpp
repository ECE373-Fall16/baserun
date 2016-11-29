#ifndef session_class_h
#include "session-class.h"
#define session_class_h
#endif


#ifndef session_structs_h
#include "session-structs.h"
#define session_structs_h
#endif

#ifndef team_class_h
#include "team-class.h"
#define team_class_h
#endif

#ifndef math_h
#include <cmath>
#define session_structs_h
#endif

#ifndef io_h
#include <iostream>
#define io_h
#endif

session::session(long int id){
	gid = id;
	numBases=0;
	numPlayers=0;	
	team1 = new team;
	team2 = new team;
	team1->setTeamNum(1);
	team2->setTeamNum(2);
}

session::session(){
	gid=0;
	numBases=0;
	numPlayers=0;	
	team1 = new team;
	team2 = new team;
	team1->setTeamNum(1);
	team2->setTeamNum(2);
}

session::~session(){}

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

int session::conquerBase(int tmNum, long int id, double x, double y){
	
	team *tm;
	if(team1->getTeamNum()==tmNum){
		tm = team1;
	}else{tm = team2;}

	int i;
	int success=0;
	for(i=0;i<numBases;i++){
		if(baseArr[i].x==x && baseArr[i].y==y){
			if(tmNum==1){
				if(baseArr[i].score == 0){
					baseArr[i].color = tm->getColor();
					tm->playerScore(id);
					success = 1;					
				}	
				baseArr[i].score++;	
			}else{			
				if(baseArr[i].score == 0){
					baseArr[i].color = tm->getColor();
					tm->playerScore(id);
					success = 1;
				}
				baseArr[i].score--;
			}	
		}
	}
	return success;

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
	baseRadius = 5;	
}

void session::setBaseRadius(double rad){
	baseRadius = rad;
}

int session::getNumPlayers(){return numPlayers;}

void session::setMaxPlayerSize(int n){maxGameSize=n;}

base_t* session::getBases(){return baseArr;}

team* session::getTeam(int tmNum){
	if(team1->getTeamNum()==tmNum){
		return team1;
	}else{
		return team2;
	}
}

void session::setGid(long int id){gid=id;}

long int session::getGid(){return gid;}

void session::setNumBases(int n){numBases = n;}

int session::getPTeamNum(long int pid){
	if(team1->playerThere(pid)){return 1;}
	if(team2->playerThere(pid)){return 2;}
	return 0;	
}

void session::restartGame(){}

void session::setStart(double x, double y){
	start_x = x;
	start_y = y;
}

double session::getStartX(){return start_x;}

double session::getStartY(){return start_y;}

int session::getNumBases(){return numBases;}

double session::getRadius(){return radius;}

double session::getBaseRadius(){return baseRadius;}

int session::getMaxPlayerSize(){return maxGameSize;}
