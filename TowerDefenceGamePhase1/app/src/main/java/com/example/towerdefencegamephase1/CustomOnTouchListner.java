package com.example.towerdefencegamephase1;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

//this is a custom touch listener class to implement the drag and drop feature on the buttons
public class CustomOnTouchListner implements View.OnTouchListener {

    private GameView currentGame;

    public CustomOnTouchListner(GameView currentGame){
        this.currentGame = currentGame;
    }

    //sends information to the current game about which button is pressed
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handleButtonAnimation(v, event);

        switch(v.getId()){
            case R.id.addTower:
                    currentGame.addDefenceButtonLogic(event,"tower");
                break;
            case R.id.addCatapult:
                    currentGame.addDefenceButtonLogic(event,"catapult");
                break;
            case R.id.addArcher:
                currentGame.addDefenceButtonLogic(event,"archer");
                break;

        }
        return true;
    }
    public void handleButtonAnimation(View view, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                view.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
    }

}
