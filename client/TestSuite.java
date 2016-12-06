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
			System.out.println("7- Print Current Players");
			System.out.println("0- Exit");
			choice = Integer.parseInt(scan.nextLine());
			switch (choice) {
				case 1:
					if(!inGame){
						System.out.println("Please enter a PID");
						a = Integer.parseInt(scan.nextLine());
						System.out.println("please enter a PlayerCount");
						b = Integer.parseInt(scan.nextLine());
						System.out.println("Plsease enter a Radius");
						f = Double.parseDouble(scan.nextLine());
						System.out.println("Please enter a BaseCount");
						c = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter a StartLat");
						g = Double.parseDouble(scan.nextLine());
						System.out.println("Please enter a StartLong");
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
						System.out.println("Please enter a GID");
						a = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter a PID");
						b = Integer.parseInt(scan.nextLine());
						curr = net.joinGame(a,b);
						if(curr != null){
							System.out.println("GAME JOINED");
							System.out.println(curr.getGameID());
						} else
							System.out.println("GAME NOT JOINED");
					}
					break;
				case 3:
					if(inGame){
						System.out.println("Please enter a GID");
						a = Integer.parseInt(scan.nextLine());
						Game ref = net.refreshGame(a);
						curr.refreshGame(ref);
						if(ref != null)
							System.out.println("GAME REFRESHED");
						else
							System.out.println("GAME FAILED REFRESH");
					} else
						System.out.println("PLEASE JOIN/CREATE GAME");
					break;
				case 4:
					System.out.println("Please enter a PID");

					a = Integer.parseInt(scan.nextLine());
					games = net.gameList(a);
					if(games != null)
						games.printGames();
					else
						System.out.println("GAME LIST FAILED");
					break;
				case 5:
					if(inGame){
						System.out.println("Please enter a GID");
						a = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter a PID");
						b = Integer.parseInt(scan.nextLine());
						System.out.println("Please enter a Latitude");
						f = Double.parseDouble(scan.nextLine());
						System.out.println("Please enter a Longitude");
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
							curr.startTimer();
						}
					}
					break;
				case 7:
					if(inGame){
						User[] temp = curr.getPlayers();
						for(int i = 0; i < temp.length; i++){
							System.out.print(temp[i].getID()+"  ");
							System.out.println(temp[i].getTeam());
						}
					}
			}
		}
		scan.close();
	}
}
