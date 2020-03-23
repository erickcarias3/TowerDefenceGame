package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

public class GameWorld {
    private MapBackground gameMap;
    private Enemy testEnemy;

    public GameWorld(Context context, DisplayManger display, Canvas canvas, Dimesion mapSize){
        gameMap = new MapBackground(context, display,canvas,mapSize);

    }

    public void draw(Canvas canvas){

        // Fill the screen with a color
        canvas.drawColor(Color.argb(255, 26, 128, 182));

        //draw the background map and grid
        gameMap.draw(canvas);

    }

    public void createEnemy(Context context){

        int enemyHeight = gameMap.getCellHeight();
        int enemyWidth = gameMap.getCellWidth();

        Bitmap newEnemy = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.map);

        newEnemy = Bitmap.createScaledBitmap(newEnemy,enemyHeight,enemyWidth,true);

        //testEnemy = newEnemy();

    }

    public void update(){

    }


}
