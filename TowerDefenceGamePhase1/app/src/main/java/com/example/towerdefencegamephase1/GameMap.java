package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameMap extends GameGrid{

    private Bitmap map;
    private Bitmap castle;
    private GamePath enemyPath;
    private DisplayManger displayManger;

    public GameMap(Context context, DisplayManger displayManger, Canvas canvas, Dimesion mapSize) {
        super(mapSize.getHeight(),mapSize.getWidth(), canvas.getWidth(), canvas.getHeight());
        map = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.map);

        map = Bitmap.createScaledBitmap(map,
                displayManger.getScreenWidth(),
                displayManger.getScreenHeight(),
                true);

        castle = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.castle);

        castle = Bitmap.createScaledBitmap(castle,
                displayManger.getScreenWidth()/10,
                displayManger.getScreenHeight()/2,
                true);

        this.displayManger = displayManger;
        enemyPath = new GamePath(gridCells, getCellWidth());
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

        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        gameCanvas.drawBitmap(map,0,0, null );

        //gameCanvas.drawPath(enemyPath.getPath(), paint );

       // gameCanvas.drawBitmap(castle,displayManger.getScreenWidth() - castle.getWidth() + 10,displayManger.getScreenHeight() - (castle.getHeight() + getGridHeight() * 9) ,null);

        //drawGrid(gameCanvas);

        //gameCanvas.drawPath(enemyPath.getPath(), paint );
    }

    public void drawCastle(Canvas gameCanvas){
        gameCanvas.drawBitmap(castle,displayManger.getScreenWidth() - castle.getWidth() + 10,displayManger.getScreenHeight() - (castle.getHeight() + getGridHeight() * 9) ,null);
    }

    public GamePath getPath(){
        return enemyPath;
    }
}
