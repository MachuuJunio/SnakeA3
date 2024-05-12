package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Class in charge of drawing the game
 */
public class ScreenManager {
    Screen s;
    Context context;

    Canvas canvas;

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
        canvas.drawColor(Color.argb(255, 20, 182, 120));
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
    private void writeNames(Canvas canvas){
        s.mPaint.setTextSize(80);
        canvas.drawText("Tyson Huynh", 1700, 100, s.mPaint);
        canvas.drawText("Matthew Junio", 1640, 200, s.mPaint);
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

    /**
     * Decides what should be drawn based on the pause state
     * @param mPaused determines whether the game is paused or not
     */
    private void drawPlayState(boolean mPaused, Canvas canvas){
        if(mPaused){
            drawResume(canvas);
            writeNames(canvas);
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
        drawPlayState(mPaused, canvas);
    }




}