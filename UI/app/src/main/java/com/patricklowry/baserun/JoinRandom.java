package com.patricklowry.baserun;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
public class JoinRandom extends AppCompatActivity {
    private GameNetwork net = new GameNetwork();
    private int PID = 99999999;
    private TextView total;
    String act = "joinbyid";
    String[] Games;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        net.connect();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random);
        ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar);
        final ListView random = (ListView) findViewById(R.id.randomList);
        total = (TextView) findViewById(R.id.textView18);
        TextView locText = (TextView) findViewById(R.id.textView5);
        Geocoder cityState = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> curAdd = cityState.getFromLocation(42.3912, -72.5267, 1);
            String state = curAdd.get(0).getAddressLine(1);
            locText.setText(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createGame();
        if (Games != null) {
            // populate list here
            int tot = Games.length;
            total.setText(Integer.toString(tot));
            ArrayList<String> items = new ArrayList<>();
            for (int i = 0; i < tot; i++) {
                System.out.println(Games[i]);
                items.add(Games[i]);
            }
            ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            random.setAdapter(adap);
            random.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE); // get rid of loading bar
        }
        else {
            loading.setVisibility(View.GONE);
            Context context = getApplicationContext();
            CharSequence text = "Failed to load available games from the server.";
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();
            finish();
        }
    }
    private void createGame(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Games = net.gameList(PID).clone();
                System.out.println(Arrays.toString(Games));
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}