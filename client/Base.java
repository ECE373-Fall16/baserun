
public class Base{
	private double[] location = new double[2];
	private double radius;
	private int own;

	public Base(double[] location, double rad){
		this.location = location;
		radius = rad;
		own = 0;
	}
	
	public Base(double[] location, double rad, int own){
		this.location = location;
		radius = rad;
		this.own = own;
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
	
	public int getOwner(){
		return own;
	}
}
