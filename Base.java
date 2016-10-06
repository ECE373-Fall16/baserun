
public Base{
	double[] location = new double[2];
	int radius;
	int own;

	public Base(double centerLat, double centerLong, int rad){
		location[1]=centerLat;
		location[2]=centerLong;
		radius = rad;
		own = 0;
	}

	public void setOwner(int own){
		this.own = own;
	}

	public double[] getLocation(){
		return location;
	}

	public int getOwner{
		return own;
	}
}