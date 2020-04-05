package com.example.towerdefencegamephase1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    GameView currentGameView;
    Button addTower, gameToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentGameView = findViewById(R.id.gameView);
        addTower = findViewById(R.id.addTower);
        gameToggle = findViewById(R.id.gameToggle);

        currentGameView.initializeCurrentView(getWindowManager(), this);

        gameToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGameView.toggleGame();
            }
        });

       addTower.setOnTouchListener(new CustomOnTouchListner(currentGameView));


    }

    @Override
    protected void onResume() {
        super.onResume();
        currentGameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentGameView.pause();
    }

}
