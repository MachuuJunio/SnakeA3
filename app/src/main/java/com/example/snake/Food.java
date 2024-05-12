package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public abstract class Food extends GameItem implements GameObject{
    /**
     * SUB CONCRETE CLASSES
     *
     * Package (
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
    public Food(Context context, Screen s, int cooldown, int vanish,
                int massGain, int scoreGain){
        super(context, s, cooldown, vanish);
        MASS_GAIN = massGain;
        SCORE_GAIN = scoreGain;
    }

}
