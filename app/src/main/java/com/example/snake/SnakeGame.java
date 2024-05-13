package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.ArrayList;

class SnakeGame extends SurfaceView implements Runnable, Frame{
    /**
     * Class splitting
     *
     * 1. Frame (Class in charge of running the program
     *              and notifying the time of an update)
     *
     * 2. FrameManager (Class in charge of updating the game)
     *      i. Checks if the snake head interacted with any object
     *         ,if true checks object type
     *              1. Passes the object to class GameObjectManager
     *      ii. Checks to see if Powerup is active
     *              1.If active, enables the desired effect.
     *      iii. Checks to see if Obstacle is active
     *              1.If active, enables the desired effect.
     *
     * 3. GameObjectManager (Class in charge of maintaining all
     *                       GameObjects in the game)
     *      i.Seperates objects in ArrayLists of activeObjects, cooldownObjects, allObjects
     *              1. activeObjects :      the objects that are visible on
     *                                      the screen at the frame
     *              2. cooldownObjects :    objects that need to cooldown
     *                                      before appearing on the screen
     *              3. allObjects:          As implied contains all objects
     *                                      that can be in the game
     *      ii.Recieves an interactedObject, and reveals the actions that must be taken
     *      iii. When this.update() is called all the arrayLists are updated.
     *      iv. Assigns all sounds of the different types of objects in the constructor.
     *
     * 4. Screen   (Class in charge of storing drawing objects and
     *              screen dimensions)
     *      i.Encapsulates all fields that are decided in runtime.
     *
     * 5. ScreenManager  (Class in charge of drawing the Screen)
     *      i. Using an instance of the Screen
     *              1. draws the background
     *              2. draws the resume/pause button + names
     *              3. draws score
     *
     *  6. StatManager (Class in charge of statistics, and additional spawning)
     *      i. Stores attributes of the game
     *              1. score
     *              2. numMoves
     *      ii. Responsible for the additional spawning of items
     *          based on the fields.
     *
     *  7. GameItem (Abstract class that denotes key properties of items)
     *
     *  8. Food extends GameItem (Encapsulates type Food)
     *
     *  9. Powerup extends GameItem
     *
     *  10. Obstacle extends GameItem
     *
     *
     */

    /**
     * In the future update should consider if snakehead, interact
     * Ideas
     * There should be more negative items added to the game as the score increases.
     * The cooldown time will be the same for every object!
     * It's best to keep the GameObject as an interface... wait actually that's dumb
     * it doesn't matter because the SnakeGame has a dependency relationship with GameObject
     *
     * In update()
     *
     * //Decrements the moves till GameItems in cooldown get spawned
     * while (!cooldown.isEmpty())
     *      for (GameItem g: cooldown)
     *          g.reduceCooldown();
     *
     * //Tells if there was an intersection with the snake head
     * GameItem interactedItem = null;
     *
     * //Decrements the moves till GameItems in activeObjects get despawned
     * while(!activeItems.isEmpty)
     *      for(GameItem g: activeItems)
     *          g.reduceDespawn();
     *          if(g.interact(snakeHead))
     *              interactedItem = g.reset();
     *
     *
     * //checks whether there is any object that interacted with the snake head
     * if (interactedItem != null)
     *
     *      //Removes the GameObject from the arraylist of present GameObjects
     *      activeItems.remove(interactedItem)
     *
     *      //Sorts the activeItems based on despawn time
     *      Collection.sort(activeItems, Comparator.comparing(GameItem:: getStayRemaining())
     *
     *      //adds the interactedItem to the cooldown menu
     *      cooldown.add(interactedItem)
     *
     *      //Sorts the cooldown objects by cooldown time
     *      Collection.sort(cooldown, Comparator.comparing(GameItem::getCooldownRemaining())
     *
     *      //Checks to see if the interactedObject is a Powerup
     *      if (interactedItem.type() == "Powerup")
     *
     *          //Initializes private var to the interactedObject
     *          powerup = interactedItem
     *
     *          //adds the interactedItem to the snake.
     *          mSnake.addPowerup(powerup)
     *
     *      //Checks to see if the interactedObject is a consumable
     *      else if (interactedItem.type() == "Food")
     *
     *          //calls the consume method.
     *          snake.consume(interactedItem.MASS_GAIN)
     *
     *          //plays the eat sound
     *          gameSound.play(gameSound.mEat_ID)
     *
     *          //adjusts the score
     *          stat.addScore(interactedItem.SCORE_GAIN)
     *
     *      //Checks if the interactedObject is an Obstacle
     *      else  if (interactedItem.type() == "Obstacle")
     *           /adds the obstacle to the snake.
     *           obstacles.add(interactedItem)
     *           gameSound.play(gameSound.mCrash_ID);
     *           mPaused = true;
     *
     * //Checks if the powerup is non null value, and if timeup() returns true
     * if( powerup != null && powerup.timeUp())
     *      powerup = null;
     * else
     *      //activates the effect of the powerup, params are undecides as of now...
     *      powerup.activateEffect(this)
     *      gameSound.play(gameSound.mPowerup_ID)
     *
     *
     * //Loops until the first object in the cooldown list, returns false for respawn()
     * while(cooldown.get(0).respawn())
     *      //initialize the readyObject as the object ready to respawn
     *      GameObject readyObject = cooldown.remove(0)
     *      //adds the readyObject to the activeObjects
     *      activeObjects.add(readyObject)
     *      //sorts the activeObjects based on despawn time
     *      Collection.sort(activeObjects, Comparator.comparing(GameObject: despawnTime())
     *
     * while(activeObjects.get(0).despawn())
     *      //Initializes the recycleObject as the object that is ready to despawn
     *      GameObject recycleObject = activeObjects.remove(0)
     *      //Add the recycleObject to the cooldown object
     *      cooldown.add(recycleObject)
     *      //sorts the cooldown objects based on the cooldown time
     *      Collection.sort(cooldown, Comarator.comparing(GameObject:: getCooldownTime())
     *
     *
     *
     *
     * public void reduceCooldown(ArrayList<GameObject> objects){
     *      for(GameObject g: objects){
     *          g.reduceCooldown();
     *      }
     * }
     *
     * public void reduceDespawn(ArrayList<GameObject> objects){
     *      ArrayList<Game
     *      for(GameObject g: objects){
     *          g.reduceDespawn();
     *      }
     * }
     *
     * For Food
     * snake.interact() : boolean   //uneccessary
     * snake.interact(objects) : GameObject   //
     * The returned GameObject, should call its interact(s: Snake) method
     * The GameObject should then call its respawn()
     *
     * For Powerup
     * snake.interact() : boolean
     * snake.interact(objects) : GameObject
     * set returned value equal to interactedObject
     * Check using GameObject.type(interactedObject) to see what ..
     * type the returned GameObject is
     * If type is Powerup, then do the following
     * Set private int counter = interactedObject.getMoveCount() + 1
     * Delete the GameObject from the ArrayList
     * Initialize the private variable powerup with interactedObject
     * If powerup variable was empty then
     *
     *
     * Each concrete GameObject class should have a ...
     * respawn(), can be a few seconds delay or whatnot.
     */

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
                /**
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
                 **/
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