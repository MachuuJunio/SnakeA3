package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


import java.util.ArrayList;

class SnakeGame extends SurfaceView implements Runnable, Frame{

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    volatile boolean mPaused = true;

    private SurfaceHolder mSurfaceHolder;

    //private StatManager statManager;

    private ScreenManager screenManager;
    private GameSound gameSound;
    // A snake ssss
    private Snake mSnake;

    private Screen s;
    private Context c;
    // And an apple
    private House mApple1;

    private House mApple2;
    private House mApple3;
    private House mApple4;


    private Canvas mCanvas;

    private GameObjectManager gManager;

    private ArrayList<GameObject> objects;

    /**
     * ArrayList consisting of all GameItems active on the board
     */
    private ArrayList<GameItem> activeItems;

    private ArrayList<GameItem> cooldownItems;


    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);
        c = context;
        s = new Screen(size);
        screenManager = new ScreenManager(s, context);

        gManager = new GameObjectManager(context, s);
        mSnake = new Snake(context, s);

        //used for drawing
        mSurfaceHolder = getHolder();
        //used for drawing
        mCanvas = new Canvas();
    }


    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset();


        // Get the apple ready for dinner
        gManager = new GameObjectManager(c, s);

        //mApple1.spawn();

        // Reset the mScore
        //statManager.reset();

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = FRAMES_PER_SECOND;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

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


    // Update all the game objects
    public void update() {
        // Move the snake
        mSnake.move();
        //Implements the gameLogic
        gManager.gameLogic(mSnake);
        //Checks if Snake died
        if(mSnake.die())
            mPaused = true;
    }

    // Do all the drawing
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            //Locks the Canvas
            mCanvas = mSurfaceHolder.lockCanvas();
            //Draws the screen, and background
            screenManager.drawScreen(mPaused, mSnake.stat, mCanvas);
            //Draws all GameItems
            gManager.drawGameItems(mCanvas);
            //Draws the snake
            mSnake.draw(mCanvas);
            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = motionEvent.getActionIndex();
        int x = (int) motionEvent.getX(i);
        int y = (int) motionEvent.getY(i);
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if(x>1900 && y>900){
                    if(mPaused){
                        mPaused = false;
                        return true;
                    }
                    else{
                        mPaused = true;
                        return true;

                    }
                }
                if (mPaused) {
                    mPaused = false;
                    newGame();
                    // Don't want to process snake direction for this tap
                    return true;
                }

                // Let the Snake class handle the input
                break;

            default:
                break;

        }
        return true;

    }

    @Override//for keyboard
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    mSnake.changeDirection(Snake.Heading.UP);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    mSnake.changeDirection(Snake.Heading.DOWN);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    mSnake.changeDirection(Snake.Heading.LEFT);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    mSnake.changeDirection(Snake.Heading.RIGHT);
                    break;
                case KeyEvent.KEYCODE_SPACE :
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        // Toggle the pause state
                        mPaused = !mPaused;

                        // Optionally, handle the game's running state
                        if (!mPlaying && !mPaused) {
                            resume();
                        } else if (mPaused) {
                            pause();
                        }

                        return true; // Key event handled

                    }
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

}