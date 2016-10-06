public static void main(String[] args){
    Scanner scan = new Scanner(system.in);
    int choice = -1;
    while(choice != 0){
	System.out.println("Welcome to the BaseRun Client Test Suite");
	System.out.println("1- Test Make Game");
	System.out.println("2- Test Join Game");
	System.out.println("3- Test onBase");
	System.out.println("0- Exit");
	choice = scan.nextLine();
	switch(choice){
	   case 1:
		System.out.println("Please enter a player count:");
		int playerCount = scan.nextLine();
		System.out.println("Please enter a game radius:");
		int radius = scan.nextLine();
		System.out.println("Please enter a base count:");
		int baseCount = scan.nextLine();
		System.out.println("Please enter a start latitude:");
		double lat = scan.nextLine();
		System.out.println("Please enter a start longitude:");
		double lon = scan.nextLine();
		System.out.println("Creating game...");
		Game test = new Game(playerCount,radius,baseCount,lat,lon);
		System.out.println("Game created! Printing test menu...");
		int choice1 = -1;
		while(choice1 != 0){
		    System.out.println("Game test menu:");
		    System.out.println("1- Print playerCount");
		    System.out.println("2- Print radius");
		    System.out.println("3- Print baseCount");
		    System.out.println("0- Exit");
		    switch(choice1){
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
		
		break;
	   case 3:
		
		break;
	}
    }
}
