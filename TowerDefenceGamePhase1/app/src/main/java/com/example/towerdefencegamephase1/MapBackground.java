package com.example.towerdefencegamephase1;

import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class MapBackground extends GameGrid{

    private Bitmap map;
    private RoadMap enemyPath;

    public MapBackground(Context context,DisplayManger displayManger, Canvas canvas, Dimesion mapSize){
        super(mapSize.getHeight(),mapSize.getWidth(), canvas.getWidth(), canvas.getHeight());
        map = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.map);

        map = Bitmap.createScaledBitmap(map,displayManger.getScreenWidth(),displayManger.getScreenHeight(),true);

        enemyPath = new RoadMap(gridCells, getCellWidth());
    }

    public void followPath(Enemy enemy){

        if(enemy.getLocationOnMap().x < 0 || enemy.getLocationOnMap().y < 0){
            enemy.setPosition(enemyPath.getStart());
        }
        else{
            enemy.setNextTurn(enemyPath.follow(enemy));
        }

    }



    public Bitmap getMap() {
        return map;
    }

    public void draw(Canvas gameCanvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        gameCanvas.drawBitmap(map,0,0, null );
        drawGrid(gameCanvas);

        gameCanvas.drawPath(enemyPath.getPath(), paint );
    }



}
