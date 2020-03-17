package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
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

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // Objects for drawing
    private Canvas gameCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private DisplayManger display;
    private GameGrid grid;
    private Context context;
    private MapBackground gameMap;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        // Initialize the drawing objects
        gameCanvas = new Canvas();
        mSurfaceHolder = getHolder();
        mPaint = new Paint();


    }

    public void initializeCurrentView(WindowManager displayWindow, Context context) {
        this.context = context;
        display = new DisplayManger(displayWindow);

        // Work out how many pixels each block is
        int blockSize = display.getScreenWidth() / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = display.getScreenHeight()/ blockSize;

        Bitmap myBitmap = Bitmap.createBitmap(display.getScreenWidth(), display.getScreenHeight(), Bitmap.Config.ARGB_8888);

        gameCanvas = new Canvas(myBitmap);

        grid = new GameGrid(10,10, gameCanvas.getWidth(), gameCanvas.getHeight());
        gameMap = new MapBackground(context, display);

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

        // Get a lock on the gameCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            gameCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            gameCanvas.drawColor(Color.argb(255, 26, 128, 182));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            gameCanvas.drawBitmap(gameMap.getMap(),0,0, null );

            grid.drawGrid(gameCanvas);


            // Draw some text while paused
            if(gamePaused){

                // Set the size and color of the mPaint for the text
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(150);

                // Draw the message
                // We will give this an international upgrade soon
                //gameCanvas.drawText("Tap To Play!", 200, 700, mPaint);
                gameCanvas.drawText("tap to play",
                        200, 500, mPaint);
            }


            // Unlock the gameCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(gameCanvas);
        }

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
