package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public abstract class GameItem implements GameObject, Frame {

    /**
     * Would be best to also add a time for despawning
     */

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

    public GameItem(Context context, Screen s,
                    int COOLDOWN, int VANISH) {
        this.COOLDOWN = COOLDOWN * FRAMES_PER_SECOND;
        this.VANISH = VANISH * FRAMES_PER_SECOND;
        movesTillReturn = this.COOLDOWN;
        movesTillVanish = this.VANISH;
        this.s = s;
        initializeBitmap(context);
        //hides apple
        location = new Point(-10,-10);
        //spawn(activeItems);
    }

    /**
     * Initializes the bitmapItem
     * @param context
     * @return the bitmapItem
     */
    public abstract void initializeBitmap(Context context);

    /**
     * Sets the spawn location of GameItem at an unoccupied position
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
     * Sets the spawn location of GameItem when none are on the Screen
     */
    protected void spawn(){
        Random random = new Random();
        int x = random.nextInt(s.NUM_BLOCKS_WIDE) + 1;
        int y = random.nextInt(s.mNumBlocksHigh - 1) + 1;
        location = new Point(x,y);
    }

    /**
     * @return number of frames remaining till GameItem will return to the screen
     */
    protected int getCooldownRemaining(){
        return movesTillReturn;
    }

    /**
     * @return number of frames remaining till GameItem automatically disappears from screen
     */
    protected int getStayRemaining(){
        return movesTillVanish;
    }

    /**
     * Reduces the number of frames remaining till GameItem will return to the screen
     */
    public void reduceCooldown() {
        movesTillReturn--;
        Log.d("GameItem", "Reduced cooldown: " + movesTillReturn);
    }

    /**
     * Reduces the number of frames till GameItem disappears from the screen
     */
    public void reduceStay() {
        movesTillVanish--;
        Log.d("GameItem", "Reduced stay: " + movesTillVanish);
    }

    /**
     * @return determines whether the GameItem should disappear from the screen
     * in current frame
     */
    public boolean despawn() {
        if(movesTillVanish <= 0){
            movesTillVanish = VANISH;
            Log.d("GameItem", "Despawning item: " + this);
            return true;
        }
        return false;
    }

    /**
     * Resets the default number of frames to dissappear for GameItem,
     * if the GameItem interacts with the snake head before it can
     * automatically despawn
     */
    public void reset(){
        movesTillReturn = COOLDOWN;
        movesTillVanish = VANISH;
    }

    /**
     * @return determines whether GameItem should return to the screen at current frame
     */
    public boolean respawn() {
        if(movesTillReturn == 0){
            movesTillReturn = COOLDOWN;
            Log.d("GameItem", "Respawning item: " + this);
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
     * draws the gameItem, doing the actual spawning
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmapItem,
                location.x * s.blockSize, location.y * s.blockSize, s.mPaint);
    }

}
