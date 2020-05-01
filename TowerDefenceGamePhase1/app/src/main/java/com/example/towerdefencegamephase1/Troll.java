package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

public class Troll extends Enemy {

    private int mspeed;
    private int mhealth;
    private boolean mdead;

    public Troll(float startingX, float startingY, Bitmap enemyBitmap) {
        super(startingX, startingY, enemyBitmap);
        mspeed = 9;
        mhealth = 20;
        mdead = false;
    }

    public void updateHealth(int damage) {
        mhealth -= damage;
        if (mhealth <= 0) {
            mdead = true;
        }
    }

    public boolean isDead() {
        return mdead;
    }

    @Override
    public int getSpeed(){ return mspeed;}
}
