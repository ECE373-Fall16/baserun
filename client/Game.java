package com.patricklowry.baserun;

import android.graphics.Color;
import android.location.Location;
import android.os.CountDownTimer;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import static com.patricklowry.baserun.R.id.GameTime;

public class Game{
	int PID = 25; //will be changed
	int GameID;
	int playerCount;
	int currPlayerCount = 1;
	double radius;
	int baseCount;
	private double[] GameLocation = new double[2];
	private User[] players;
	private User[] Team1;
	private User[] Team2;
	private Base[] bases;
//	private long timeToStart;
//	private GameTimer time;

	public Game(int currPlayers, User[] currPlay, Base[] base){
		currPlayerCount = currPlayers;
		players = currPlay;
		Team1 = new User[(currPlayers/2) + 1];
		Team2 = new User[(currPlayers/2) + 1];
		int j = 0;
		int k = 0;
		for(int i=0; i<currPlayers; i++){
			if(players[i].getTeam() == 1){
				Team1[j++] = currPlay[i];
			} else if(players[i].getTeam() == 2){
				Team2[k++] = currPlay[i];
			}
		}
		bases = base;
	}

	public Game(int ID, int playerCount, double radius, int baseCount, double startLat, double startLong,long tts){
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
		currPlayerCount = 1;
		//time = new GameTimer(10 /*duration*/);
		players[0] = new User(PID);
		players[0].setTeam(1);
		new CountDownTimer(tts, 1000) {
			public void onTick(long millisUntilFinished) {
				GameTime.setText(millisUntilFinished/1000);
			}
			public void onFinish() {
				GameTime.setText("BEGIN");
			}
		}.start();
	}

/*	public void startTimer(){
		time.start();
	}

	public double getTime(){
		double temp = time.getDur();
		return temp;
	}
*/
	public void refreshGame(Game temp){
		int i;
		int j=0;
		int k=0;
		currPlayerCount = temp.getCurrPlayCount();
		User[] users = temp.getPlayers();
		User[] t1 = temp.getTeam1();
		User[] t2 = temp.getTeam2();
		for(i = 0; i < users.length; i++){
			players[i] = users[i];
			if(users[i].getTeam() == 1)
				Team1[j++] = users[i];
			if(users[i].getTeam() == 2)
				Team2[k++] = users[i];
		}
		bases = temp.getBases();
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

	public void setPlayers(User[] temp, int count){
		currPlayerCount = count;
		players = temp;
		int j = 0;
		int k = 0;
		for(int i=0; i< count; i++){
			if(temp[i].getTeam() == 1){
				Team1[j++] = temp[i];
			} else if(temp[i].getTeam() == 2){
				Team2[k++] = temp[i];
			}
		}
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

	public void setBases(Base[] temp){
		bases = temp;
	}

	//NEEDS ANDROID API TO BE FUNCTIONAL//

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

	//public float distanceToBase(Base a){
	//	return a.getDistance();
	//}

	public void drawBases(GoogleMap a){
		int fill;
		for(int i=0; i<baseCount; i++){
			if(bases[i].getOwner() == 1)
				fill = Color.RED;
			else if(bases[i].getOwner() == 2)
				fill = Color.BLUE;
            else
                fill = Color.GRAY;
			Circle temp = a.addCircle(new CircleOptions()
				.center(new LatLng(bases[i].getLatitude(),bases[i].getLongitude()))
				.radius(bases[i].getRadius())
				.strokeColor(Color.BLACK)
				.fillColor(fill));
            bases[i].initBase(temp);
		}
	}

	public void drawUserLoc(){
		double[] currentLoc = new double[2];
		currentLoc[1] = Location.getLatitude();
		currentLoc[2] = Location.getLongitude();
		//Draw currentLoc
	}
}
