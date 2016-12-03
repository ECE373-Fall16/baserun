import java.util.Scanner;

public class TestSuite {
	public static void main(String[] args) {
		Network net = new Network();
		Scanner scan = new Scanner(System.in);
		net.connect();
		GameList games;
		Game curr;
		boolean inGame = false;
		int a;
		int b;
		int c;
		double f;
		double g;
		double h;
		int choice = -1;
		while (choice != 0) {
			/* OLD CLIENT TESTING SUITE

			System.out.println("Welcome to the BaseRun Client Test Suite");
			System.out.println("1- Test Make Game");
			System.out.println("2- Test Join Game");
			System.out.println("3- Test onBase");*/
			System.out.println("Welcome to the BaseRun Client Test Suite V2");
			System.out.println("1- Test Create Game");
			System.out.println("2- Test Join Game");
			System.out.println("3- Test Refresh Game");
			System.out.println("4- Test GameList");
			System.out.println("5- Test Networking");
			System.out.println("0- Exit");
			choice = Integer.parseInt(scan.nextLine());
			switch (choice) {
				case 1:
					if(!inGame){
						System.out.println("Please enter an int");
						a = Integer.parseInt(scan.nextLine());
						System.out.println("please enter an int");
						b = Integer.parseInt(scan.nextLine());
						System.out.println("Plsease enter a double");
						f = Double.parseDouble(scan.nextLine());
						System.out.println("Please enter an int");
						c = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter a double");
						g = Double.parseDouble(scan.nextLine());
						System.out.println("Please enter a double");
						h = Double.parseDouble(scan.nextLine());
						curr = net.createGame(a,b,f,c,g,h);
						inGame = true;
						if(curr != null){
							System.out.println(curr.getGameID());
							System.out.println(curr.getPlayerCount());
							System.out.println(curr.getCurrPlayCount());
						} else
							System.out.println("CANNOT CREATE GAME");
					} else
						System.out.println("CURRENTLY IN GAME");
					break;
				case 2:
					if(inGame)
						System.out.println("CURRENTLY IN GAME");
					else {
						Game test2 = net.createGame(1,1,1.1,1,1.1,1.1);
						int testGID = test2.GameID;
						Game test3 = net.joinGame(testGID,1);
						if(test3 != null)
							System.out.println("GAME JOINED");
						else
							System.out.println("GAME NOT JOINED");
					}
					break;
				case 3:
					if(inGame){
						System.out.println("Please enter an int");
						a = Integer.parseInt(scan.nextLine());
						Game ref = net.refreshGame(a);
						//curr.refreshGame(ref);
						if(ref != null)
							System.out.println("GAME REFRESHED");
						else
							System.out.println("GAME FAILED REFRESH");
					} else
						System.out.println("PLEASE JOIN/CREATE GAME");
					break;
				case 4:
					System.out.println("Please enter an int");

					a = Integer.parseInt(scan.nextLine());
					games = net.gameList(a);
					if(games != null)
						games.printGames();
					else
						System.out.println("GAME LIST FAILED");
					break;
				case 5:
					System.out.println("Please enter an int");
					a = Integer.parseInt(scan.nextLine());
					System.out.println("please enter an int");
					b = Integer.parseInt(scan.nextLine());
					System.out.println("Plsease enter a double");
					f = Double.parseDouble(scan.nextLine());
					System.out.println("Please enter an int");
					c = Integer.parseInt(scan.nextLine());
					System.out.println("Please enter a double");
					g = Double.parseDouble(scan.nextLine());
					System.out.println("Please enter a double");
					h = Double.parseDouble(scan.nextLine());
					if(net.createGame(a,b,f,c,g,h) != null)
						System.out.println("CREATE GAME PASSED");
					else
						System.out.println("CREATE GAME FAILED");
					System.out.println("Please enter an int");
					a = Integer.parseInt(scan.nextLine());
					System.out.println("Please enter an int");
					b = Integer.parseInt(scan.nextLine());
					if(net.joinGame(a,b) != null)
						System.out.println("JOIN GAME PASSED");
					else
						System.out.println("JOIN GAME FAILED");
					System.out.println("Please enter an int");
					a = Integer.parseInt(scan.nextLine());
					if(net.refreshGame(a) != null)
						System.out.println("REFRESH GAME PASSED");
					else
						System.out.println("REFRESH GAME FAILED");
					System.out.println("Please enter an int");
					a = Integer.parseInt(scan.nextLine());
					if(net.gameList(a) != null)
						System.out.println("GAME LIST PASSED");
					else
						System.out.println("GAME LIST FAILED");
					System.out.println("Please enter an int");
					a = Integer.parseInt(scan.nextLine());
					System.out.println("Please enter an int");
					b = Integer.parseInt(scan.nextLine());
					System.out.println("Please enter a double");
					f = Double.parseDouble(scan.nextLine());
					System.out.println("Please enter a double");
					g = Double.parseDouble(scan.nextLine());
					double[] loc = new double[2];
					loc[0] = f;
					loc[1] = g;
					if(net.onBase(a,b,loc))
						System.out.println("ON BASE PASSED");
					else
						System.out.println("ON BASE FAILED");
			}
		}
		scan.close();
	}
}
