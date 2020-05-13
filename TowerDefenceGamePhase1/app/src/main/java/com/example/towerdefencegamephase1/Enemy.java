package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

public class Enemy extends GameObject {

    protected int health;
    private int nextTurn = 1;
    private boolean mdead = false;

    public Enemy(float startingX, float startingY, Bitmap enemyBitmap){
        super(startingX, startingY, enemyBitmap);
    }

    public int getNextTurn(){
        return nextTurn;
    }

    public void setNextTurn(int nextTurn){
        this.nextTurn = nextTurn;
    }

    public int getSpeed() {return 10;}

    public boolean isDead() {
        return mdead;
    }

    public void takeShot(){
        health--;
        if(health < 0){
            System.out.println("DEAD ENEMY");
            mdead = true;
        }
    }

}
