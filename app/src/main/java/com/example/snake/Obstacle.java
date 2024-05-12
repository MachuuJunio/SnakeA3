package com.example.snake;

import android.content.Context;

import java.util.ArrayList;

public abstract class Obstacle extends GameItem implements GameObject{
    /**
     * CONCRETE SUBCLASSES
     *
     * Robber  (Steals 2 packages) [reduces score]
     *         {if mass less than 2, you're fired}
     *
     * Cone    (Drop 1 package) [reduces score]
     *          {if mass less than 1, you're fired}
     *
     * Children (Gameover) {how dare you hit a child}
     *
     *
     */
    /**
     * Number of frames the Obstacle will affect the snake if touched
     */
    final int ACTIVATION;
    /**
     * Current number of frames the Obstacle will affect the Snake
     */
    protected int movesTillDeactivate;
    public Obstacle(){
        super();
        ACTIVATION = 0;
    }

    public Obstacle(Context context, ArrayList<GameItem> activeItems, Screen s,
                   int cooldown, int vanish, int activation){
        super(context, s, cooldown, vanish);
        ACTIVATION = activation;
        movesTillDeactivate = ACTIVATION;
    }

    /**
     * @return the number of frames remaining till Obstacle deactivated
     */
    public int getActivationRemaining(){
        return movesTillDeactivate;
    }

    /**
     * Checks if the Obstacle should be deactivated
     */
    public boolean deactivate(){
        if(movesTillDeactivate <= 0){
            movesTillDeactivate = ACTIVATION;
            return true;
        }
        return false;
    }

    /**
     * Get's the activation effect of the Obstacle
     */
    public abstract void activationEffect();

}
