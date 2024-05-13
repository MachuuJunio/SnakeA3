package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;


import androidx.constraintlayout.utils.widget.MotionButton;

import java.util.ArrayList;

class SnakeGame extends SurfaceView implements Runnable{
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
     * Modification to this class
     * Create a private Stack caled Cooldown,
     * It will take in a new type called, activationTime
     * with two fields...
     * The gameObject name
     * the amount of moves.
     * //the idea is that once th
     *
     * Modification to other classes
     * GAMEOBJECT
     * change from an interface to an abstract class
     * Make the class implement a Comporable<GameObject>
     * private int cooldown //cooldown time, that will remain the same for any GameObject
     * getCooldown(): int   //in how many moves will object get spawned again
     * compareTo(): int     //compares objects based on cooldown time
     * getType() : String   //gets then type of GameObject
     *
     *
     * Modifications to the method
     * add a conditional statement that checks whether a stack is empty.
     * Declare a new private variable called counter, it counts the number of moves of the snake
     * add local variable interactedObject
     *
     * Creation of cooldown node in beginning
     *
     *
     * In general
     *
     * //Decrements the moves till GameObjects in cooldown get spawned
     * for (GameObject g: cooldown)
     *      g.reduceCooldown();
     *
     * //Decrements the moves till GameObjects in activeObjects get despawned
     * for(GameObject g: activeObjects)
     *      g.reduceDespawn();
     *
     * //checks whether there is any object that interacted with the snake head
     * if (snake.interact(activeObjects) != null)
     *
     *      //intializes loc variable w/GameObject that interacted with snake head
     *      interactedObject = snake.interact(activeObjects)
     *
     *      //Removes the GameObject from the arraylist of present GameObjects
     *      activeObjects.remove(interactedObject)
     *
     *      //Sorts the activeObjects based on despawn time
     *      Collection.sort(activeObjects, Comparator.comparing(GameObject: despawnTime())
     *
     *      //adds the interactedObject to the cooldown menu
     *      cooldown.add(interactedObject)
     *
     *      //Sorts the cooldown objects by cooldown time
     *      Collection.sort(cooldown, Comparator.comparing(GameObject::getCooldownTime())
     *
     *      //Checks to see if the interactedObject is a Powerup
     *      if (interactedObject.type() == "Powerup")
      *
     *          //Initializes private var to the interactedObject
     *          powerup = interactedObject
     *
     *      //Checks to see if the interactedObject is a consumable
     *      else if (interactedObject.type() == "Food")
     *
     *          //calls the consume method.
     *          snake.consume(interactedObject.addMass)
     *
     *          //plays the eat sound
     *          gameSound.play(gameSound.mEat_ID)
     *
     *          //adjusts the score
     *          adjustScore(interactedObject.addMass)
     *
     *      //Checks if the interactedObject is an Obstacle
     *      else  if (interactedObject.type() == "Obstacle")
     *           gameSound.play(gameSound.mCrash_ID);
     *           mPaused = true;
     *           adjustScore(0);
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

    private StatManager statManager;

    private ScreenManager screenManager;
    private GameSound gameSound;
    // A snake ssss
    private Snake mSnake;

    private Screen s;
    private Context c;
    // And an apple
    private Apple mApple;

    private Canvas mCanvas;

    private ArrayList<GameObject> objects;

    private ArrayList<GameItem> activeItems;

    private ArrayList<GameItem> cooldownItems;

    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);
        c = context;
        s = new Screen(size);
        //only used by draw function as of now
        statManager = new StatManager();
        //only used by draw function as of now
        screenManager = new ScreenManager(s, context);
        //used everywhere
        activeItems = new ArrayList<GameItem>();

        //used only at run() function
        gameSound = new GameSound(context);

        // Initialize the drawing objects
        //used for drawing
        mSurfaceHolder = getHolder();
        //used for drawing
        mCanvas = new Canvas();

        // Call the constructors of our two game objects
        //used everywhere
        mSnake = new Snake(context, s);
        //used everywhere
        mApple = new Apple(context, activeItems, s);

        //Construct ArrayList for GameObjects
        activeItems.add(mApple);
        objects = new ArrayList<>();
        objects.add(mApple);
        objects.add(mSnake);

    }


    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset();

        // Get the apple ready for dinner
        mApple.spawn();

        // Reset the mScore
        statManager.reset();

        statManager.toggleDead = false;

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
                while(statManager.toggleDead){
                    drawGameOverScreen();
                    draw();
                }
            }
            draw();
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
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
        statManager.incrementMoveCount();

        // Did the head of the snake eat the apple?
        if(mSnake.checkDinner(mApple.getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();

            // Add to  mScore
            statManager.incrementScore();
            // Play a sound
            gameSound.play(gameSound.mEat_ID);
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            gameSound.play(gameSound.mCrash_ID);
            mPaused = true;
            statManager.toggleDead = true;
        }

    }



    // Do all the drawing
    // Do all the drawing
    public void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Common drawing logic, like drawing the background, score, etc.
            screenManager.drawScreen(mPaused, statManager, mCanvas);

            // Draw the apple, the snake
            for(GameObject g : objects){
                g.draw(mCanvas);
            }

            // Draw "PAUSE" text only when the game is not paused (i.e., it's running)
            if (!mPaused && !statManager.toggleDead) {
                screenManager.drawPause(mCanvas);  // Method to draw "PAUSE"
            }

            // Draw "RESUME" text only when the game is paused
            if (mPaused && !statManager.toggleDead) {
                screenManager.drawResume(mCanvas);  // Method to draw "RESUME"
            }

            // Unlock the canvas and post the drawing to the screen
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }


    public void drawGameOverScreen(){
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            screenManager.drawGameOver(mCanvas);



            // Draw the apple, the snake
            for (GameObject g : objects) {
                g.draw(mCanvas);
            }

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