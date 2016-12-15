#include <iostream>
#include <string>


//header gaurds

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

using namespace std;


//constructor for session class that initializes GID
session::session(long int id){
	gid = id;
	numBases=0;
	numPlayers=0;
	team1 = new team;
	team2 = new team;
	team1->setTeamNum(1);
	team1->setColor('r');
	team2->setTeamNum(2);
	team2->setColor('b');
	baseIndex = 0;
}

//constructor for session class
session::session(int maxS){
	gid=0;
	numBases=0;
	numPlayers=0;
 	maxGameSize = maxS;
	team1 = new team(maxS/2);
	team2 = new team(maxS/2);
	baseIndex=0;
	team1->setTeamNum(1);
	team1->setColor('r');
	team2->setTeamNum(2);
	team2->setColor('b');
}

//destructor
session::~session(){}

//generates the bases
void session::generateLocationArray(){
	baseArr = new base_t[numBases];
	base_t *b=new base_t;
	b->color='n';
	b->score=0;
	double theta = 2*M_PI/numBases;
	int i;
	//creates spiral set of points as bases
	for(i=1; i<numBases+1; i++){
		b->x = start_x+(i*1609.34*radius*1.0/(numBases+1.0))*cos(i*theta)*(180/M_PI)/(6371000);
		b->y = start_y+(i*1609.34*radius*1.0/(numBases+1.0))*sin(i*theta)*(180/M_PI)/(6371000*cos(b->x*M_PI/180));
		addBase(b);
	}
}

//add a base to the base array
void session::addBase(base_t *b){
	baseArr[baseIndex++] = *b;
}

//checks to see if base gets conquered, and scores appropriately
int session::conquerBase(int tmNum, long int id, double x, double y){

	team *tm;
	team *otherTm;
	if(team1->getTeamNum()==tmNum){
		tm = team1;
		otherTm = team2;
	}else{
		otherTm = team1;
		tm = team2;
	}

	int i;
	int success=0;
	double con_x;
	double con_y;
	double diff;

	player_t *pl = tm->getPlayer(id);

	for(i=0;i<numBases;i++){
		con_x = (baseArr[i].x - x)*(M_PI/180)*6378137;
		con_y = (baseArr[i].y - y)*(M_PI/180)*6378137*cos(y*M_PI/180);
		diff = sqrt((con_x*con_x)+(con_y*con_y));
		cout<<"DIFFERENCE:::: "<<diff<<endl;

		if(diff <= baseRadius && pl->baseN != i){
			if(baseArr[i].score == 0){
				if(tm->getColor() != baseArr[i].color){
					tm->playerScore(id);
					if(pl->baseN != 1){
						if(tm->getTeamNum() == 1){
							baseArr[pl->baseN].score--;
						}else{
							baseArr[pl->baseN].score++;
					}

					}
					pl->baseN = i;
					if(otherTm->getColor() == baseArr[i].color){
						otherTm->downByOne();
					}
					baseArr[i].color = tm->getColor();
				}
					success = 1;
			}
			if(tm->getTeamNum() == 1){
				baseArr[i].score++;
			}else{
				baseArr[i].score--;
			}
		}
	}
	return success;

}

//initialize a player in the team
void session::init_Player(long int pid){
	player_t pl;
	pl.id=pid;
	pl.points=0;
	pl.baseN = -1;
	if(numPlayers<maxGameSize){
		if(numPlayers%2==0){
			team1->addPlayer(pl);
		}else{
			team2->addPlayer(pl);
		}
		numPlayers++;
	}
}

//set the radius of the game
void session::setRadius(double rad){
	radius = rad;
	baseRadius = 25;
}

//set the base radius for the game
void session::setBaseRadius(double rad){
	baseRadius = rad;
}

//return number of players in game
int session::getNumPlayers(){return numPlayers;}

//return max player size of game
void session::setMaxPlayerSize(int n){maxGameSize=n;}

//returns base array
base_t* session::getBases(){return baseArr;}

//returns ptr of requested team by team number
team* session::getTeam(int tmNum){
	if(team1->getTeamNum()==tmNum){
		return team1;
	}else{
		return team2;
	}
}

//set the game ID
void session::setGid(long int id){gid=id;}

//return GID
long int session::getGid(){return gid;}

//set number of bases for game
void session::setNumBases(int n){numBases = n;}

//get team number of team with player corresponding to pid
int session::getPTeamNum(long int pid){
	if(team1->playerThere(pid)){return 1;}
	if(team2->playerThere(pid)){return 2;}
	return 0;
}

//restarts the game -- yet to be implemented
void session::restartGame(){}

//set starting location for all players
void session::setStart(double x, double y){
	start_x = x;
	start_y = y;
}

//return start position (x coord)
double session::getStartX(){return start_x;}

//return start position (y coord)
double session::getStartY(){return start_y;}

//return number of bases
int session::getNumBases(){return numBases;}

//return radius of game
double session::getRadius(){return radius;}

//return base radius of game
double session::getBaseRadius(){return baseRadius;}

//return max player size of game
int session::getMaxPlayerSize(){return maxGameSize;}

//sets start and end time of game
void session::setTime(string startT, string endT){
	sTime = startT;
	eTime = endT;
}

//return start time
string session::getStartTime(){return sTime;}

//return end time
string session::getEndTime(){return eTime;}
