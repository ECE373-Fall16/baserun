import org.apache.xmlrpc.*;
import java.util.*;

public class Network{
	private XmlRpcClient server;

	public Network(){
		
	}
	
	public boolean connect(){
		try{
			server = new XmlRpcClient("http://104.196.221.210:3389/RPC2");
		} catch(Exception e){
			return false;
		}
		if(checkConn()){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkConn(){
		try {
			System.out.println("CHECKING CONNECTION");
			Vector check = new Vector();
			check.addElement(new String("CHECKCON"));
			Vector checkConn = (Vector)server.execute("server.check",check);
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

	public Game createGame(int PID, int playerCount, double radius, int baseCount, double startLat, double startLong){
		if(checkConn()){
			try {
				Vector params = new Vector();
				params.addElement(new Integer(PID));
				params.addElement(new Integer(playerCount));
				params.addElement(new Double(radius));
				params.addElement(new Integer(baseCount));
				params.addElement(new Double(startLat));
				params.addElement(new Double(startLong));
				Vector createGame = (Vector)server.execute("server.createGame",params);
				Integer GID = (Integer)createGame.get(0);
				System.out.println(GID);
				if(GID != -1){
					Game temp = new Game(GID,playerCount,radius,baseCount,startLat,startLong);
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
		if(checkConn()){
			try {
			//CALL SERVER, RETURN GAME VALUE
				Vector params = new Vector();
				params.addElement(new Integer(GID));
				params.addElement(new Integer(PID));
				Vector join = (Vector)server.execute("server.join",params);
				Integer playCount = (Integer)join.get(0);
				User[] players = new User[playCount];
				int j = 0;
				for(int i=1; j<playCount; i++){
					players[j] = new User((Integer)join.get(i++));
					players[j].setTeam((Integer)join.get(i));
					j++;
				}
				Integer BaseCount = (Integer)join.get(playCount+3);
				Base[] bases = new Base[BaseCount];
				j=0;
				for(int i=1; j<BaseCount; i++){
					Double lat = (Double)join.get((playCount+3)+i++);
					Double lon = (Double)join.get((playCount+3)+i++);
					Double rad = (Double)join.get((playCount+3)+i);
				double[] loc = new double[2];	
				loc[0] = lat;
				loc[1] = lon;
				bases[j++] = new Base(loc,rad);
				}
				Integer totPlayCount = (Integer)join.get(playCount+BaseCount+1);
				Double gameLat = (Double)join.get(playCount+BaseCount+2);
				Double gameLong = (Double)join.get(playCount+BaseCount+3);
				Double gameRad = (Double)join.get(playCount+BaseCount+4);
				temp = new Game(GID,totPlayCount,gameRad,BaseCount,gameLat,gameLong);
				temp.setCurrPlayCount(playCount);
				return temp;
			} catch(Exception e){	
				return null;
			}
		} else 
			return null;
	}

	public Game refreshGame(int GID){
		Game temp;
		if(checkConn()){
			try {
			//CALL SERVER, RETURN GAME UPDATE
				Vector params = new Vector();
				params.addElement(new Integer(GID));
				Vector refresh = (Vector)server.execute("server.refresh",params);
				Integer playCount = (Integer)refresh.get(0);
				User[] players = new User[playCount];
				int j = 0;
				for(int i=1; j<playCount; i++){
					players[j] = new User((Integer)refresh.get(i++));
					players[j].setTeam((Integer)refresh.get(i));
					j++;
				}
				Integer BaseCount = (Integer)refresh.get(playCount+3);
				Base[] bases = new Base[BaseCount];
				j=0;
				for(int i=1; j<BaseCount; i++){
					Double lat = (Double)refresh.get((playCount+3)+i++);
					Double lon = (Double)refresh.get((playCount+3)+i++);
					Double rad = (Double)refresh.get((playCount+3)+i);
					double[] loc = new double[2];
					loc[0] = lat;
					loc[1] = lon;
					bases[j++] = new Base(loc,rad);
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
		int j = 0;
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
				System.out.println(gcount);
				/*for(int i=0; i<gcount; i++){
					gids[i] = (Integer)gameList.get(i+1);
				}
				int[] players = new int[gcount];
				for(int i=0; i<gcount; i++){
					players[i] = (Integer)gameList.get(gcount+i+1);
				}
				int[] currPlayers = new int[gcount];
				for(int i=0; i<gcount; i++){
					currPlayers[i] = (Integer)gameList.get(2*gcount+i+1);
				}*/
				for(int i=0; i<gcount; i++){
					gids[i] = (Integer)gameList.get(j+1);
					players[i] = (Integer)gameList.get(j+2);
					currPlayers[i] = (Integer)gameList.get(j+3);
					j+=3;
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
}