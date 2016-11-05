

public class Game{
	int GameID;
	int playerCount;
	int currPlayerCount = 0;
	double radius;
	int baseCount;
	double[] GameLocation = new double[2];
	User[] players;
	User[] Team1;
	User[] Team2;
	Base[] bases;

	public Game(int currPlayers, User[] currPlay, Base[] base){
		currPlayCount = currPlayers;
		players = currPlay;
		j = 0;
		k = 0;
		for(int i=0; i<players.length; i++){
			if(players[i].getTeam() == 0){
				Team1[j++] = players[i];
			} else if(players[i].getTeam() == 1){
				Team2[k++] = players[i];
			}
		}
		bases = base;
	}

	public Game(int ID, int playerCount, double radius, int baseCount, double startLat, double startLong){
		this.playerCount = playerCount;
		this.radius = radius;
		this.baseCount = baseCount;
		bases = new Base[baseCount];
		players = new User[playerCount];
		Team1 = new User[playerCount/2];
		Team2 = new User[playerCount/2];
		location[0] = startLat;
		location[1] = startLong;
		GameID = ID;
	}

	public void refreshGame(Game refresh){
		currPlayCount = refresh.getCurrPlayCount();
		players = currPlay.getPlayers();
		Team1 = t1.getTeam1();
		Team2 = t2.getTeam2();
		bases = base.getBases();
	}

	/*public int joinTeam(int pid){
		for(int i=0; i<playerCount; i++){
			if(players[i].getID() == pid){
				if(getTeam2Size() > getTeam1Size()){
					players[i].setTeam(1);
					return 1;
				} else {
					players[i].setTeam(0);
					return 0;
				}
			}
		}
		return -1;

	}*/

	public int getTeam1Size(){
		for(int i=0; i<playerCount/2; i++){
			if(Team1[i] == null)
				return i;
		}
		return Team1.length;
	}

	public int getTeam2Size(){
		for(int i=0; i<playerCount/2; i++){
			if(Team2[i] == null)
				return i;
		}
		return Team2.length;
	}

	public int getPlayerCount(){
		return playerCount;
	}

	public int getCurrPlayCount(){
		return currPlayerCount;
	}

	public void setCurrPlayCount(int count){
		currPlayerCount = count;
	}

	public double getRadius(){
		return radius;
	}

	public int getBaseCount(){
		return baseCount;
	}
	
	public int getGameID(){
		return GameID;
	}

	public User[] getPlayers(){
		return players;
	}

	public User[] getTeam1(){
		return Team1;
	}

	public User[] getTeam2(){
		return Team2;
	}

	public Base[] getBases(){
		return bases;
	}

	public double[] onBase(double longitude, double latitude){
		double[] loc = new double[2];
		loc = {latitude,longitude};
		for(int i = 0; i<baseCount; i++){
			if(latitude == bases[i].getLatitude()){
				if(longitude == bases[i].getLongitude()){
					return loc;
				}
			}
		}
		return null;
	}

	public float distanceToBase(Base a){
		return a.getDistance();
	}

	public void drawBases(){
		for(int i=0; i<baseCount; i++){
			//Draw bases[i]
		}
	}

	public void drawUserLoc(){
		double[] currentLoc = new double[2];
		currentLoc[1] = Location.getLatitude();
		currentLoc[2] = Location.getLongitude();
		//Draw currentLoc
	}
}
