package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

public class Troll extends Enemy {

    private int mspeed;

    public Troll(float startingX, float startingY, Bitmap enemyBitmap) {
        super(startingX, startingY, enemyBitmap);
        mspeed = 8;
        health = 15;

    }

    @Override
    public int getSpeed(){ return mspeed;}
}
