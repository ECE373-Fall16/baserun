package com.patricklowry.baserun;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Network;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class CreateGame extends AppCompatActivity {
    int players;
    int bases;
    double dur;
    Game created;
    Network net = new Network();

    /*public Game createGame(){
        net.connect("127.0.0.1",8080);
	return (created = net.createGame(1,10,1.0,10,1.0,1.0));
	//net.createGame format is int PID, int playerCount, double radius, int baseCount, double startLat, double startLong
    }*/ //WILL BE USED AFTER .1

    public void VerifyGame() {
        net.connect("104.196.195.139", 8080);
        if (net.sendToServer(players, bases, dur)) {
            //DO SOMETHING IF SERVER SEES GAME CALL
        } else {
            //DO SOMETHING IF SERVER DOES NOT GET GAME CALL
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        Spinner num_players = (Spinner) findViewById(R.id.num_players_spinner);
        ArrayAdapter<CharSequence> players_adapter = ArrayAdapter.createFromResource(this, R.array.num_players_array, android.R.layout.simple_spinner_item);
        players_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        num_players.setAdapter(players_adapter);

        Spinner num_bases = (Spinner) findViewById(R.id.num_bases_spinner);
        ArrayAdapter<CharSequence> bases_adapter = ArrayAdapter.createFromResource(this, R.array.num_bases_array, android.R.layout.simple_spinner_item);
        bases_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        num_bases.setAdapter(bases_adapter);

        Spinner game_dur = (Spinner) findViewById(R.id.game_dur_spinner);
        ArrayAdapter<CharSequence> dur_adapter = ArrayAdapter.createFromResource(this, R.array.game_dur_array, android.R.layout.simple_spinner_item);
        dur_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        game_dur.setAdapter(dur_adapter);

        Spinner game_rad = (Spinner) findViewById(R.id.game_rad_spinner);
        ArrayAdapter<CharSequence> rad_adapter = ArrayAdapter.createFromResource(this, R.array.game_rad_array, android.R.layout.simple_spinner_item);
        rad_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        game_rad.setAdapter(rad_adapter);

        Spinner game_start = (Spinner) findViewById(R.id.game_start_spinner);
        ArrayAdapter<CharSequence> start_adapter = ArrayAdapter.createFromResource(this, R.array.game_start_array, android.R.layout.simple_spinner_item);
        start_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        game_start.setAdapter(start_adapter);
    }

    public void GameScreen(View view) {
        if(created != null){
            Intent begin = new Intent(this, Gameplay.class);
            begin.putExtra("EXTRA_GAME", (Parcelable) created);
            startActivity(begin);
        }
    }
}
