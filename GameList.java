
public class GameList{

	int gameCount;
	Game[] gameList = new Game[gameCount];

	public GameList(int count, Game[] games){
		gameCount = count;
		gameList = games;
	}

	public void printGames(){
		for(int i=0; i<gameCount; i++){
			System.out.print(gameList[i].getGameID()+" || "+gameList[i].getCurrPlayCount()+"/"+gameList[i].getPlayerCount());
		}
	}
}
