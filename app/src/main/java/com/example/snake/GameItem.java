package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public abstract class GameItem implements GameObject {


    //Contains drawing elements
    Screen s;

    //Number of frames for cooldown
    private final int COOLDOWN;

    //Number of frames till the GameItem disappears
    private final int VANISH;

    //Number of frames after despawning the GameItem will return
    protected int movesTillReturn;

    //number of  frames after spawning the GameItem will vanish
    protected int movesTillVanish;

    //The location of the GameItem
    protected Point location;

    //The bitmap representation of the GameItem
    protected Bitmap mBitmapItem;

    public GameItem() {
        COOLDOWN = 0;
        VANISH = 0;
    }

    public GameItem(Context context, ArrayList<GameItem> activeItems, Screen s,
                    int COOLDOWN, int VANISH) {
        this.COOLDOWN = COOLDOWN;
        this.VANISH = VANISH;
        movesTillReturn = COOLDOWN;
        movesTillVanish = VANISH;
        this.s = s;
        initializeBitmap(context);
        spawn(activeItems);
    }

    /**
     * Initializes the bitmapItem
     * @param context
     * @return the bitmapItem
     */
    public abstract void initializeBitmap(Context context);

    /**
     * Spawns the gameItem at an unoccupied position
     * @param activeItems the list of all items on the Screen
     */
    public void spawn(ArrayList<GameItem> activeItems) {
        if(activeItems.isEmpty()){
            spawn();
        }else {
            Point temp = new Point();
            Random random = new Random();

            //Choose two random values and place the apple
            //temp = new Point();
            temp.x = random.nextInt(s.NUM_BLOCKS_WIDE) + 1;
            temp.y = random.nextInt(s.mNumBlocksHigh - 1) + 1;
            for (GameItem g : activeItems) {
                if (temp.equals(g.getLocation())) {
                    spawn(activeItems);
                    return;
                }
            }
            location.set(temp.x, temp.y);
        }
    }

    /**
     * Spawns a GameItem, when there are none on the screen
     */
    protected void spawn(){
        Random random = new Random();
        int x = random.nextInt(s.NUM_BLOCKS_WIDE) + 1;
        int y = random.nextInt(s.mNumBlocksHigh - 1) + 1;
        location = new Point(x,y);
    }

    /**
     * @return number of frames remaining till cooldown is finished for the GameItem
     */
    protected int getCooldownRemaining(){
        return movesTillReturn;
    }

    protected int getStayRemaining(){
        return movesTillVanish;
    }

    /**
     * Reduces the number of frames remaining till Cooldown is complete by 1
     */
    public void reduceCooldown() {
        movesTillReturn--;
    }

    /**
     * Reduces the number of frames till GameItem vanishes by 1.
     */
    public void reduceStay() {
        movesTillVanish--;
    }

    /**
     * @return whether the GameItem should be despawned
     */
    public boolean despawn() {
        if(movesTillVanish == 0){
            movesTillVanish = VANISH;
            return true;
        }
        return false;
    }

    /**
     * In the situation where the snakehead interacts with the GameItem
     */
    public void reset(){
        movesTillReturn = COOLDOWN;
        movesTillVanish = VANISH;
    }

    /**
     * @return type of GameItem, the GameItem is in string representation
     */
    public String type(){
        if(this instanceof Food){
            return "Food";
        }else if(this instanceof Powerup){
            return "Powerup";
        }else if(this instanceof Obstacle) {
            return "Obstacle";
        }
        else{
            return "No Type";
        }
    }



    //Checks to see if movesTillReturn == 0

    /**
     * @return whether the GameItem should respawn
     */
    public boolean respawn() {
        if(movesTillReturn == 0){
            movesTillReturn = COOLDOWN;
            return true;
        }
        return false;
    }

    /**
     * @param snakeHead the location of the head of the snake
     * @return whether the snake's head has interacted with the GameObject
     */
    public boolean interact(Point snakeHead) {
        if(snakeHead.equals(getLocation())) {
            this.reset();
            return true;
        }
        return false;
    }

    /**
     * @return the location of the GameItem
     */
    public Point getLocation() {
        return location;
    }



    /**
     * draws the gameItem
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmapItem,
                location.x * s.blockSize, location.y * s.blockSize, s.mPaint);
    }

}
