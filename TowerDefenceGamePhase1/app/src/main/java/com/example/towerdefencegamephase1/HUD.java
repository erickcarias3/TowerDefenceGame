package com.example.towerdefencegamephase1;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;

public class HUD {

    // Holds the text size and size of the screen.
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;


    private int mLives = 10;
    private int mGold = 20;
    private int mWave = 0;
    private final int waveTimer = 20;
    private int mTimer = waveTimer;
    private boolean spawn = true;
    private boolean newWave = true;

    private long countdown_milliseconds = 1000;
    private long saved_milli_time = 0;


    public HUD() {
        // Gets the screen size and assigns it to the appropriate height and width variable.
        mScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        mScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        // Sets the text size.
        mTextFormatting = mScreenWidth / 35;

        //prepareControls();
    }

    /*
    private void prepareControls() {
        int buttonWidth = mScreenWidth / 15;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 95;

        Rect towerOne = new Rect(buttonPadding,
                mScreenHeight - (buttonHeight * 2) - buttonPadding,
                buttonWidth + buttonPadding,
                mScreenHeight - buttonHeight - buttonPadding);

        Rect towerTwo = new Rect(buttonPadding,
                mScreenHeight - (buttonHeight * 2) - buttonPadding,
                (buttonWidth + buttonPadding) * 2,
                mScreenHeight - buttonHeight - buttonPadding);

        controls = new ArrayList<>();
        controls.add(TOWER_ONE, towerOne);
        controls.add(TOWER_TWO, towerTwo);
    }
    */

    public void draw(Canvas canvas, Paint paint) {
        // Drawing the HUD
        paint.setColor(Color.WHITE);
        paint.setTextSize(mTextFormatting);

        canvas.drawText("Gold: " + mGold,
                mTextFormatting / 2, mTextFormatting, paint);

        canvas.drawText("Lives: " + mLives,
                mTextFormatting / 2, mTextFormatting + mTextFormatting, paint);

        canvas.drawText("Wave: " + mWave,
                mTextFormatting / 2, mTextFormatting * 3, paint);

        canvas.drawText("Time: " + mTimer,
               mTextFormatting / 2, mTextFormatting * 4, paint);

    }
/*
    private void drawControls(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(100, 255, 255, 255));

        for(Rect r : controls) {
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }
        // Sets the colors black
        paint.setColor(Color.argb(255, 255, 255, 255));
    }
*/
    public void createPausedMessage(Canvas canvas, Paint paint) {

        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(mTextFormatting);

        // Draw the message
        // We will give this an international upgrade soon
        //gameCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        canvas.drawText("Game Paused",
                mScreenWidth / 2, mScreenHeight / 2, paint);
    }

    public void updateLives() {mLives--;}

    public int getLives() {return mLives;}

    public void updateGold(int pointValue) { mGold += pointValue; }

    public void updateTimer() {

        long current_time_milliseconds = System.currentTimeMillis();
        countdown_milliseconds =
                countdown_milliseconds - (current_time_milliseconds - saved_milli_time);

       // Log.d("countdown", String.valueOf(countdown_milliseconds));

        if ( countdown_milliseconds <= 0) {
            mTimer--;
            countdown_milliseconds = 1000;
            spawn = true;
        }

        if (mTimer == 0) {
            resetTimer();
        }

        saved_milli_time = current_time_milliseconds;

    }

    public void resetTimer() {
        mTimer = waveTimer;
        countdown_milliseconds = 1000;
        newWave = true;
    }

    public int getScreenWidth() {return mScreenWidth;}

    public int getWave() { return mWave; }

    public void updateWave() { mWave++; }

    public void setSpawn() { spawn = false; }

    public boolean getSpawn() { return spawn; }

    public int getTimer() { return mTimer; }

    public boolean getNewWave() { return newWave;}

    public void setNewWave() {newWave = false;}

    public void resetGame() {
        resetTimer();
        spawn = true;
        mWave = 0;
        mLives = 10;
        mGold = 20;
        newWave = true;
    }
}
