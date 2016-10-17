

public class Game{
	int GameID;
	int playerCount;
	int radius;
	int baseCount;
	double[] location = new double[2];
	User[] players = new User[playerCount];
	User[] Team1 = new User[playerCount/2];
	User[] Team2 = new User[playerCount/2];
	Base[] bases;

	public Game(int ID, int playerCount, int radius, int baseCount, double startLat, double startLong){
		this.playerCount = playerCount;
		this.radius = radius;
		this.baseCount = baseCount;
		bases = new Base[baseCount];
		location[1] = startLat;
		location[2] = startLong;
		GameID = ID;
	}

	public boolean joinGame(int pid){
		for(int i=0; i<playerCount; i++){
			if(players[i] == null){
				players[i].setID() == pid;
				return true;
			}
		}
		return false;
	}

	public int joinTeam(int pid){
		for(int i=0; i<playercount; i++){
			if(players[i].getID() == pid)
				if(getTeam2Size() > getTeam1Size()){
					players[i].setTeam(1);
					return 1;
				} else {
					players[i].setTeam(0);
					return 0;
				}
		}

	}

	public int getTeam1Size(){
		for(int i=0; i<playercount/2; i++){
			if(Team1[i] == null)
				return i;
		}
	}

	public int getTeam2Size(){
		for(int i=0; i<playercount/2; i++{
			if(Team2[i] == null)
				return i;
		}
	}

	public int getPlayerCount(){
		return playerCount;
	}

	public int getCurrPlayCount(){
		for(int i=0; i<playerCount; i++){
			if(players[i] == null)
				return i;
		}
	}

	public int getRadius(){
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
		while(bases[i] != null)
			i++;
		bases[i] = new Base(location,radius);
	}

	private boolean onBase(){
		double latitude = Location.getLatitude();
		double longitude = Location.getLongitude();
		for(int i = 0; i<baseCount; i++){
			if(latitude == bases[i].getLatitude()){
				if(longitude == bases[i].getLongitude()){
					return true;
				}
				else return false;
			}
			else return false;
		}
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
	
	public void joinGame(User userID){
		
	}
}
