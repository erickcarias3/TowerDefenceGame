package com.example.towerdefencegamephase1;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

public class CustomOnTouchListner implements View.OnTouchListener {

    private GameView currentGame;

    public CustomOnTouchListner(GameView currentGame){
        this.currentGame = currentGame;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handleButtonAnimation(v, event);

        switch(v.getId()){
            case R.id.addTower:

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
