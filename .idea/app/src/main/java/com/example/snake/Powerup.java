package com.example.snake;

import android.content.Context;

import java.util.ArrayList;

public abstract class Powerup extends GameItem implements GameObject{
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
     * CONCRETE SUB-CLASSES
     *
     * Speed x2. (will involve manipulating the number of moves,
     *            snake takes per frame)
     *            [needs to intervene in SnakeGame.run()]
     *
     * Invulnerable (will involve removing all instances of death
     *               unless snake moves off map)
     *               [needs to stop calling snake.death]
     *
     * Food Mania  (will involve turning all gameItems that are type
     *              Obstacle to Food) [needs access to ActiveItems]
     *
     * Kirby        (will make all Food items into Gourmet Food)
     *              [Needs access to ActiveItems]
     *
     *
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
