package com.example.towerdefencegamephase1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    GameView currentGame;
    Button addTower, gameToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentGame = findViewById(R.id.gameView);
        addTower = findViewById(R.id.addTower);
        gameToggle = findViewById(R.id.gameToggle);

        currentGame.initializeCurrentView(getWindowManager(), this);

        gameToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.toggleGame();
            }
        });

       addTower.setOnTouchListener(new CustomOnTouchListner(currentGame));


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
