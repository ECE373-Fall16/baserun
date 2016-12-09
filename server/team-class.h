#ifndef session_structs_h
#include "session-structs.h"
#define session_structs_h
#endif

//team class
class team{
public:	
	//all public functions that can be called from team class
	//note descriptions are in comments in cpp file
	team();
	team(int maxP);
	~team(); 
	void addPlayer(player_t p);
	int getTeamScore();
	player_t* getPlayer(long int pid);
	char getColor();
	player_t* getPlayerArr();
	int getNumPlayers();
	void setMaxPlayers(int maxN);
	void setColor(char c);
	void setTeamNum(int n);
	int getTeamNum();
	bool playerThere(long int pid);
	void playerScore(long int pid);
	void downByOne();
private:
	//private variables of the team class
	char color;
	player_t *playerArr;
	int teamScore;
	int numPlayers;
	int maxPlayers;
	int tmNum;
};
