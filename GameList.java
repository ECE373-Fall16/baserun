
public class GameList{

	int gameCount;
	Game[] gameList = new Game[gameCount];

	public GameList(int count, Game[] games){
		gameCount = count;
		gameList = games;
	}

	public void printGames(){
		for(int i=0; i<gameList.length; i++){
			System.out.println(gameList[i].getGameID()+" || "+gameList[i].getCurrPlayCount()+"/"+gameList[i].getPlayerCount());
		}
	}
	
	public boolean joinGame(int GID, int PID){
		for(int i=0; i<gameList.length; i++){
			if(GID == gameList[i].getGameID()){
				if(gameList[i].joinGame(PID)){
					return true;
				}
			}
		}
			return false;
	}
}
