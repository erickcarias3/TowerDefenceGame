package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class GameWorld {


    private GameMap gameMap;

    //two test objects
    private Enemy testEnemy;
    private Tower testTower;

    //array list of towers
    private ArrayList<Tower> allTowers = new ArrayList<>();

    // Array of enemies
    private ArrayList<Enemy> spawnedEnemies = new ArrayList<>();

    public Position canvasPosition = new Position();

    // Objects for drawing
    private Canvas viewCanvas;
    private SurfaceHolder viewSurfaceHolder;
    private Paint mPaint;

    // The size in segments of the playable area
    private Dimesion mapDimensions;

    // The Heads up Display
    private HUD mHUD;


    public GameWorld(Context context, DisplayManger display, Canvas canvas, SurfaceHolder surfaceHolder){
       //initialize variables
        viewCanvas = canvas;
        viewSurfaceHolder = surfaceHolder;
        mapDimensions = new Dimesion(20,20);
        mPaint = new Paint();
        gameMap = new GameMap(context, display, viewCanvas, mapDimensions);
        createEnemy(context);
        mHUD = new HUD();

    }

    public void draw(Boolean gamePaused){
        // Get a lock on the gameCanvas
        if (viewSurfaceHolder.getSurface().isValid()) {

            viewCanvas = viewSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            viewCanvas.drawColor(Color.argb(255, 26, 128, 182));

            //draw the background map and grid
            gameMap.draw(viewCanvas);

            mHUD.draw(viewCanvas, mPaint);

            for( int i = 0; i < spawnedEnemies.size(); ++i) {
                spawnedEnemies.get(i).draw(viewCanvas);
            }

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

        spawnedEnemies.add(new Skeleton(-10,-10, newEnemyBitmap));
        spawnedEnemies.add(new Skeleton(-12,-11, newEnemyBitmap));
        spawnedEnemies.add(new Skeleton(-13,-9, newEnemyBitmap));
        spawnedEnemies.add(new Skeleton(-15,-11, newEnemyBitmap));
        spawnedEnemies.add(new Skeleton(-17,-10, newEnemyBitmap));
        spawnedEnemies.add(new Skeleton(-19,-9, newEnemyBitmap));
        spawnedEnemies.add(new Skeleton(-20,-11, newEnemyBitmap));

    }

    public void setTower(Position setPosition){
        Rect[] occupiedCells = gameMap.checkGrid(setPosition.x, setPosition.y);
        testTower.placeTower(occupiedCells);
        allTowers.add(testTower);
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
            for(Tower tower: allTowers){
                tower.draw(viewCanvas);
            }
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

        for( int i = 0; i < spawnedEnemies.size(); ++i) {
            if(spawnedEnemies.get(i).location.x >= mHUD.getScreenWidth()){
                mHUD.updateLives();
            }
        }
        moveEnemy();

    }

    public void moveEnemy(){

        for( int i = 0; i < spawnedEnemies.size(); ++i) {
            gameMap.followPath(spawnedEnemies.get(i));
        }
    }

    public void displayPausedMessage(){
        mHUD.createPausedMessage(viewCanvas, mPaint);
    }

}
