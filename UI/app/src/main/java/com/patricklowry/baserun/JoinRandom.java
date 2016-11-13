package com.patricklowry.baserun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

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
        ListView random = (ListView) findViewById(R.id.randomList);
        ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar);
        //loading.setVisibility(View.INVISIBLE);
        //random.setVisibility(View.VISIBLE);
    }
}
