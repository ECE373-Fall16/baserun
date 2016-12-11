package com.patricklowry.baserun;

import android.location.Address;
import android.location.Geocoder;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random);
        TextView locText = (TextView) findViewById(R.id.textView5);
        Geocoder cityState = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> curAdd = cityState.getFromLocation(42.3912, -72.5267, 1);
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
