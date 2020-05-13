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
    public TowerManager defenceManager;

    //two test objects
    private Tower testTower;

    //array list of towers
    private ArrayList<Tower> allTowers = new ArrayList<>();

    // Array of enemies
    private ArrayList<Enemy> spawnedEnemies = new ArrayList<>();

    private ArrayList<Enemy> enemyHoldingList = new ArrayList<>();

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
        defenceManager = new TowerManager(context , gameMap);
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

            gameMap.drawCastle(viewCanvas);

            defenceManager.drawTowers(viewCanvas , allTowers);

            defenceManager.drawShots(viewCanvas);

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

        int number_of_enemies_to_spawn;

        Bitmap skeletonBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.skeleton);

        Bitmap snakeBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.snake);

        Bitmap trollBitmap = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.troll_1);

        skeletonBitmap = Bitmap.createScaledBitmap(skeletonBitmap,enemyHeight,enemyWidth,true);
        snakeBitmap = Bitmap.createScaledBitmap(snakeBitmap, enemyHeight, enemyWidth, true);
        trollBitmap = Bitmap.createScaledBitmap(trollBitmap, enemyHeight, enemyWidth, true);

        //Added for when we get wave counting working otherwise we just spawn a few of the different
        //enemies.

        if(currentWave < 3){
            number_of_enemies_to_spawn = currentWave * 2;

            for (int i = 0; i < number_of_enemies_to_spawn; ++i) {
                enemyHoldingList.add(new Skeleton(-10, -10, skeletonBitmap));
            }
        } else if (currentWave < 10) {
            number_of_enemies_to_spawn = currentWave * 2;

            float number_of_skeletons = (float)number_of_enemies_to_spawn * 0.7f;
            float number_of_snakes = (float)number_of_enemies_to_spawn * 0.3f;

            for (int i = 0; i < number_of_skeletons; ++i) {
                enemyHoldingList.add(new Skeleton(-10, -10, skeletonBitmap));
            }

            for (int i = 0; i < number_of_snakes; ++i) {
                enemyHoldingList.add(new Snake(-10, -10, snakeBitmap));
            }
        } else {
            number_of_enemies_to_spawn = 20;

            float number_of_skeletons = (float)number_of_enemies_to_spawn * 0.5f;
            float number_of_snakes = (float)number_of_enemies_to_spawn * 0.3f;
            float number_of_trolls = (float)number_of_enemies_to_spawn * 0.2f;

            for (int i = 0; i < number_of_skeletons; ++i) {
                enemyHoldingList.add(new Skeleton(-10, -10, skeletonBitmap));
            }

            for (int i = 0; i < number_of_snakes; ++i) {
                enemyHoldingList.add(new Snake(-10, -10, snakeBitmap));
            }

            for (int i = 0; i < number_of_trolls; ++i) {
                enemyHoldingList.add(new Troll(-10, -10, trollBitmap));
            }
        }
    }

    public void placeCreatedTower(Position setPosition){
        Rect[] occupiedCells = gameMap.checkGrid(setPosition.x, setPosition.y);
        defenceManager.setTower(occupiedCells,allTowers);
        gameMap.invalidateGuideCells();
    }

    public void createDefence(Context context, Position startingPosition, String typeOfDefence){
        startingPosition = translatesToGridCords(startingPosition.x, startingPosition.y);
        defenceManager.createTower(context, startingPosition , gameMap, typeOfDefence);

    }

    public void updateCreatedTower(Position newPosition){
        gameMap.createGuideBox(newPosition);
        defenceManager.updateCreatedTowerPosition(newPosition);
    }

    public Position translatesToGridCords(float x, float y){

        Position bitmapCoordinates = new Position();

        bitmapCoordinates.x = x - canvasPosition.x;
        bitmapCoordinates.y = y - canvasPosition.y;


        return bitmapCoordinates;
    }

    public void update(){

        /* Disabled for testing..
        if(mHUD.getLives() <= 0){
            // GameOver
            mHUD.resetGame();
            spawnedEnemies.clear();
            enemyHoldingList.clear();
        }
         */

        // checks the timer and checks if its okay to generate spawn list so it only happens once.
        if (mHUD.getTimer() == 20 && mHUD.getNewWave()) {
            mHUD.updateWave();
            createWave();
            mHUD.setNewWave(); // turns off wave creation.
        }
        mHUD.updateTimer();

        // Checking to see if it's okay to spawn another enemy and checking to see that there is an
        // enemy to spawn in the holding list.
        if (mHUD.getSpawn() && enemyHoldingList.size() > 0) {
            spawnedEnemies.add(enemyHoldingList.get(0));
            enemyHoldingList.remove(0);
            mHUD.setSpawn();
        }

        // Spawned enemies being checked to see if they went off screen, if they did they are
        // removed from the game.
        for( int i = 0; i < spawnedEnemies.size(); ++i) {
            if(spawnedEnemies.get(i).location.x >= mHUD.getScreenWidth()){
                spawnedEnemies.remove(spawnedEnemies.get(i));
                mHUD.updateLives();
            }
            // Checks if the enemy is dead, and if they are removes them from the game.
            else if(spawnedEnemies.get(i).isDead()) {
                spawnedEnemies.remove(spawnedEnemies.get(i));
            }
            else {
                continue;
            }
        }
        moveEnemy(); // does the enemy movement.


        defenceManager.checkTowerTargeting(allTowers,spawnedEnemies);

        defenceManager.updateShots();

        defenceManager.fireShots(allTowers);


    }

    public void moveEnemy(){

        // updates all of the enemies pathing.
        for( int i = 0; i < spawnedEnemies.size(); ++i) {
            gameMap.followPath(spawnedEnemies.get(i));
        }
    }

    public void displayPausedMessage(){
        mHUD.createPausedMessage(viewCanvas, mPaint);
    }

}
