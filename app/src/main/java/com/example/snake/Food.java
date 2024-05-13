package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public abstract class Food extends GameItem implements GameObject{
    /**
     * SUB CONCRETE CLASSES
     *
     * House    (+1 package)  [+1 score]
     *
     * Mansion  (+3 package)  [+3 score]
     *
     * (?)box   (5 > package > -5) [score = package]
     *
     * Robber   (-2 package)   [-2 score]
     *          {if mass less than 2, you're fired}
     *
     * Cone     (-1 package) [-1 score]
     *          {if mass less than 1, you're fired}
     *
     * FOOD
     *      Only affects the score, and mass
     *
     * CONSIDERATION
     *
     * Buff
     *      Increases score, and Mass
     *
     * Debuff
     *      Decreases score, and Mass
     */


    /**
     * Amount of mass gained when Food is consumed
     */
    protected final int MASS_GAIN;

    /**
     * Number of points gained when Food is consumed
     */
    protected final int SCORE_GAIN;

    private boolean isDoubled;

    public Food(){
        super();
        MASS_GAIN = 0;
        SCORE_GAIN = 0;
    }
    public Food(Context context, Screen s, int cooldown, int vanish,
                int massGain, int scoreGain){
        super(context, s, cooldown, vanish);
        this.isDoubled = isDoubled;
        SCORE_GAIN = scoreGain;
        MASS_GAIN = massGain;
        //SCORE_GAIN = getGain(scoreGain, isDoubled);
        //MASS_GAIN = getGain(massGain, isDoubled);
    }

    /**
     * If the Amazon Powerup is activated
     */
    public final int getGain(int gain, boolean isDoubled){
        return isDoubled ? gain : gain * 2;
    }

    public boolean getIsDoubled(){
        return isDoubled;
    }

}
