package com.patricklowry.baserun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class CreateGame extends AppCompatActivity {
    String players;
    String bases;
    String dur;
    String rad;
    String startT;

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
        Intent begin = new Intent(this, Gameplay.class);
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
        players = num_players_array[numPlayersPos];
        bases = num_bases_array[numBasesPos];
        dur = game_dur_array[gameDurPos];
        rad = game_rad_array[gameRadPos];
        startT = game_start_array[gameStartPos];
        Bundle params = new Bundle();
        params.putString("EXTRA_PLAYERS_INT", players);
        params.putString("EXTRA_BASES_INT", bases);
        params.putString("EXTRA_DUR_DOUB", dur);
        params.putString("EXTRA_RAD_DOUB", rad);
        params.putString("EXTRA_START_INT", startT);
        begin.putExtras(params);
        startActivity(begin);
    }
}
