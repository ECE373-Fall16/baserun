package com.patricklowry.baserun;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.Math.cos;

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
    TextView redScore;
    TextView blueScore;
    TextView timer;
    private long mils;
    private long mils2;
    private boolean gameStart = false;

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

            public void onProviderDisabled(String provider) {
            }
        };
        locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclisten);
        locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,loclisten);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        timer = (TextView)findViewById(R.id.textView9);
        new CountDownTimer(startT*60*1000+(long)(dur*3600*1000),1000) {
            public void onTick(long millisUntilFinished){
                mils = millisUntilFinished-(long)(dur*3600*1000);
                if(mils >= 0) {
                    int sec = (int) ((mils) / 1000) % 60;
                    int min = (int) ((mils) / (1000 * 60)) % 60;
                    int hrs = (int) ((mils) / (1000 * 3600)) % 24;
                    String time = String.format("%02d:%02d:%02d", hrs, min, sec);
                    timer.setText(time);
                } else {
                    gameStart = true;
                    mils2 = millisUntilFinished;
                    int sec2 = (int)(mils2 /1000)%60;
                    int min2 = (int)(mils2 /(1000*60))%60;
                    int hrs2 = (int)(mils2 /(1000*3600))%24;
                    String time2 = String.format("%02d:%02d:%02d", hrs2, min2, sec2);
                    timer.setText(time2);
                }
            }
            public void onFinish(){
                timer.setText("GAME OVER");
            }
        }.start();
        redScore = (TextView)findViewById(R.id.RedScore);
        blueScore = (TextView)findViewById(R.id.BlueScore);
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
        Circle game = mMap.addCircle(new CircleOptions()
                .center(new LatLng(currLat, currLong))
                .radius(currGame.getRadius()*1609.34)
                .strokeColor(Color.BLACK)
                .fillColor(0x88888888));
        user = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(currLat, currLong)));
        System.out.println(currLat+"  "+currLong);

        double diff =(game.getRadius()/6378137) * (180/3.1415626535);
        LatLngBounds GAME = new LatLngBounds(new LatLng(game.getCenter().latitude-diff,game.getCenter().longitude-diff/ cos(game.getCenter().latitude*3.1415926535/180)),new LatLng(game.getCenter().latitude+diff,game.getCenter().longitude+diff/ cos(game.getCenter().latitude*3.1415926535/180)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(GAME,10));
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
                user.setPosition(new LatLng(currLat, currLong));
                user.setVisible(true);
                Location UserLoc = new Location("");
                UserLoc.setLatitude(currLat);
                UserLoc.setLongitude(currLong);
                for(int i=0; i<currGame.getBaseCount(); i++) {
                    Location tempBase = new Location("");
                    tempBase.setLatitude(currGame.getBases()[i].getLatitude());
                    tempBase.setLongitude(currGame.getBases()[i].getLongitude());
                    float distance = UserLoc.distanceTo(tempBase);
                    System.out.println(distance);
                    if (distance <= currGame.getBases()[i].getRadius() && gameStart) {
                        onBase(i);
                    }
                }
                refreshGame();
                setScore();
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
        Thread refresh = new Thread(new Runnable() {
            @Override
            public void run() {
                currGame.refreshGame(net.refreshGame(currGame.getGameID()));
            }
        });
        refresh.start();
    }

    private void onBase(final int i){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("CALLING ON BASE");
                net.onBase((currGame.getGameID()), PID, currGame.getBases()[i].getLocation());
            }
        });
        thread.start();
    }

    private void setScore(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                currGame.setScores(net.getScore(currGame.getGameID()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String red = Integer.toString(currGame.rScore);
                        System.out.println("RED: "+ red);
                        String blue = Integer.toString(currGame.bScore);
                        System.out.println("BLUE: "+ blue);
                        redScore.setText(red);
                        blueScore.setText(blue);
                        for(int i=0; i<currGame.baseCount; i++) {
                            if (currGame.getBases()[i].getOwner() == 1) {
                                baseCircles[i].setFillColor(Color.RED);
                                System.out.println("MAKE BASE RED");
                            }
                            if (currGame.getBases()[i].getOwner() == 2) {
                                baseCircles[i].setFillColor(Color.BLUE);
                                System.out.println("MAKE BASE BLUE");
                            }
                        }
                    }
                });
            }
        });
        thread.start();
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