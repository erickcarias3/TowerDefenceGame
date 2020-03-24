package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameView extends SurfaceView implements Runnable {


    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;

    // Is the game currently playing and or paused?
    private volatile boolean gamePlaying = false;
    private volatile boolean gamePaused = true;

    // Run at 10 frames per second
    final long TARGET_FPS = 10;
    // There are 1000 milliseconds in a second
    final long MILLIS_PER_SECOND = 1000;


    //context which the game will run
    private Context context;

    //objects in the gameWorld are stored here
    private GameWorld currentGame;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

    }

    public void initializeCurrentView(WindowManager displayWindow, Context context) {
        this.context = context;
        DisplayManger display = new DisplayManger(displayWindow);

        //create a canvas using a bitmap
        Bitmap myBitmap = Bitmap.createBitmap(display.getScreenWidth(), display.getScreenHeight(), Bitmap.Config.ARGB_8888);
        Canvas bitmapGameCanvas = new Canvas(myBitmap);

        currentGame = new GameWorld(context, display, bitmapGameCanvas, getHolder() );

        drawGame();
    }

    // Handles the game loop
    @Override
    public void run() {
        while (gamePlaying) {
            if(!gamePaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            drawGame();
        }
    }

    private void drawGame(){
        currentGame.draw(gamePaused);
    }

    // Check to see if it is time for an update
    private boolean updateRequired() {

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }

    private void update(){

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        gamePaused = !gamePaused;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (gamePaused) {


                    // Don't want to process snake direction for this tap
                    return true;
                }

                break;

            default:
                break;

        }
        return true;
    }

    // Stop the thread
    public void pause() {
        gamePlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        gamePlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

}
