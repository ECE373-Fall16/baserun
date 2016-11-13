package com.patricklowry.baserun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        //Intent intent = getIntent();
    }
}
