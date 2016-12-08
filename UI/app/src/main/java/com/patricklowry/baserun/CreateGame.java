package com.patricklowry.baserun;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class CreateGame extends AppCompatActivity {
    int PID;
    int players;
    int bases;
    double dur;
    double rad;
    int startT;
    Game created;
    GameNetwork net = new GameNetwork();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        net.connect();

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

        final Button start = (Button) findViewById(R.id.button7);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Spinner numPlayers = (Spinner) findViewById(R.id.num_players_spinner);
                Spinner numBases = (Spinner) findViewById(R.id.num_bases_spinner);
                Spinner gameDur = (Spinner) findViewById(R.id.game_dur_spinner);
                Spinner gameRad = (Spinner) findViewById(R.id.game_rad_spinner);
                Spinner gameStart = (Spinner) findViewById(R.id.game_start_spinner);
                int numPlayersPos = numPlayers.getSelectedItemPosition();
                int numBasesPos = numBases.getSelectedItemPosition();
                int gameDurPos = gameDur.getSelectedItemPosition();
                int gameRadPos = gameRad.getSelectedItemPosition();
                int gameStartPos = gameStart.getSelectedItemPosition();
                String[] num_players_array = getResources().getStringArray(R.array.num_players_array);
                String[] num_bases_array = getResources().getStringArray(R.array.num_bases_array);
                String[] game_dur_array = getResources().getStringArray(R.array.game_dur_array);
                String[] game_rad_array = getResources().getStringArray(R.array.game_rad_array);
                String[] game_start_array = getResources().getStringArray(R.array.game_start_array);
                players = Integer.valueOf(num_players_array[numPlayersPos]);
                bases = Integer.valueOf(num_bases_array[numBasesPos]);
                dur = Double.valueOf(game_dur_array[gameDurPos]);
                rad = Double.valueOf(game_rad_array[gameRadPos]);
                startT = Integer.valueOf(game_start_array[gameStartPos]);
                net.createGame(PID,);
            }
        });
    }

    public void GameScreen(View view) {
        if(created != null){
            Intent begin = new Intent(this, Gameplay.class);
            begin.putExtra("EXTRA_GAME", (Parcelable) created);
            startActivity(begin);
        }
    }
}
