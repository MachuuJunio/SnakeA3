package com.example.snake;

import android.content.Context;

import java.util.ArrayList;

public abstract class Powerup extends GameItem{
    /**
     * CONSTRUCTOR
     *
     *
     * FIELD
     * //The amount of moves that the powerup will be activated
     * activationMoves : int
     *
     *
     *
     *METHOD
     *
     * //Checks to see if a
     * timeup(): boolean
     *
     *BEHAVIOR
     * Some kind of effect will be added based on the type of powerup
     *
     * If powerup becomes activated, it technically
     * always gets attributed to the snake's Head
     *
     * So activated powerups, have a dependency to the Snake
     *
     *
     */
    /**
     * The amount of frames the Powerup will be activated for
     */
    private final int ACTIVATION;
    private int movesTillDeactivate;

    public Powerup(){
        super();
        ACTIVATION = 0;
    }

    public Powerup(Context context, ArrayList<GameItem> activeItems, Screen s,
                   int cooldown, int vanish, int activation){
        super(context, activeItems, s, cooldown, vanish);
        ACTIVATION = activation;
        movesTillDeactivate = ACTIVATION;
    }

    /**
     * Reduces the number of moves the Powerup is activated
     */
    public void reduceActivation(){
        movesTillDeactivate--;
    }

    /**
     * Checks if the Powerup should be deactivated
     */
    public boolean deactivate(){
        if(movesTillDeactivate <= 0){
            movesTillDeactivate = ACTIVATION;
            return true;
        }
        return false;
    }
}
