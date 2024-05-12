package com.example.snake;

import android.content.Context;

import java.util.ArrayList;

public abstract class Powerup extends GameItem implements GameObject{
    /**
     * CONCRETE SUB-CLASSES
     *
     * Ambulance Siren   (will double the speed)
     *
     * Cop Siren         (Invulnerable to Obstacles)
     *
     */
    /**
     * The amount of frames the Powerup will be activated for
     */
    private final int ACTIVATION;
    protected int movesTillDeactivate;

    public Powerup(){
        super();
        ACTIVATION = 0;
    }

    public Powerup(Context context, ArrayList<GameItem> activeItems, Screen s,
                   int cooldown, int vanish, int activation){
        super(context, s, cooldown, vanish);
        ACTIVATION = activation;
        movesTillDeactivate = ACTIVATION;
    }

    /**
     * Reduces the number of frames the Powerup is activated
     */
    public void reduceActivation(){
        movesTillDeactivate--;
    }

    /**
     * Gets the number of frames till the Powerup will deactivate
     */
    public int getActivationRemaining(){
        return movesTillDeactivate;
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

    /**
     * Gets the activation effect of the Powerup
     */
    public abstract void activationEffect();

    /**
     * Checks whether the powerup is capable of deflecting
     * gameEnding actions
     */
    public abstract boolean gameEnding();
}
