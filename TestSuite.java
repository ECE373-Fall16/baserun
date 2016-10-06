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
					System.out.println("0- Exit");
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
				System.out.println("Please enter Game ID:");
				int gameID = Integer.parseInt(scan.nextLine());
				System.out.println("Please enter User ID:");
				int userID = Integer.parseInt(scan.nextLine());
				
				break;
			case 3:

				break;
			}
		}
		scan.close();
	}
}
