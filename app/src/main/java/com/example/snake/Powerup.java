package com.example.snake;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public abstract class Powerup extends GameItem{
    /**
     * CONCRETE SUB-CLASSES
     * ------
     * Format
     * -----------------------
     * Powerup name      (activation effect)
     *                   {how effect can be achieved}
     *                   [availability] [activation time]
     *                   [stayTime] [cooldown]
     *                   {Sound}
     *                   ...additional details
     * -------------------------------------------------------------------
     * Ambulance         (will double the speed)
     *                   {Calls snake.move() twice}
     *                   [1] [3]
     *                   [2] [11]
     *                   {Ambulance Siren}
     *------------------------------------------------------------------
     * Cop               (Invulnerable to Obstacles, Debuff)
     *                   {if snake.interact() is Obstacle or Debuff ignore}
     *                   [1] [3]
     *                   [3] [10]
     *                   {Police Siren}
     *------------------------------------------------------------------
     * Amazon            (Doubles mass, and score)
     *                   {Multiply by two for both 'Food' final var}
     *                   [1] [4]
     *                   [2] [12]
     *                   {Ring notification}
     *-----------------------------------------------------------------------
     * Time Stop         (Stops cooldown, and despawning of GameItems)
     *                   {Don't call gom.reduceStay() and gom.reduceCooldown()}
     *                   [1] [5]
     *                   [3] [12]
     *                   {Church bell}
     *
     *
     * RULES
     *          If a powerup is activated, all other powerups will be isolated
     *
     * EFFECTS
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
        ACTIVATION = activation * FRAMES_PER_SECOND;
        movesTillDeactivate = this.ACTIVATION;
    }

    /**
     * Reduces the number of frames the Powerup is activated
     */
    public void reduceActivation(){
        movesTillDeactivate--;
        Log.d("Powerup", "Reduced activation: " + movesTillDeactivate);
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
            Log.d("Powerup", "Despawning item: " + this);
            return true;
        }
        return false;
    }

    /**
     * Gets the sound that is played when the Powerup is still activated
     */
    public abstract void activationEffect();

}
