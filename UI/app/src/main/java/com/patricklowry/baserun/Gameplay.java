package com.patricklowry.baserun;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Gameplay extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private com.patricklowry.baserun.Game currGame = null;
    private GameNetwork net = new GameNetwork();
    private int PID = 99999999;
    private android.location.LocationManager locman;
    private android.location.LocationListener loclisten;
    private android.location.Location currLoc;
    private double currLat, currLong;
    private Handler handler = new Handler();
    private Marker user;
    private int players;
    private int bases;
    private double dur;
    private double rad;
    private int startT;
    private Circle[] baseCircles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
            return;
        }
        super.onCreate(savedInstanceState);
        players = Integer.parseInt(getIntent().getStringExtra("EXTRA_PLAYERS_INT"));
        bases = Integer.parseInt(getIntent().getStringExtra("EXTRA_BASES_INT"));
        dur = Double.parseDouble(getIntent().getStringExtra("EXTRA_DUR_DOUB"));
        rad = Double.parseDouble(getIntent().getStringExtra("EXTRA_RAD_DOUB"));
        startT = Integer.parseInt(getIntent().getStringExtra("EXTRA_START_INT"));
        System.out.println(players);
        System.out.println(bases);
        System.out.println(dur);
        System.out.println(rad);
        System.out.println(startT);
        setContentView(R.layout.activity_gameplay);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    }

    //public void onConnected(Bundle connectionHint) {}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        currLoc = locman.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (currLoc != null){
            currLat = currLoc.getLatitude();
            currLong = currLoc.getLongitude();
        } else {
            currLoc = locman.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(currLoc != null){
                currLat = currLoc.getLatitude();
                currLong = currLoc.getLongitude();
            }else
                System.out.print(currLat + " " + currLong);
        }
        locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclisten);
        locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,loclisten);
        System.out.println(currLat+"  "+currLong);
        checkCon();
        createGame();
        while(currGame == null){}
        baseCircles = new Circle[currGame.getBaseCount()];
        drawBases();
        System.out.println("GameID: "+currGame.getGameID());

    /*
        LatLng curLoc = new LatLng(42.3912, -72.5267);
        mMap.addMarker(new MarkerOptions().position(curLoc).title("Marker in UMass Amherst"));
	*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currLat, currLong)));
        Circle game = mMap.addCircle(new CircleOptions()
                .center(new LatLng(currLat, currLong))
                .radius(currGame.getRadius()*1609.34)
                .strokeColor(Color.BLACK)
                .fillColor(0x88888888));
        user = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(currLat, currLong)));
        System.out.println(currLat+"  "+currLong);
        currGame.drawBases(mMap);
        //UPDATE GAME AND SCORE
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(Gameplay.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(Gameplay.this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

                    } else {
                        ActivityCompat.requestPermissions(Gameplay.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }
                    return;
                }
                locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclisten);
                locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,loclisten);
                System.out.println("ENTERS LOOP");
                //Update User Location
                user.setPosition(new LatLng(currLat, currLong));
                user.setVisible(true);
                //Check On Base
                Thread refresh = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (net.onBase(currGame.getGameID(),PID,currGame.getGameLocation())) {
                            double[] location = new double[2];
                            location[0] = currLat;
                            location[1] = currLong;
                            net.onBase((/*currGame.getGameID()*/100), PID, location);
                        }
                        currGame.refreshGame(net.refreshGame(currGame.getGameID()));
                        //currGame.drawBases(mMap);
                        currGame.setScores(net.getScore(currGame.getGameID()));
                    }
                });
                refresh.start();
                handler.postDelayed(this,1000);
            }
        }, 1000);
    }


    public void onBackPressed() {
        if (ActivityCompat.checkSelfPermission(Gameplay.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(Gameplay.this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(Gameplay.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
            return;
        }
        locman.removeUpdates(loclisten);
        AlertDialog exit = LeaveGame();
        exit.show();
    }

    private AlertDialog LeaveGame() {
        return new AlertDialog.Builder(this)
                .setTitle("Leave current game?")
                .setMessage("You will be disconnected and returned to the previous screen.")
                .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    private void checkCon(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                net.connect();
            }
        });
        thread.start();
    }

    private void createGame(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                currGame = net.createGame(PID,players,rad,bases,currLat,currLong);
            }
        });
        thread.start();
    }

    private void refreshGame(){

    }

    private void drawBases(){
        Base[] bases = currGame.getBases();
        int fill;
        for(int i=0; i<currGame.getBaseCount(); i++){
            System.out.println("PRINTING BASE LOCS");
            System.out.println(bases[i].getLatitude() + " " + bases[i].getLongitude());
            System.out.println(bases[i].getRadius());
            if(bases[i].getOwner() == 1)
                fill = Color.RED;
            else if(bases[i].getOwner() == 2)
                fill = Color.BLUE;
            else
                fill = Color.GRAY;
            baseCircles[i] = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(bases[i].getLatitude(),bases[i].getLongitude()))
                    .radius(bases[i].getRadius())
                    .strokeColor(Color.BLACK)
                    .fillColor(fill));
            bases[i].initBase(baseCircles[i]);
        }
    }
}
