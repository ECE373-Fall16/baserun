//header gaurds
#ifndef session_structs_h
#define session_structs_h
#include "session-structs.h"
#endif

#ifndef team_class_h
#include "team-class.h"
#define team_class_h
#endif

//header for session class
//note that descriptions for each fctn is shown in commoents in cpp file
class session{
public:
	//all public functions that can be called
	session(long int id);
	session(int maxS);
	~session();
	void generateLocationArray();
	int conquerBase(int tmNum, long int id, double x, double y);
	void init_Player(long int id);
	void setRadius(double rad);
	void setBaseRadius(double rad);
	int getNumPlayers();
	void setMaxPlayerSize(int n);
	base_t* getBases();
	team* getTeam(int tmNum);
	void restartGame();
	void setGid(long int id);
	long int getGid();
	void setNumBases(int n);
	void setStart(double x, double y);
	double getStartX();
	double getStartY();
	int getPTeamNum(long int pid);
	int getNumBases();
	double getRadius();
	double getBaseRadius();	
	int getMaxPlayerSize();
	
private:
	//private variables and functions that can only be accessed within the class
	long int gid;
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
	void addBase(base_t *b);
	int baseIndex;
};

