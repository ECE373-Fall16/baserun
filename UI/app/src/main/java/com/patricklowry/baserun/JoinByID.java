package com.patricklowry.baserun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JoinByID extends AppCompatActivity {
    int GID;
    Game joined;
    Network net = new Network();

    public Game joinGame(){
	net.connect("127.0.0.1",8080);
        return (joined = net.joinGame(11111111,1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_by_id);
        //Intent intent = getIntent();
    }
	
    public void GameScreen(View view) {
	if(joined != null){
	    Intent begin = new Intent(this, GameScreen.class);
	    intent.putExtra("EXTRA_GAME", joined);
	    startActivity(begin);
	}
    }
}
