package com.patricklowry.baserun;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    }

    public void GameScreen(View view) {
        //if(joined != null){
            Intent begin = new Intent(this, Gameplay.class);
            EditText id_text = (EditText) findViewById(R.id.editText);
            String id = id_text.getText().toString();
            if (!id.isEmpty()) {
                Bundle params = new Bundle();
                params.putString("EXTRA_ID", id);
                params.putParcelable("EXTRA_GAME", (Parcelable) joined);
                begin.putExtras(params);
                startActivity(begin);
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Game ID cannot be blank.";
                int duration = Toast.LENGTH_LONG;
                Toast.makeText(context, text, duration).show();
            }
        //}
    }
}
