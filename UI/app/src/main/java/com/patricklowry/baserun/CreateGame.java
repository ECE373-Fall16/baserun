package com.patricklowry.baserun;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class CreateGame extends AppCompatActivity {
    //int players;
    //int bases;
    //Game created;
    //Network net = new Network();

//    public Game createGame(){
//        net.connect("127.0.0.1",8080);
//	return (created = net.createGame(1,10,1.0,10,1.0,1.0));
//	//net.createGame format is int PID, int playerCount, double radius, int baseCount, double startLat, double startLong
//    }

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
    }

    public void startGame(View view) {
        Spinner numPlayers = (Spinner) findViewById(R.id.num_players_spinner);
        Spinner numBases = (Spinner) findViewById(R.id.num_bases_spinner);
        Spinner gameDur = (Spinner) findViewById(R.id.game_dur_spinner);
        int numPlayersPos = numPlayers.getSelectedItemPosition();
        int numBasesPos = numBases.getSelectedItemPosition();
        int gameDurPos = gameDur.getSelectedItemPosition();
        String[] num_players_array = getResources().getStringArray(R.array.num_players_array);
        String[] num_bases_array = getResources().getStringArray(R.array.num_bases_array);
        String[] game_dur_array = getResources().getStringArray(R.array.game_dur_array);
        int first = Integer.valueOf(num_players_array[numPlayersPos]);
        int second = Integer.valueOf(num_bases_array[numBasesPos]);
        double third = Double.valueOf(game_dur_array[gameDurPos]);
        return;
        //sendToServer(first, second, third);
    }

//    public void GameScreen(View view) {
//        Intent begin = new Intent(this, GameScreen.class);
//        startActivity(begin);
//    }
}
