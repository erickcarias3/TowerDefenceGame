package com.example.towerdefencegamephase1;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Hud {

    // Holds the text size and size of the screen.
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;

    //
    private int mLives = 10;
    private int mGold = 0;

    static int TOWER_ONE = 0;
    static int TOWER_TWO = 1;

    private ArrayList<Rect> controls;

    public Hud () {
        // Gets the screen size and assigns it to the appropriate height and width variable.
        mScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        mScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        // Sets the text size.
        mTextFormatting = mScreenWidth / 35;

        prepareControls();
    }

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

    public void draw(Canvas canvas, Paint paint) {
        // Drawing the HUD
        paint.setColor(Color.argb(255, 0, 0, 0));
        paint.setTextSize(mTextFormatting);

        canvas.drawText("Gold: " + mGold,
                mTextFormatting / 2, mTextFormatting, paint);

        canvas.drawText("Lives: " + mLives,
                mTextFormatting / 2, mTextFormatting + mTextFormatting, paint);

        drawControls(canvas, paint);

    }

    private void drawControls(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(100, 255, 255, 255));

        for(Rect r : controls) {
            canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
        }
        // Sets the colors black
        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    public void createSimpleHud(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(mTextFormatting);

        // Draw the message
        // We will give this an international upgrade soon
        //gameCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        canvas.drawText("Tap to Play",
                mScreenWidth / 2, mScreenHeight / 2, paint);
    }

    public void updateLives() {mLives--;}

    public void updateScore(int pointValue) {
        mGold += pointValue;
    }

    public int getScreenWidth() {return mScreenWidth;}

}
