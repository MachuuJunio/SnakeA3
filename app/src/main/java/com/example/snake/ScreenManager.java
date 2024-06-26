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
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.road);

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
        canvas.drawText("" + stat.mScore, 200, 200, s.mPaint);
    }

    /***
     * Writes the names of the programmer in the upper right corner, when the game is paused
     */
    private void writeTitle(Canvas canvas){
        s.mPaint.setTextSize(80);
        canvas.drawText("Movers!", 1500, 200, s.mPaint);
    }

    /**
     * Draws the pause button
     */
    void drawPause(Canvas canvas){
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
        s.mPaint.setTextSize(70);
        canvas.drawText("RESUME", 1900, 950, s.mPaint);
    }
    public void drawTapToPlay(Canvas canvas){
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(70);

        // Draw the message
        // We will give this an international upgrade soon
        //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        canvas.drawText(context.getResources().getString(R.string.tap_to_play), 925, 700, s.mPaint);
    }
    public void drawLeaderBoard(Canvas canvas, StatManager stat){
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(50);

        canvas.drawText("Leader Board", 250, 250, s.mPaint);
        canvas.drawText("" + stat.leaderBoard[0], 250, 300, s.mPaint);
        canvas.drawText("" + stat.leaderBoard[1], 250, 350, s.mPaint);
        canvas.drawText("" + stat.leaderBoard[2], 250, 400, s.mPaint);
    }
    public void drawGameOver(Canvas canvas){
        s.mPaint.setColor(Color.argb(255, 255, 255, 255));
        s.mPaint.setTextSize(100);

        canvas.drawText("You Crashed! Game Over!", 550, 400, s.mPaint);
    }

    /**
     * Decides what should be drawn based on the pause state
     * @param mPaused determines whether the game is paused or not
     */
    private void drawPlayState(boolean mPaused, Canvas canvas, StatManager stat){
        if (mPaused) {
            // When the game is paused, only draw "RESUME" and related UI elements
            writeTitle(canvas);
            drawLeaderBoard(canvas, stat);
            drawTapToPlay(canvas);
            drawResume(canvas);  // Ensure this is the only button shown when paused
        } else {
            // Only draw "PAUSE" when the game is running
            drawPause(canvas);
        }
    }


    /**
     * Draws the screen
     * @param mPaused determines the current pause state of the game
     * @param stat contains real time stats of the current game
     */
    public void drawScreen(boolean mPaused, StatManager stat, Canvas canvas) {
        drawBackground(canvas);
        drawScore(stat, canvas);
        drawPlayState(mPaused, canvas, stat);
    }
}