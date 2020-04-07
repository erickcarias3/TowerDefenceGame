package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Tower extends GameObject {
    Rect[] occupiedCells = new Rect[2];

    public Tower(float startingX, float startingY, Bitmap towerBitmap){
        super(startingX, startingY, towerBitmap);
    }

    public void placeTower(Rect[] occupiedCells) {
        this.occupiedCells = occupiedCells;
        Position newPosition = new Position(occupiedCells[1].centerX() , occupiedCells[1].centerY());
        setPosition(newPosition);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(objectBitmap, location.x - ((float) objectBitmap.getWidth()/2), (int) location.y - ((float) objectBitmap.getHeight()/4),null);
    }
}
