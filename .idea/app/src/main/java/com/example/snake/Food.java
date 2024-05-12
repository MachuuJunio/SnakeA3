package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public abstract class Food extends GameItem implements GameObject{
    /**
     * UNIQUE PROPERTIES
     *
     * Amount of massGain
     * numScore
     */

    /**
     * SUB CONCRETE CLASSES
     *
     * Gourmet      (Increase score by 5, gives additional mass of 2,
     *               can only be called by the Kirby powerup, once consumed
     *               put into Cooldown as whatever item it substituted)
     *
     * Choco         (Decreases score by 4, reduces mass by 3.
     *               If mass, is less than 3, then snake dies)
     *
     * Apple         (Already implemented)
     *
     * Golden         (Increases score by 10, gives additional mass of 3
     */


    /**
     * Amount of mass gained when Food is consumed
     */
    protected final int MASS_GAIN;

    /**
     * Number of points gained when Food is consumed
     */
    protected final int SCORE_GAIN;

    public Food(){
        super();
        MASS_GAIN = 0;
        SCORE_GAIN = 0;
    }
    public Food(Context context, ArrayList<GameItem> activeItems, Screen s,
                int cooldown, int vanish, int massGain, int scoreGain){
        super(context, activeItems, s, cooldown, vanish);
        MASS_GAIN = massGain;
        SCORE_GAIN = scoreGain;
    }

}
