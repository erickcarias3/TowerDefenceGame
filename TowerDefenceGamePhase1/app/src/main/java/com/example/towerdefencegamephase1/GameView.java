package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
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
    private volatile boolean drawingTower = false;



    // Run at 10 frames per second
    final long TARGET_FPS = 10;
    // There are 1000 milliseconds in a second
    final long MILLIS_PER_SECOND = 1000;

    //context which the game will run
    private Context context;

    // The sound engine
    private SoundEngine mSoundEngine;

    //objects in the gameWorld are stored here
    private GameWorld currentGameWorld;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        // Chooses the appropriate Sound Engine strategy.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mSoundEngine = new SoundEngine(new PostLollipopSoundEngine(context));
        else
            mSoundEngine = new SoundEngine(new PreLollipopSoundEngine(context));
    }

    public void initializeCurrentView(WindowManager displayWindow, Context context) {
        this.context = context;
        DisplayManger display = new DisplayManger(displayWindow);

        //create a canvas using a bitmap
        Bitmap myBitmap = Bitmap.createBitmap(display.getScreenWidth(), display.getScreenHeight(), Bitmap.Config.ARGB_8888);
        Canvas bitmapGameCanvas = new Canvas(myBitmap);

        currentGameWorld = new GameWorld(context, display, bitmapGameCanvas, getHolder() );

        drawGame();
    }

    // Handles the game loop
    @Override
    public void run() {
        while (gamePlaying) {
            if(!gamePaused | drawingTower) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }
            drawGame();
        }
    }

    private void drawGame(){
        currentGameWorld.draw(gamePaused);

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
        currentGameWorld.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

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


    public void addTowerButtonLogic(MotionEvent event){
        if(!drawingTower){
            drawingTower = true;
        }

    }

    public void toggleGame(){
        gamePaused = !gamePaused;
    }

    // Stop the thread
    public void pause() {

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
