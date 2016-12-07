package com.patricklowry.baserun;

public class GameList{
	int gameCount;
	int[] GIDs = new int[gameCount];
	int[] players = new int[gameCount];
	int[] currPlayers = new int[gameCount];
	GameNetwork net = new GameNetwork();

	public GameList(int count, int[] gids, int[] play, int[] currPlay){
		gameCount = count;
		GIDs = gids;
		players = play;
		currPlayers = currPlay;
	}

	public void printGames(){
		for(int i=0; i<gameCount; i++){
			System.out.println(GIDs[i]+" || "+currPlayers[i]+"/"+players[i]);
		}
	}
	
	public Game joinGame(int GID, int PID){
		for(int i=0; i<gameCount; i++){
			if(GID == GIDs[i]){
				return net.joinGame(GID,PID);
			}
		}
			return null;
	}
}
