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
    private Context context;

    //two test objects
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
        mHUD = new HUD();
        this.context = context;

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

            // Draws the Array List of the enemies.
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

    private void createWave(){

        int currentWave = mHUD.getWave();
        int enemyHeight = gameMap.getCellHeight();
        int enemyWidth = gameMap.getCellWidth();

        int number_of_enemies_to_spawn = currentWave;

        Bitmap skeletonBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.skeleton);

        Bitmap snakeBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.snake);

        Bitmap trollBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.troll_1);

        skeletonBitmap = Bitmap.createScaledBitmap(skeletonBitmap,enemyHeight,enemyWidth,true);
        snakeBitmap = Bitmap.createScaledBitmap(snakeBitmap, enemyHeight, enemyWidth, true);
        trollBitmap = Bitmap.createScaledBitmap(trollBitmap, enemyHeight, enemyWidth, true);
/*
        Added for when we get wave counting working otherwise we just spawn a few of the different
        enemies.

        if(currentWave < 3){
            number_of_enemies_to_spawn = currentWave * 10;

            for (int i = 0; i < number_of_enemies_to_spawn; ++i) {
                spawnedEnemies.add(new Skeleton(-10, -10, skeletonBitmap));
            }
        } else if (currentWave >= 3 && currentWave < 7) {
            number_of_enemies_to_spawn = currentWave * 10;

            float number_of_skeletons = (float)number_of_enemies_to_spawn * 0.7f;
            float number_of_snakes = (float)number_of_enemies_to_spawn * 0.3f;

            for (int i = 0; i < number_of_skeletons; ++i) {
                spawnedEnemies.add(new Skeleton(-10, -10, skeletonBitmap));
            }

            for (int i = 0; i < number_of_snakes; ++i) {
                spawnedEnemies.add(new Skeleton(-10, -10, snakeBitmap));
            }
        } else {
            number_of_enemies_to_spawn = currentWave * 10;

            float number_of_skeletons = (float)number_of_enemies_to_spawn * 0.7f;
            float number_of_snakes = (float)number_of_enemies_to_spawn * 0.2f;
            float number_of_trolls = (float)number_of_enemies_to_spawn * 0.1f;

            for (int i = 0; i < number_of_skeletons; ++i) {
                spawnedEnemies.add(new Skeleton(-10, -10, skeletonBitmap));
            }

            for (int i = 0; i < number_of_snakes; ++i) {
                spawnedEnemies.add(new Skeleton(-10, -10, snakeBitmap));
            }

            for (int i = 0; i < number_of_trolls; ++i) {
                spawnedEnemies.add(new Skeleton(-10, -10, skeletonBitmap));
            }
        }

 */


        spawnedEnemies.add(new Skeleton(-10,-10, skeletonBitmap));
        spawnedEnemies.add(new Skeleton(-10,-10, skeletonBitmap));
        spawnedEnemies.add(new Skeleton(-10,-10, skeletonBitmap));
        spawnedEnemies.add(new Skeleton(-10,-10, skeletonBitmap));
        spawnedEnemies.add(new Skeleton(-10,-10, skeletonBitmap));
        spawnedEnemies.add(new Snake(-10,-10, snakeBitmap));
        spawnedEnemies.add(new Troll(-10,-10, trollBitmap));

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

        mHUD.updateTimer();

        if (spawnedEnemies.size() == 0)
            createWave();

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
