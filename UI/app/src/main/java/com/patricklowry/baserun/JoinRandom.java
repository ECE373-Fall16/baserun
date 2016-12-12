package com.patricklowry.baserun;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class JoinRandom extends AppCompatActivity {
    private GameNetwork net = new GameNetwork();
    private int PID = 99999999;
    String act = "joinbyid";
    String[] Games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random);
        ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar);
        final ListView random = (ListView) findViewById(R.id.randomList);
        TextView total = (TextView) findViewById(R.id.textView18);
        Button retry = (Button) findViewById(R.id.button8);
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
            total.setText(tot);
            ArrayList<String> items = new ArrayList<>();
            for (int i = 0; i < tot; i++) {
                items.add(Games[i]);
            }
            ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            random.setAdapter(adap);
            random.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE); // get rid of loading bar
            random.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent begin = new Intent(JoinRandom.this, Gameplay.class);
                    String gmeStr = (random.getItemAtPosition(position).toString());
                    String[] gmeStrArr = gmeStr.split(" ");
                    String idStr = gmeStrArr[0];
                    Bundle params = new Bundle();
                    params.putString("EXTRA_ACTIVITY", act);
                    params.putString("EXTRA_ID", idStr);
                    begin.putExtras(params);
                    startActivity(begin);
                }
            });
        }
        else {
            loading.setVisibility(View.GONE);
            retry.setVisibility(View.VISIBLE);
            Context context = getApplicationContext();
            CharSequence text = "Failed to load available games from the server.";
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();
        }
    }
    private void createGame(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Games = net.gameList(PID).getStrings();
            }
        });
        thread.start();
    }
    public void Restart() {
        this.recreate();
    }
}
