package com.example.towerdefencegamephase1;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    GameView currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentGame = findViewById(R.id.gameView);
        currentGame.initializeCurrentView(getWindowManager(), this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentGame.pause();
    }

}
