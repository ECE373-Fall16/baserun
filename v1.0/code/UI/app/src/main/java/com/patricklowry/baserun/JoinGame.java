package com.patricklowry.baserun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JoinGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        //Intent intent = getIntent();
    }

    public void JoinRandom(View view) {
        Intent intent = new Intent(this, JoinRandom.class);
        startActivity(intent);
    }

    public void JoinByID(View view) {
        Intent intent = new Intent(this, JoinByID.class);
        startActivity(intent);
    }
}
