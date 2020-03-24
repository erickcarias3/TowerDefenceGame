package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

public class Enemy extends GameObject {

    public Enemy(float startingX, float startingY, Bitmap enemyBitmap){
        locationOnMap = new Position();
        locationOnMap.x = startingX;
        locationOnMap.y = startingY;
        this.objectBitmap = enemyBitmap;
    }

    public void updatePosition(float x, float y){
        locationOnMap.x = x;
        locationOnMap.y = y;
    }
    public void setPosition(Position newPosition){
        this.locationOnMap = newPosition;
    }

    public Position getLocationOnMap(){
        return locationOnMap;
    }

}
