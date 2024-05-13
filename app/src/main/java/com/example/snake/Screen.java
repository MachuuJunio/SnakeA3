package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

/**
 * Class is in charge of storing most of the drawing objects
 * and all of the screen dimensions
 */
public class Screen {
    protected final int NUM_BLOCKS_WIDE = 40;
    protected final int mNumBlocksHigh;
    protected final int blockSize;
    //protected Canvas canvas;
    protected Paint mPaint;

    SurfaceHolder mSurfaceHolder;

    /**
     * Initializes the dimension of the device.
     * @param size the dimensions of the device the game is launched on
     */
    public Screen(Point size) {
        blockSize = size.x / NUM_BLOCKS_WIDE;
        mNumBlocksHigh = size.y / blockSize;
        mPaint = new Paint();
    }
    /**

     * This method is wrongly placed.
     * It should be dealt in screenManager, or dealt in a different class,
     * due to the strange properties of SurfaceHolder.
     * @param canvas

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }
    **/

    /**
     * @return a Point within the constraints of the screen
     */
    public Point getPoint(){
        return new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh);
    }

}
