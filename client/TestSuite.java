import java.util.Scanner;

public class TestSuite {
	public static void main(String[] args) {
		Network net = new Network();
		Scanner scan = new Scanner(System.in);
		net.connect();
		GameList games;
		Game curr = null;
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
			System.out.println("5- Test onBase");
			System.out.println("6- Test Timer");
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
						System.out.println("Please enter an int");
						a = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter an int");
						b = Integer.parseInt(scan.nextLine());
						Game test3 = net.joinGame(a,b);
						if(test3 != null){
							System.out.println("GAME JOINED");
							System.out.println(test3.getGameID());
						} else
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
					if(inGame){
						System.out.println("Please enter an int");
						a = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter an int");
						b = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter a double");
						f = Double.parseDouble(scan.nextLine());
						System.out.println("Please enter a double");
						g = Double.parseDouble(scan.nextLine());
						double[] temp = new double[2];
						temp[0] = f;
						temp[1] = g;
						if(net.onBase(a,b,temp))
							System.out.println("ON BASE");
						else
							System.out.println("NOT ON BASE");
					}
					break;
				case 6:
					if(inGame){
						if(/*net.startGame(curr.getGameID())*/ true){
							long init = System.currentTimeMillis();
							if(curr != null)
								curr.startTimer();
							if((System.currentTimeMillis()-init)%100 == 0)
								System.out.println(curr.getTime());
						}
					}
			}
		}
		scan.close();
	}
}
