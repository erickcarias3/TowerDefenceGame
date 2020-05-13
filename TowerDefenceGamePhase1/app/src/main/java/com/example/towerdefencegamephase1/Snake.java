package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

public class Snake extends Enemy{
    private int mspeed;

    public Snake(float startingX, float startingY, Bitmap enemyBitmap) {
        super(startingX, startingY, enemyBitmap);
        mspeed = 16;
        health = 5;
    }

    @Override
    public int getSpeed(){ return mspeed;}
}
