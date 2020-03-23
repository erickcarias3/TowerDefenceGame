package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy {

    protected Position locationOnMap;
    Bitmap enemyBitmap;

    public Enemy(float startingX, float startingY, Bitmap enemyBitmap){
        locationOnMap.x = startingX;
        locationOnMap.y = startingY;
        this.enemyBitmap = enemyBitmap;
    }

    public void updatePosition(float x, float y){

        locationOnMap.x = x;
        locationOnMap.y = y;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(enemyBitmap,locationOnMap.x,locationOnMap.y,null);
    }
}
