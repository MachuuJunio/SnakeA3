package com.example.snake;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Class in charge of drawing the game
 */

public class ScreenManager {
    Screen s;
    Context context;

    Canvas canvas;

    Snake snake;

    /**
     * Takes in an instance of the Screen, and context.
     * @param s
     * @param context
     */
    public ScreenManager(Screen s, Context context){
        this.s = s;
        this.context = context;



    }

    /**
     * Draws the background of the Game
     */
    private void drawBackground(Canvas canvas){

        //Loads the background image
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.road2);

        int dstWidth = 2220;
        int dstHeight = 1015;

        //Creates a scaled bitmap of the background image (resized image)
        Bitmap resizedBackground = Bitmap.createScaledBitmap(backgroundBitmap, dstWidth, dstHeight, true);

        //Draws the background image to the screen
        canvas.drawBitmap(resizedBackground, 0,0, null);

    }


    /**
     * Draws the score
     * @param stat an object that encapsulates the stats at the instanteous frame of the game
     */
    private void drawScore(StatManager stat, Canvas canvas){
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(120);

        // Draw the score
        canvas.drawText("" + stat.mScore, 20, 120, s.mPaint);
    }

    /***
     * Writes the names of the programmer in the upper right corner, when the game is paused
     */
    private void writeTitle(Canvas canvas){
        s.mPaint.setTextSize(80);
        canvas.drawText("Mover Outers !", 1700, 100, s.mPaint);
    }

    /**
     * Draws the pause button
     */
    private void drawPause(Canvas canvas){
        // Set the size and color of the mPaint for the text
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(250);

        s.mPaint.setTextSize(70);
        canvas.drawText("PAUSE", 1950, 950, s.mPaint);
    }

    /**
     * Draws the resume button, when the game is paused
     */
    public void drawResume(Canvas canvas){
        // Set the size and color of the mPaint for the text
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(250);

        // Draw the message
        // We will give this an international upgrade soon
        //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        canvas.drawText(context.getResources().
                        getString(R.string.tap_to_play),
                470, 600, s.mPaint);
        s.mPaint.setTextSize(70);
        canvas.drawText("RESUME", 1900, 950, s.mPaint);
    }
    public void drawLeaderBoard(Canvas canvas, StatManager stat){
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(50);

        canvas.drawText("Leader Board", 20, 200, s.mPaint);
        canvas.drawText("" + stat.leaderBoard[0], 20, 250, s.mPaint);
        canvas.drawText("" + stat.leaderBoard[1], 20, 300, s.mPaint);
        canvas.drawText("" + stat.leaderBoard[2], 20, 350, s.mPaint);
    }

    /**
     * Decides what should be drawn based on the pause state
     * @param mPaused determines whether the game is paused or not
     */
    private void drawPlayState(boolean mPaused, Canvas canvas, StatManager stat){
        if(mPaused){
            drawResume(canvas);
            writeTitle(canvas);
            drawLeaderBoard(canvas,stat);
        }else{
            drawPause(canvas);
        }
    }

    /**
     * Draws the screen
     * @param mPaused determines the current pause state of the game
     * @param stat contains real time stats of the current game
     */
    public void drawScreen(boolean mPaused, StatManager stat, Canvas canvas){
        drawBackground(canvas);
        drawScore(stat, canvas);
        drawPlayState(mPaused, canvas, stat);
    }




}