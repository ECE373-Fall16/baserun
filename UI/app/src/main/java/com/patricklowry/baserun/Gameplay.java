package com.patricklowry.baserun;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Game;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

public class Gameplay extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private Game currGame;
    private GameNetwork net = new GameNetwork();
    private int PID = 99999999;
    private GoogleApiClient client;
    private Location mLastLocation;
    private LocationRequest LocRequest = new LocationRequest();
    private LocationListener listen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        Game game = params.getParcelable("EXTRA_GAME");
        String id = params.getString("EXTRA_ID");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Context context = getApplicationContext();
        CharSequence text = "Joined game " + id;
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, text, duration).show();
        if (client == null) {
            client = new GoogleApiClient.Builder(this)
                    .addOnConnectionFailedListener(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    protected void onStart() {
        client.connect();
        super.onStart();
    }

    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        if (mLastLocation != null) {
            startLocationUpdates();
        }
    }

    protected void createLocationRequest() {
        LocRequest.setInterval(1000);
        LocRequest.setFastestInterval(500);
        LocRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, LocRequest, listen);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        net.connect();
    /*
        LatLng curLoc = new LatLng(42.3912, -72.5267);
        mMap.addMarker(new MarkerOptions().position(curLoc).title("Marker in UMass Amherst"));
	*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currGame.getLatitude(), currGame.getLongitude())));
        Circle game = mMap.addCircle(new CircleOptions()
                .center(new LatLng(currGame.getLatitude(), currGame.getLongitude()))
                .radius(currGame.getRadius())
                .strokeColor(Color.BLACK)
                .fillColor(0x88888888));
        final Marker user = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(currGame.getLatitude(), currGame.getLongitude()))
                .setVisible(false));
        currGame.drawBases(mMap);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //Update User Location
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                double pLat;
                double pLng;

                if (mLastLocation != null) {
                    pLat = mLastLocation.getLatitude();
                    pLng = mLastLocation.getLongitude();
                }
                user.setPosition(new LatLng(pLat, pLng));
                user.setVisible(true);
                //Check On Base
                if (currGame.onBase(pLat, pLng) != -1) {
                    double[] location = new double[2];
                    location[0] = pLat;
                    location[1] = pLng;
                    net.onBase((currGame.getGameID()), PID, location);
                }
            }
        }, 0, 1000);
    }

    public void onBackPressed() {
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

    protected void onStop() {
        client.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
