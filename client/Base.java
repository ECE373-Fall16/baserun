//package com.patricklowry.baserun;

//import com.google.android.gms.maps.model.Circle;

public class Base{
	double[] location = new double[2];
	double radius;
	int own;
	//Circle base;

	public Base(double[] location,int rad){
		this.location = location;
		radius = rad;
	}
	
	public Base(double centerLat, double centerLong, double rad){
		location[0]=centerLat;
		location[1]=centerLong;
		radius = rad;
		own = 0;
	}
	
	public Base(double centerLat, double centerLong, double rad, int own){
		location[0]=centerLat;
		location[1]=centerLong;
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
	
	public double getLatitude(){
		return location[1];
	}
	
	public double getLongitude(){
		return location[2];
	}
	
	public int getOwner(){
		return own;
	}

	public double getRadius(){
		return radius;
	}

/*	public void initBase(Circle a){
		base = a;
	}
*/
}
