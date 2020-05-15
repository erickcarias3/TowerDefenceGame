package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

//a basic enemy class that hold information and methods that each enemy should implement
public class Enemy extends GameObject {

    protected int health;

    //a turn is an index in the turn array that is stored inside the path object
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
