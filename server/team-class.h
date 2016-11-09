#ifndef session_structs_h
#include "session-structs.h"
#define session_structs_h
#endif

class team{
public:	
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
private:
	char color;
	player_t *playerArr;
	int teamScore;
	int numPlayers;
	int maxPlayers;
	int tmNum;
};
