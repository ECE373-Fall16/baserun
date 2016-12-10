package com.patricklowry.baserun;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GameNetwork net = new GameNetwork();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        net.connect();
        setContentView(R.layout.activity_main);
        String versionName = "Version";
        try {
            PackageInfo pkg = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = versionName + " " + pkg.versionName;
        }
        catch (PackageManager.NameNotFoundException error) {
            error.printStackTrace();
        }
        TextView version = (TextView) findViewById(R.id.textView2);
        version.setText(versionName);
    }

    public void JoinGame(View view) {
        Intent join = new Intent(this, JoinGame.class);
        startActivity(join);
    }

    public void CreateGame(View view) {
        Intent create = new Intent(this, CreateGame.class);
        startActivity(create);
    }

    public void Rules(View view) {
        Intent rules = new Intent(this, Rules.class);
        startActivity(rules);
    }
}
