package com.patricklowry.baserun;

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
		System.out.println("FUCK OFF AND DIE");
		currPlayerCount = currPlayers;
		players = currPlay;
		int j = 0;
		int k = 0;
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
		GameLocation[0] = startLat;
		GameLocation[1] = startLong;
		GameID = ID;
	}

	public void refreshGame(Game refresh){
		currPlayerCount = refresh.getCurrPlayCount();
		players = refresh.getPlayers();
		Team1 = refresh.getTeam1();
		Team2 = refresh.getTeam2();
		bases = refresh.getBases();
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

	//NEEDS ANDROID API TO BE FUNCTIONAL//
/*
	public double[] onBase(double longitude, double latitude){
		double[] loc = new double[2];
		loc[0] = latitude;
		loc[1] = longitude;
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
*/
	public void drawBases(googleMap a){
		int fill;
		for(int i=0; i<baseCount; i++){
			if(bases[i].getOwn() == 0)
				fill = Color.GREY;
			if(bases[i].getOwn() == 1)
				fill = Color.RED;
			if(bases[i].getOwn() == 2)
				fill = Color.BLUE;
			bases[i].initBase() = a.addCircle(new CircleOptions()
				.center(new LatLng(bases[i].getLatitude(),bases[i].getLongitude()))
				.radius(bases[i].getRadius())
				.strokeColor(Color.BLACK)
				.fillColor(fill));
		}
	}
/*
	public void drawUserLoc(){
		double[] currentLoc = new double[2];
		currentLoc[1] = Location.getLatitude();
		currentLoc[2] = Location.getLongitude();
		//Draw currentLoc
	}
*/
}
