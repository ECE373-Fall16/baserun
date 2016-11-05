
public class Network{
	XmlRpcClient server;
	public Network(){
		
	}
	
	public boolean connect(String server, int port){
		try{
			server = new XmlRpcClient("http://"+server+":"+port);
		} catch(UnknownHostException e){
			return false;
		} catch(IOException e){
			e.printStackTrace();
		}
		if(checkConn())
			return true;
		else
			return false;
	}
	
	private boolean checkConn(){
		Vector check = new Vector();
		params.addElement(new String("CHECKCON"));
		Vector checkConn = (Vector)server.execute("server.check",check);
		if((String)checkConn.get(0).equals("CHECKCON"){
			return true;
		} else 
			return false
	}

	public Game createGame(int PID, int playerCount, double radius, int baseCount, double startLat, double startLong){
		if(checkConn()){
			Vector params = new Vector();
			params.addElement(new Integer(PID));
			
			Vector genGID = (Vector)server.execute("server.genGID",params);
			Integer GID = (Integer)server.get(0);
			Vector params2 = new Vector();
			params2.addElement(new Integer(GID));
			params2.addElement(new Integer(playerCount));
			params2.addElement(new Double(radius));
			params2.addElement(new Integer(baseCount));
			params2.addElement(new Double(startLat));
			params2.addElement(new Double(startLong));
			Vector createGame = (Vector)server.execute("server.createGame",params2);
			if((Integer)createGame.get(0) == 1){
				Game temp = new Game(GID,playerCount,radius,baseCount,startLat,startLong);
				return temp;
			} else
				return null;
		}
		return null;
	}

	public Game joinGame(int GID, int PID){
		Game temp;
		if(checkConn()){
			//CALL SERVER, RETURN GAME VALUE
			Vector params = new Vector();
			params.addElement(new Integer(GID));
			params.addElement(new Integer(PID));
			Vector join = (Vector)server.execute("server.join",params);
			Integer playCount = (Integer)join.get(0);
			User[] players = new User[playCount];
			int j = 0;
			for(int i=1; j<playCount; i++){
				User[j] = new User((Integer)join.get(i++));
				User[j++].setTeam((Integer)join.get(i));
			}
			Integer BaseCount = (Integer)join.get(playCount+1);
			Base[] bases = new Base[BaseCount];
			j=0;
			for(int i=1; j<BaseCount; i++){
				Double lat = (Double)join.get((playCount+1)+i++);
				Double lon = (Double)join.get((playCount+1)+i++);
				Double rad = (Double)join.get((playCount+1)+i);
				Base[j++] = new Base(lat,lon,rad);
			}
			Integer totPlayCount = (Integer)join.get(playCount+BaseCount+1);
			Double gameLat = (Double)join.get(playCount+BaseCount+2);
			Double gameLon = (Double)join.get(playCount+BaseCount+3);
			Double gameRad = (Double)join.get(playCount+BaseCount+4);
			temp = new Game(GID,totPlayCount,gameRad,BaseCount,gamelat,gamelong);
			temp.setCurrPlayCount(playCount);
			return temp;
		} else 
			return null;
	}

	public Game refreshGame(int GID, int PID){
		Game temp;
		if(checkConn()){
			//CALL SERVER, RETURN GAME UPDATE
			Vector params = new Vector();
			params.addElement(new Integer(GID));
			params.addElement(new Integer(PID));
			Vector refresh = (Vector)server.execute("server.join",params);
			Integer playCount = (Integer)refresh.get(0);
			User[] players = new User[playCount];
			int j = 0;
			for(int i=1; j<playCount; i++){
				User[j] = new User((Integer)refresh.get(i++));
				User[j++].setTeam((Integer)refresh.get(i));
			}
			Integer BaseCount = (Integer)refresh.get(playCount+1);
			Base[] bases = new Base[BaseCount];
			j=0;
			for(int i=1; j<BaseCount; i++){
				Double lat = (Double)refresh.get((playCount+1)+i++);
				Double lon = (Double)refresh.get((playCount+1)+i++);
				Double rad = (Double)refresh.get((playCount+1)+i);
				Base[j++] = new Base(lat,lon,rad);
			}
	
			return temp;
		} else
			return null;
	}

	public GameList gameList(int PID){
		GameList temp;
		if(checkConn()){
			//CALL SERVER, RETURN GAME LIST
			Vector params = new Vector();
			params.addElement(new Integer(PID));
			
			Vector gameList = (Vector)server.execute("server.gameList", params);
			Integer gcount = (Integer)gameList.get(0);
			Integer[] gids = new Integer[gcount];
			for(int i=0; i<gcount; i++){
				gids[i] = (Integer)gameList.get(i+1);
			}
			Integer[] players = new Integer[gcount];
			for(i=0; i<gcount; i++){
				players[i] = (Integer)gameList.get(gcount+i+1);
			}
			Integer[] currPlayers = new Integer[gcount];
			for(i=0; i<gcount; i++){
				currPlayers[i] = (Integer)gameList.get(2*gcount+i+1);
			}
			temp = new GameList(gcount,gids, players, currPlayers);
			return temp;
		} else
			return null;
	}

	public boolean onBase(int GID, int PID, double[] loc){
		Game temp;
		if(checkConn()){
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
		} else
			return null
	}
}
