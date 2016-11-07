#ifndef session_structs_h
#define session_structs_h
#include "session-structs.h"
#endif

#ifndef team_class_h
#include "team-class.h"
#define team_class_h
#endif

class session{
public:
	session();
	~session();
	void generateLocationArray();
	void conquerBase(int tmNum, long int id, double x, double y);
	void init_Player(long int id);
	void setRadius(double rad);
	void setBaseRadius(double rad);
	int getNumPlayers();
	void setMaxPlayerSize(int n);
	base_t* getBases();
	team getTeam(int tmNum);
	void restartGame();
	
private:
	int numPlayers;
	int maxGameSize;
	int numBases;
	double radius;
	double baseRadius;
	double start_x;
	double start_y;
	team* team1;
	team* team2;
	base_t *baseArr;
	void addBase(base_t b);
};

