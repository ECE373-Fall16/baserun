import java.util.Scanner;

public class TestSuite {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int choice = -1;
		while (choice != 0) {
			System.out.println("Welcome to the BaseRun Client Test Suite");
			System.out.println("1- Test Make Game");
			System.out.println("2- Test Join Game");
			System.out.println("3- Test onBase");
			System.out.println("0- Exit");
			choice = Integer.parseInt(scan.nextLine());
			switch (choice) {
			case 1:
				System.out.println("Please enter a player count:");
				int playerCount = Integer.parseInt(scan.nextLine());
				System.out.println("Please enter a game radius:");
				int radius = Integer.parseInt(scan.nextLine());
				System.out.println("Please enter a base count:");
				int baseCount = Integer.parseInt(scan.nextLine());
				System.out.println("Please enter a start latitude:");
				double lat = Double.parseDouble(scan.nextLine());
				System.out.println("Please enter a start longitude:");
				double lon = Double.parseDouble(scan.nextLine());
				System.out.println("Creating game...");
				Game test = new Game(playerCount, radius, baseCount, lat, lon);
				System.out.println("Game created! Printing test menu...");
				int choice1 = -1;
				while (choice1 != 0) {
					System.out.println("Game test menu:");
					System.out.println("1- Print playerCount");
					System.out.println("2- Print radius");
					System.out.println("3- Print baseCount");
					System.out.println("0- Main Menu");
					switch (choice1) {
					case 1:
						System.out.println(test.getPlayerCount());
						break;
					case 2:
						System.out.println(test.getRadius());
						break;
					case 3:
						System.out.println(test.getBaseCount());
						break;
					}
				}
				break;
			case 2:
				Game[] test = new Game[5];
				int[] id = new int[5];
				for(int i=0; i<5; i++){
					id[i] = (int)Math.random()*99999999;
					for(int j=0; j<5; j++){
						while(id[i] == id[j])
							id[i] = (int)Math.random()*99999999;
					}
					test[i] = new Game(id[i],8,10,10,100,100);
				}
				GameList games = new GameList(test);
				games.printGames();
				int gameChoice;
				System.out.println("Please enter Game ID you would like to join:");
				int gameID = Integer.parseInt(scan.nextLine());
				for(int i=0; i<5; i++){
					if(gameID == test[i].getGameID())
						gameChoice == i;
				}
				System.out.println("Please enter User ID:");
				int userID = Integer.parseInt(scan.nextLine());
				System.out.println("Joining Game...");
				boolean join = test[i].joinGame(userID);
				if(join)
					System.out.println("Joined game!");
				else
					System.out.println("Game full!");
				break;
			case 3:

				break;
			}
		}
		scan.close();
	}
}
