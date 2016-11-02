
public class BaseRunClient{
	int id;
	boolean inGame;
	Game curr;
	public static void  main(String[] args){
		try{
			Scanner uid = new Scanner(new FileReader("uid.db"));
			id = uid.nextInt();
		} catch(Exception e){
			PrintWriter save = new PrintWriter("uid.db","UTF-8");
			id = /*Contact server for new UID*/;
			save.println(id);
		}
		//Connect user to server//
		//After connected, as user changes pages allow for user inputs//
		if(/*CREATE GAME*/){
			//Server Call to create game//
		} else if(/*JOIN GAME*/){
			if(/*GameList*/){
				//PrintGameList to select//
				//Server call to join selected game//
				if(/*Game is joined*/){
					inGame == true;
					curr = new Game(/*Info from server*/);
				}
			} else if (/*GameID*/){
				//Server call to join game//
				if(/*Game is joined*/){
					inGame == true;
					curr = new Game(/*Info from server*/);
				}
			}
			//Refresh game until starts//
		} else if(/*ON GAME SCREEN*/&& inGame){
			//PLAY GAME BASED ON GAME RULES//
		}
	}
}
