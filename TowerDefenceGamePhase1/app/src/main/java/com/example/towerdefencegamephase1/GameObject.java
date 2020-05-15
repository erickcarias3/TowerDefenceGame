package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//towers and enmies will use the game object class to perform some of its core functions
public abstract class GameObject {
    Bitmap objectBitmap;
    protected Position location;

    public GameObject(float startingX, float startingY, Bitmap enemyBitmap){
        location = new Position();
        location.x = startingX;
        location.y = startingY;
        this.objectBitmap = enemyBitmap;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(objectBitmap,
                location.x - ((float) objectBitmap.getWidth()/2),
                (int) location.y - ((float) objectBitmap.getHeight()/2),
                null);
    }

    public void setPosition(Position newPosition){
        this.location.x = newPosition.x;
        this.location.y = newPosition.y;
    }

    public Position getLocationOnMap(){
        return location;
    }

}
