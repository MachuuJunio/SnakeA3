package com.example.snake;

import android.content.Context;

import java.util.ArrayList;

public abstract class Powerup extends GameItem implements GameObject{
    /**
     * CONCRETE SUB-CLASSES
     * ------
     * Format
     * -----------------------
     * Powerup name      (activation effect)
     *                   {how effect can be achieved}
     *                   [availability] [activation time]
     *                   [stayTime] [cooldown
     * -------------------------
     *
     *
     * Ambulance Siren   (will double the speed)
     *                   {Calls snake.move() twice}
     *                   [1 powerup] [activation time 3 seconds]
     *                   [stayTime 2 seconds] [cooldown 11 seconds]
     *                   {Sound: Ambulance Siren}
     *
     * Cop Siren         (Invulnerable to Obstacles, Debuff)
     *                   {if snake.interact is Obstacle or Debuff ignore}
     *                   [1 powerup] [activation time 3 seconds]
     *                   [stayTime 3 seconds] [cooldown 10 seconds]
     *                   {Sound: Police Siren}
     *
     * Amazon Ring       (Doubles mass, and score)
     *                   {Multiply by two for both 'Food' final var}
     *                   [1 powerup] [activation 4 seconds]
     *                   [stayTime 2 seconds] [cooldown 12 seconds]
     *                   //powerup.getType() = "Amazon";
     *                   //powerup should recreate the GameItem as a Food
     *                   //Find all occurances of Food in
     *                   //...activeItems, and cooldownItems
     *                   //Replace with same parametes
     *                   {Sound Ring notification }
     *
     * Time Stop         (Stops cooldown, and despawning of GameItems)
     *                   {Don't call gom.reduceStay() and gom.reduceCooldown()}
     *                   [1 powerup] [activation 5 seconds]
     *                   [stayTime 3 seconds] [cooldown 12 seconds]
     *                   {Sound: Church bell}
     *
     * WHAT IS COMMON WITH THE EFFECTS?
     *      additional call, modification of call
     * METHODS
     * //the activation effect of the Powerup
     * activationEffect()
     *
     * INFO
     * If a powerup is interacted with, all other powerups,
     * will be suspended.
     *
     * POWERUP
     *      Affects speed, mass, score, ActiveItems, and Snake methods
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

    public Powerup(Context context, Screen s,
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
        if(movesTillDeactivate == 0){
            movesTillDeactivate = ACTIVATION;
            return true;
        }
        return false;
    }

    /**
     * Gets the sound that is played when the Powerup is still activated
     */
    public abstract void activationEffect();

    /**
     * Checks whether the powerup is capable of deflecting
     * gameEnding actions
     */
    public abstract boolean gameEnding();
}
