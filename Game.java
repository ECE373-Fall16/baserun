

public class Game{
	int playerCount;
	int radius;
	int baseCount;
	double[] location = new double[2];
	Base[] bases;

	public Game(int playerCount, int radius, int baseCount, double startLat, double startLong){
		this.playerCount = playerCount;
		this.radius = radius;
		this.baseCount = baseCount;
		bases = new Base[baseCount];
		location[1] = startLat;
		location[2] = startLong;
	}

	public int getPlayerCount(){
		return playerCount;
	}

	public int getRadius(){
		return radius;
	}

	public int getBaseCount(){
		return baseCount;
	}
	
	public void addBase(double[] location, int radius){
		int i=0;
		while(bases[i] != null)
			i++;
		bases[i] = new Base(location,radius);
	}

	private boolean onBase(){
		double latitude = Location.getLatitude();
		double longitude = Location.getLongitude();
		for(int i = 0; i<baseCount; i++){
			if(latitude == bases[i].getLatitude()){
				if(longitude == bases[i].getLongitude()){
					return true;
				}
				else return false;
			}
			else return false;
		}
	}

	public float distanceToBase(Base a){
		return a.getDistance();
	}

	public void drawBases(){
		for(int i=0; i<baseCount; i++){
			//Draw bases[i]
		}
	}

	public void drawUserLoc(){
		double[] currentLoc = new double[2];
		currentLoc[1] = Location.getLatitude();
		currentLoc[2] = Location.getLongitude();
		//Draw currentLoc
	}
	
	public void joinGame(User userID){
		
	}
}