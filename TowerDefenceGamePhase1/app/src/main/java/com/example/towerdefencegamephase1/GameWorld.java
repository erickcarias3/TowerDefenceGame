package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameWorld {


    private GameMap gameMap;
    private Enemy testEnemy;

    // Objects for drawing
    private Canvas viewCanvas;
    private SurfaceHolder viewSurfaceHolder;
    private Paint mPaint;

    // The size in segments of the playable area
    private Dimesion mapDimensions;

    // The Heads up Display
    private Hud mHud;


    public GameWorld(Context context, DisplayManger display, Canvas canvas, SurfaceHolder surfaceHolder){
       //initialize variables
        viewCanvas = canvas;
        viewSurfaceHolder = surfaceHolder;
        mapDimensions = new Dimesion(20,20);
        mPaint = new Paint();
        gameMap = new GameMap(context, display, viewCanvas, mapDimensions);
        createEnemy(context);
        mHud = new Hud();

    }

    public void draw(Boolean gamePaused){
        // Get a lock on the gameCanvas
        if (viewSurfaceHolder.getSurface().isValid()) {

            viewCanvas = viewSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            viewCanvas.drawColor(Color.argb(255, 26, 128, 182));

            //draw the background map and grid
            gameMap.draw(viewCanvas);

            mHud.draw(viewCanvas, mPaint);

            //draw the background map and grid

            // Draw some text while paused
             testEnemy.draw(viewCanvas);

             if(gamePaused){
                 displayPausedMessage();
             }

            // Unlock the gameCanvas and reveal the graphics for this frame
            viewSurfaceHolder.unlockCanvasAndPost(viewCanvas);
        }

    }

    /*public void createSimpleHUD(){
        // Set the size and color of the mPaint for the text
        mPaint.setColor(Color.argb(255, 255, 255, 255));
        mPaint.setTextSize(150);

        // Draw the message
        // We will give this an international upgrade soon
        //gameCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        viewCanvas.drawText("Tap to Play",
                200, 500, mPaint);
    }

     */

    public void createEnemy(Context context){

        int enemyHeight = gameMap.getCellHeight();
        int enemyWidth = gameMap.getCellWidth();

        Bitmap newEnemyBitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.skeleton);

        newEnemyBitmap = Bitmap.createScaledBitmap(newEnemyBitmap,enemyHeight,enemyWidth,true);

        testEnemy = new Enemy(-10,-10, newEnemyBitmap);

    }

    public void update(){
        if( testEnemy.location.x >= mHud.getScreenWidth())
            mHud.updateLives();
        moveEnemy();

    }

    public void moveEnemy(){
        gameMap.followPath(testEnemy);
    }

    public void displayPausedMessage(){
        mHud.createPausedMessage(viewCanvas, mPaint);
    }
}
