package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class GameWorld {


    private GameMap gameMap;
    private Enemy testEnemy;
    private Tower testTower;

    public Position canvasPosition = new Position();

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

            testEnemy.draw(viewCanvas);

            drawTowers();

            // Draw some text while paused
             if(gamePaused){
                 displayPausedMessage();
             }

            // Unlock the gameCanvas and reveal the graphics for this frame
            viewSurfaceHolder.unlockCanvasAndPost(viewCanvas);
        }

    }

    public void createEnemy(Context context){

        int enemyHeight = gameMap.getCellHeight();
        int enemyWidth = gameMap.getCellWidth();

        Bitmap newEnemyBitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.skeleton);

        newEnemyBitmap = Bitmap.createScaledBitmap(newEnemyBitmap,enemyHeight,enemyWidth,true);

        testEnemy = new Enemy(-10,-10, newEnemyBitmap);

    }

    public void setTower(Position setPosition){
        Rect[] occupiedCells = gameMap.checkGrid(setPosition.x, setPosition.y);
        testTower.placeTower(occupiedCells);
        gameMap.invalidateGuideCells();
    }

    public void createTower(Context context, Position startingPosition){

        int towerHeight = gameMap.getCellHeight() * 2 ;
        int towerWidth = gameMap.getCellWidth();

        Bitmap newTowerBitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.tower);

        newTowerBitmap = Bitmap.createScaledBitmap(newTowerBitmap,towerHeight,towerWidth,true);

        startingPosition = translatesToGridCords(startingPosition.x, startingPosition.y);

        testTower = new Tower(startingPosition.x,startingPosition.y, newTowerBitmap);

    }

    public void updateCreatedTower(Position newPosition){
        testTower.setPosition(newPosition);
        gameMap.createGuideBox(newPosition);
    }

    public void drawTowers(){
        try{
            testTower.draw(viewCanvas);

        }
        catch(NullPointerException o){
            return;
        }
    }

    public Position translatesToGridCords(float x, float y){

        Position bitmapCoordinates = new Position();

        bitmapCoordinates.x = x - canvasPosition.x;
        bitmapCoordinates.y = y - canvasPosition.y;


        return bitmapCoordinates;
    }

    public void update(){

        if (testEnemy.location.x >= mHud.getScreenWidth())
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
