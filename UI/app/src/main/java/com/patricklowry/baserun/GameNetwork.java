package com.patricklowry.baserun;

import org.apache.xmlrpc.*;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.valueOf;

public class GameNetwork{
	XmlRpcClient server;
	public GameNetwork(){
		
	}
	
	public boolean connect(){
		try{
			String temp  = "http://104.196.221.210:3389/RPC2";
			server = new XmlRpcClient(temp);
		} catch(Exception e){
			return false;
		}
		if(checkConn())
			return true;
		else
			return false;
	}
	
	private boolean checkConn(){
		try {
			System.out.println("CHECKING CONNECTION");
			Vector check = new Vector();
			check.addElement(new String("CHECKCON"));
            System.out.println("ADDED ELEMENT");
			Vector checkConn = (Vector)server.execute("server.check",check);
			System.out.println("SERVER CALLED");
			if(((String)checkConn.get(0)).equals("CHECKCON")){
				System.out.println("CONN VERIFIED");
				return true;
			} else {
				System.out.println("CONN FAILED");
				return false;
			}
		} catch(Exception e){
			return false;
		}
	}
	
	public int getPID(){
		try{
			Vector params = new Vector();
			Vector getPID = (Vector)server.execute("server.getPID",params);
			return ((Integer)getPID.get(0)).intValue();
		} catch(Exception e){
			return 0;
		}
	}

	public Game createGame(int PID, int playerCount, double radius, int baseCount, double startLat, double startLong, long start, long end){
		if(checkConn()){
			try {
				Vector params2 = new Vector();
				params2.addElement(new Integer(PID));
				params2.addElement(new Integer(playerCount));
				params2.addElement(new Double(radius));
				params2.addElement(new Integer(baseCount));
				params2.addElement(new Double(startLat));
				params2.addElement(new Double(startLong));
                System.out.println("GAME DATA IN VECTOR");
				Vector createGame = (Vector)server.execute("server.createGame",params2);
                System.out.println("GAME DATA RECIEVED");
				Integer GID = (Integer)createGame.get(0);
				if(GID != -1){
                    System.out.println("GAME CREATED");
					Game temp = new Game(GID,playerCount,radius,baseCount,startLat,startLong);
                    temp.refreshGame(refreshGame(GID));
					setTime(GID,start,end);
					return temp;
				} else
					return null;
			} catch(Exception e){
				return null;
			}
		}
		return null;
	}

	public Game joinGame(int GID, int PID){
		Game temp;
		int k = 0;
		if(checkConn()){
			try {
			//CALL SERVER, RETURN GAME VALUE
				Vector params = new Vector();
				params.addElement(new Integer(GID));
				params.addElement(new Integer(PID));
				Vector join = (Vector)server.execute("server.join",params);
				Integer playCount = (Integer)join.get(k++);
				System.out.println(playCount);
				User[] players = new User[playCount];
				int j = 0;
				for(int i=1; j<playCount; i++){
					Integer id = (Integer)join.get(k++);
					players[j] = new User(id);
					Integer tid = (Integer)join.get(k++);
					players[j].setTeam(tid);
					System.out.println(players[j].getID());
					System.out.println(players[j].getTeam());
					j++;
				}
				Integer BaseCount = (Integer)join.get(k++);
				System.out.println(BaseCount);
				Base[] bases = new Base[BaseCount];
				j=0;
				for(int i=1; j<BaseCount; i++){
					Double lat = (Double)join.get(k++);
					System.out.println(lat);
					Double lon = (Double)join.get(k++);
					System.out.println(lon);
					Double rad = (Double)join.get(k++);
					System.out.println(rad);
					bases[j++] = new Base(lat,lon,rad);
				}
				Integer totPlayCount = (Integer)join.get(k++);
				System.out.println(totPlayCount);
				Double gameLat = (Double)join.get(k++);
				System.out.println(gameLat);
				Double gameLong = (Double)join.get(k++);
				System.out.println(gameLong);
				Double gameRad = (Double)join.get(k);
				System.out.println(gameRad);
				temp = new Game(GID,totPlayCount,gameRad,BaseCount,gameLat,gameLong);
				temp.setCurrPlayCount(playCount);
				temp.refreshGame(refreshGame(GID));
				long[] times = getTime(GID);
				temp.setStartTime(times[0]);
				temp.setEndTime(times[1]);
				return temp;
			} catch(Exception e){
				e.printStackTrace();	
				return null;
			}
		} else 
			return null;
	}

	public Game refreshGame(int GID){
		Game temp;
		int k = 0;
		if(checkConn()){
			try {
			//CALL SERVER, RETURN GAME UPDATE
				Vector params = new Vector();
				params.addElement(new Integer(GID));
				Vector refresh = (Vector)server.execute("server.refresh",params);
				int playCount = ((Integer)refresh.get(k++)).intValue();
				System.out.println(playCount);
				User[] players = new User[playCount];
				int j = 0;
				for(int i=1; j<playCount; i++){
					players[j] = new User(((Integer)refresh.get(k++)).intValue());
					System.out.println(players[j].getID());
					players[j].setTeam(((Integer)refresh.get(k++)).intValue());
					System.out.println(players[j].getTeam());
					j++;
				}
				int BaseCount = ((Integer)refresh.get(k++)).intValue();
				System.out.println(BaseCount);
				Base[] bases = new Base[BaseCount];
				j=0;
				for(int i=1; j<BaseCount; i++){
					double lat = ((Double)refresh.get(k++)).doubleValue();
					System.out.println(lat);
					double lon = ((Double)refresh.get(k++)).doubleValue();
					System.out.println(lon);
					double rad = ((Double)refresh.get(k++)).doubleValue();
					System.out.println(rad);
                    int own = ((Integer)refresh.get(k++));
					bases[j] = new Base(lat,lon,rad);
                    bases[j++].setOwner(own);
				}
				temp = new Game(playCount,players,bases);
				return temp;
			} catch(Exception e){
				return null;
			}
		} else
			return null;
	}

	public GameList gameList(int PID){
		GameList temp;
		if(checkConn()){
			try {
			//CALL SERVER, RETURN GAME LIST
				Vector params = new Vector();
				params.addElement(new Integer(PID));
				Vector gameList = (Vector)server.execute("server.gameList", params);
				int gcount = (Integer)gameList.get(0);
				int[] gids = new int[gcount];
				int[] players = new int[gcount];
				int[] currPlayers = new int[gcount];
				for(int i=0; i<gcount; i++){
					gids[i] = (Integer)gameList.get((i*3)+1);
					players[i] = (Integer)gameList.get((i*3)+2);
					currPlayers[i] = (Integer)gameList.get((i*3)+3);
				}
				temp = new GameList(gcount,gids, players, currPlayers);
				return temp;
			} catch(Exception e){
				return null;
			}
		} else
			return null;
	}

	public boolean onBase(int GID, int PID, double[] loc){
		Game temp;
		if(checkConn()){
			try {
				Vector params = new Vector();
				params.addElement(new Integer(GID));
				params.addElement(new Integer(PID));
				params.addElement(new Double(loc[0]));
				params.addElement(new Double(loc[1]));
				Vector onBase = (Vector)server.execute("server.onBase", params);
                System.out.println("ON BASE RECIEVED");
				if((Integer)onBase.get(0) == 1)
					return true;
				else
					return false;
			} catch(Exception e) {
				return false;
			}
		} else
			return false;
	}

	public long[] getTime(int GID){
		long[] times = new long[2];
		try {
			Vector params = new Vector();
			params.addElement(new Integer(GID));
			Vector getTime = (Vector)server.execute("server.getTime", params);
			times[0] = Long.parseLong((String)getTime.get(0));
			System.out.println(times[0]);
			times[1] = Long.parseLong((String)getTime.get(1));
			System.out.println(times[1]);
			return times;
		} catch(Exception e) {
			return null;
		}
	}

	public void setTime(int gid, long startl, long durl){
		String start = Long.toString(startl);
		System.out.println(start);
		String dur = Long.toString(durl);
		System.out.println(dur);
		try{
			Vector params = new Vector();
			params.addElement(new Integer(gid));
			params.addElement(start);
			params.addElement(dur);
			Vector setTime = (Vector)server.execute("server.setTime", params);
		} catch(Exception e) {
			return;
		}
	}

	public int[] getScore(int gid){
		int[] score = new int[2];
		try{
			Vector params = new Vector();
			params.addElement(new Integer(gid));
			Vector scores = (Vector)server.execute("server.getScore",params);
			score[0] = valueOf((Integer)scores.get(0));
			score[1] = valueOf((Integer)scores.get(1));
			return score;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
