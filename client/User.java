//package com.patricklowry.baserun;

public class User {
	
	private int ID;
	private int TID;
	
	public User(int id){
		ID = id;
	}
	
	public int getID(){
		return ID;
	}

	public int getTeam(){
		return TID;
	}
	
	public void setTeam(int team){
		TID = team;
	}
}
