#include "session-class.h"
#include "session-structs.h"
#include <cmath>
#include <iostream>

void session::generateLocationArray(){
	int i;
	double x,y;
	double theta = 2*3.1415/numBases;
	for(i=0; i<numBases; i++){
		x = start_x+(i*radius*1.0/numBases)*cos(i*theta);			
		y = start_y+(i*radius*1.0/numBases)*sin(i*theta);
		addBase(base_t(x,y,'n',0));			
	}
}


class session{
public:
	void session();
	void generateLocationArray();
	void conquerBase(int tmNum, long int id, double x, double y);
	void init_player(long int id);
	void setRadius(double rad);
	void setBaseRadius(double rad);
	int getNumPlayers();
	base_t* getBases();
	team_t getTeam(int tmNum);
	void restartGame();
	
private:
	int numPlayers;
	int numBases;
	double radius;
	double baseRadius;
	double start_x;
	double start_y;
	team_t team1;
	team_t team2;
	base_t *baseArr;

	void addBase(double x, double y);
	void addPlayer(player p);	

}


include "session-structs.h"
include <string>;

typedef struct team{
	char color;
	player_t *playerArr;
	int teamPoints;
}team_t;

typedef struct base{}


typedef struct player{
	long int id;
	string name;
	int points;
}



