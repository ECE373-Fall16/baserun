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
private:
	char color;
	player_t *playerArr;
	int teamScore;
	int numPlayers;
	int maxPlayers;
	int tmNum
}
