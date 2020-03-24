package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
    Bitmap objectBitmap;
    protected Position locationOnMap;

    public void draw(Canvas canvas){
        canvas.drawBitmap(objectBitmap,locationOnMap.x,locationOnMap.y,null);
    }
}
