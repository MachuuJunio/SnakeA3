package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import java.util.ArrayList;

class Snake implements Movable, GameObject {

    private Screen s;
    // The location in the grid of all the segments
    private ArrayList<Point> segmentLocations = new ArrayList<>();
    // How big is the entire grid
    private Point mMoveRange;

    protected Point head;

    // For tracking movement Heading
    enum Heading { UP, RIGHT, DOWN, LEFT }
    private Heading heading = Heading.RIGHT;

    // A bitmap for each direction the head can face
    private Bitmap mBitmapHeadRight, mBitmapHeadLeft, mBitmapHeadUp, mBitmapHeadDown;
    // A bitmap for the body
    private Bitmap mBitmapBody;

    Snake(Context context, Screen s) {
        this.s = s;
        mMoveRange = s.getPoint();

        // Create and scale the bitmaps
        mBitmapHeadRight = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.truckfront);

        // Create 3 more versions of the head for different headings
        mBitmapHeadLeft = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.truckfront);

        mBitmapHeadUp = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.truckfront);

        mBitmapHeadDown = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.truckfront);

        // Modify the bitmaps to face the snake head
        // in the correct direction
        mBitmapHeadRight = Bitmap
                .createScaledBitmap(mBitmapHeadRight,
                        s.blockSize, s.blockSize, false);

        // A matrix for scaling
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, s.blockSize, s.blockSize, matrix, true);

        // A matrix for rotating
        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, s.blockSize, s.blockSize, matrix, true);

        // Matrix operations are cumulative
        // so rotate by 180 to face down
        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, s.blockSize, s.blockSize, matrix, true);

        // Create and scale the body
        mBitmapBody = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.crate);

        mBitmapBody = Bitmap
                .createScaledBitmap(mBitmapBody,
                        s.blockSize, s.blockSize, false);
    }
    // Get the snake ready for a new game

    void reset() {

        // Reset the heading
        heading = Heading.RIGHT;

        // Delete the old contents of the ArrayList
        segmentLocations.clear();

        // Start with a single snake segment
        segmentLocations.add(new Point(s.NUM_BLOCKS_WIDE / 2, s.mNumBlocksHigh / 2));
    }

    @Override
    public void move() {
        // Make it the same value as the next segment
        // going forwards towards the head
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            segmentLocations.get(i).x = segmentLocations.get(i - 1).x;
            segmentLocations.get(i).y = segmentLocations.get(i - 1).y;
        }

        //move the head appropriately
        Point p = segmentLocations.get(0);
        switch (heading) {
            case UP: p.y--; break;
            case RIGHT: p.x++; break;
            case DOWN: p.y++; break;
            case LEFT: p.x--; break;
        }
    }

    boolean detectDeath() {
        //check for death
        boolean dead = false;
        Point head = segmentLocations.get(0);
        if (head.x == -1 || head.x > mMoveRange.x || head.y == -1 || head.y > mMoveRange.y) {
            dead = true;
        }
        // Eaten itself?
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            if (head.x == segmentLocations.get(i).x && head.y == segmentLocations.get(i).y) {
                dead = true;
            }
        }
        return dead;
    }

    boolean checkDinner(Point appleLocation) {
        //check if the snake's head has collided with the apple
        Point head = segmentLocations.get(0);
        if (head.x == appleLocation.x && head.y == appleLocation.y) {
            segmentLocations.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!segmentLocations.isEmpty()) {
            head = segmentLocations.get(0);
            // Draw the head
            switch (heading) {
                case RIGHT: canvas.drawBitmap(mBitmapHeadRight, segmentLocations.get(0).x * s.blockSize,
                        segmentLocations.get(0).y * s.blockSize, s.mPaint); break;
                case LEFT: canvas.drawBitmap(mBitmapHeadLeft, segmentLocations.get(0).x * s.blockSize,
                        segmentLocations.get(0).y * s.blockSize, s.mPaint); break;
                case UP: canvas.drawBitmap(mBitmapHeadUp, segmentLocations.get(0).x * s.blockSize,
                        segmentLocations.get(0).y * s.blockSize, s.mPaint); break;
                case DOWN: canvas.drawBitmap(mBitmapHeadDown, segmentLocations.get(0).x * s.blockSize,
                        segmentLocations.get(0).y * s.blockSize, s.mPaint); break;
            }

            //draw the segment
            for (int i = 1; i < segmentLocations.size(); i++) {
                canvas.drawBitmap(mBitmapBody, segmentLocations.get(i).x * s.blockSize,
                        segmentLocations.get(i).y * s.blockSize, s.mPaint);
            }
        }
    }
    public void changeDirection(Heading newDirection) {
        // Only allow the snake to turn 90 degrees
        if (Math.abs(newDirection.ordinal() - this.heading.ordinal()) % 2 == 1) {
            this.heading = newDirection;
        }}
}