package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;

public class MapBackground extends GameGrid{

    private Bitmap map;
    private Path enemyPath;

    public MapBackground(Context context,DisplayManger displayManger, Canvas canvas, Dimesion mapSize){
        super(mapSize.getHeight(),mapSize.getWidth(), canvas.getWidth(), canvas.getHeight());
        map = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.map);

        map = Bitmap.createScaledBitmap(map,displayManger.getScreenWidth(),displayManger.getScreenHeight(),true);
        createPath();


    }

    private void createPath(){

        enemyPath = new Path();
        enemyPath.moveTo((float) gridCells[10][0].centerX() - getCellWidth()/2,(float) gridCells[10][0].centerY());

        enemyPath.lineTo((float) gridCells[10][3].centerX(), (float) gridCells[10][3].centerY());
        enemyPath.moveTo((float) gridCells[10][3].centerX(),(float) gridCells[10][3].centerY());

        enemyPath.lineTo((float) gridCells[4][3].centerX(),(float) gridCells[4][3].centerY());
        enemyPath.moveTo((float) gridCells[4][3].centerX(),(float) gridCells[4][3].centerY());

        enemyPath.lineTo((float) gridCells[4][7].centerX(),(float) gridCells[4][7].centerY());
        enemyPath.moveTo((float) gridCells[4][7].centerX(),(float) gridCells[4][7].centerY());

        enemyPath.lineTo((float) gridCells[12][7].centerX(),(float) gridCells[12][7].centerY());
        enemyPath.moveTo((float) gridCells[12][7].centerX(),(float) gridCells[12][7].centerY());

        enemyPath.lineTo((float) gridCells[12][12].centerX(),(float) gridCells[12][12].centerY());
        enemyPath.moveTo((float) gridCells[12][12].centerX(),(float) gridCells[12][12].centerY());

        enemyPath.lineTo((float) gridCells[8][12].centerX(),(float) gridCells[8][12].centerY());
        enemyPath.moveTo((float) gridCells[8][12].centerX(),(float) gridCells[8][12].centerY());

        enemyPath.lineTo((float) gridCells[8][19].centerX() + getCellWidth()/2,(float) gridCells[8][19].centerY());
        enemyPath.moveTo((float) gridCells[8][19].centerX() + getCellWidth()/2,(float) gridCells[8][19].centerY());



    }

    public Position followPath(Position startingPostion){
        if(startingPostion.x < 0 || startingPostion.y < 0) {
            startingPostion.x = (float) gridCells[10][0].centerX() - getCellWidth()/2 + 1;
            startingPostion.y = (float) gridCells[10][0].centerY();
            return startingPostion;
        }
        //else if(startingPostion.x  )



        return startingPostion;
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

        gameCanvas.drawPath(enemyPath, paint );
    }

}
