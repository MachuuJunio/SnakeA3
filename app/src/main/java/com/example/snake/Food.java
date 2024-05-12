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
     * Amount of mass gained when Food is consumed
     */
    private final int MASS_GAIN;
    public Food(){
        super();
        MASS_GAIN = 0;
    }
    public Food(Context context, ArrayList<GameItem> activeItems, Screen s,
                int cooldown, int vanish, int massGain){
        super(context, activeItems, s, cooldown, vanish);
        MASS_GAIN = massGain;
    }

}
