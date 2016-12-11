package com.patricklowry.baserun;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class JoinRandom extends AppCompatActivity {
    private GameNetwork net = new GameNetwork();
    private GameList list;
    private int PID = 99999999;
    private android.location.LocationManager locman;
    private android.location.LocationListener loclisten;
    private android.location.Location currLoc;
    private double currLat,currLong;

    protected void onCreate(Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
            return;
        }
        locman = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        loclisten = new LocationListener() {
            public void onLocationChanged(Location location) {
                if(location == null)
                    System.out.print(currLat + " " + currLong);
                else {
                    currLat = location.getLatitude();
                    currLong = location.getLongitude();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            ;

            public void onProviderDisabled(String provider) {
            }

            ;
        };
        locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclisten);
        locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,loclisten);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random);
        TextView locText = (TextView) findViewById(R.id.textView5);
        Geocoder cityState = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> curAdd = cityState.getFromLocation(currLat, currLong, 1);
            String state = curAdd.get(0).getAddressLine(1);
            CharSequence loc = state;
            locText.setText(loc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createGame();
    }

    private void createGame(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                list = net.gameList(PID);
            }
        });
        thread.start();
    }
}
