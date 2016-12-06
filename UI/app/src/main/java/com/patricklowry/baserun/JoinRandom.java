package com.patricklowry.baserun;

import android.location.Address;
import android.location.Geocoder;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class JoinRandom extends AppCompatActivity {
    GameList list;
    Network net = new Network();
    Game joined;

    public Game joinGame(){
	net.connect("127.0.0.1",8080);
	return (joined = net.joinGame(1,11111111));
    }

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
    }
}
