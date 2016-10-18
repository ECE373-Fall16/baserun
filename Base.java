
public class Base{
	double[] location = new double[2];
	int radius;
	int own;

	public Base(double[] location,int rad){
		this.location = location;
		radius = rad;
	}
	
	public Base(double centerLat, double centerLong, int rad){
		location[1]=centerLat;
		location[2]=centerLong;
		radius = rad;
		own = 0;
	}

//	public  getDistance(Base a){
		//get distance from location of base a to current location
		
//	}
	
	public void setOwner(int own){
		this.own = own;
	}

	public double[] getLocation(){
		return location;
	}
	
	public double getLatitude(){
		return location[1];
	}
	
	public double getLongitude(){
		return location[2];
	}
	
	public int getOwner(){
		return own;
	}
}
