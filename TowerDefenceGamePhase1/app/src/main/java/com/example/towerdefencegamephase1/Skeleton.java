package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

public class Skeleton extends Enemy {

    private int mspeed;

    public Skeleton(float startingX, float startingY, Bitmap enemyBitmap) {
        super(startingX, startingY, enemyBitmap);
        mspeed = 12;
        health = 5;
    }


    @Override
    public int getSpeed(){ return mspeed;}
}
