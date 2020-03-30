package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
    Bitmap objectBitmap;
    protected Position location;

    public void draw(Canvas canvas){
        canvas.drawBitmap(objectBitmap, location.x, location.y,null);
    }
}
