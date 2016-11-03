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

	void addBase(base_t b);
	void addPlayer(player_t p);	
}
