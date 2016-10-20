

public class Game{
	int GameID;
	int playerCount;
	int currPlayerCount = 0;
	double radius;
	int baseCount;
	double[] location = new double[2];
	User[] players;
	User[] Team1;
	User[] Team2;
	Base[] bases;

	public Game(int ID, int playerCount, int radius, int baseCount, double startLat, double startLong){
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

	public boolean joinGame(int pid){
		for(int i=0; i<playerCount; i++){
			if(players[i] == null){
				players[i] = new User(pid);
				currPlayerCount++;
				return true;
			}
		}
		return false;
	}

	public int joinTeam(int pid){
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

	}

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

	public double getRadius(){
		return radius;
	}

	public int getBaseCount(){
		return baseCount;
	}
	
	public int getGameID(){
		return GameID;
	}

	public void addBase(double[] location, int radius){
		int i=0;
		while(bases[i] != null){
			if(i < baseCount)
				i++;
			else
				return;
		}
		bases[i] = new Base(location,radius);
	}

	public boolean onBase(double longitude, double latitude){
		for(int i = 0; i<baseCount; i++){
			if(latitude == bases[i].getLatitude()){
				if(longitude == bases[i].getLongitude()){
					return true;
				}
			}
		}
		return false;
	}

/*	public float distanceToBase(Base a){
		return a.getDistance();
	}*/

	public void drawBases(){
		for(int i=0; i<baseCount; i++){
			//Draw bases[i]
		}
	}

/*	public void drawUserLoc(){
		double[] currentLoc = new double[2];
		currentLoc[1] = Location.getLatitude();
		currentLoc[2] = Location.getLongitude();
		//Draw currentLoc
	}*/
	
	public void joinGame(User userID){
		
	}
}
