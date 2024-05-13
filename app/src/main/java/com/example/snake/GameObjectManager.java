package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * This class is responsible for encapsulating
 * the management of all GameItems,
 */
public class GameObjectManager {

    /**
     * Consists of all GameItems that are not on the screen, and
     * waiting to respawn
     */
    private ArrayList<GameItem> cooldownItems;

    /**
     * Consists of all GameItems on the screen
     */
    private ArrayList<GameItem> activeItems;

    /**
     * Consists all Obstacles that have interacted with snake head,
     * and have not deactivated yet
     */
    private ArrayList<Obstacle> activatedObstacles;

    /**
     * Frame class
     *
     */
    /**
     * A Powerup the snake head previously or just collided with, that has not expired
     *
     * this variable would be best assigned to the Snake or to an actual frame class....
     */
    protected Powerup powerup;
    private Context c;
    private Screen s;

    public GameObjectManager(Context context, Screen s){
        cooldownItems = new ArrayList<GameItem>();
        activeItems = new ArrayList<GameItem>();
        activatedObstacles = new ArrayList<Obstacle>();
        c = context;
        this.s = s;
        setGameItems();
    }

    /**
     * The objects that will be added to the activeItems,
     * and will be assigned unique locations on the board
     */
    private void setGameItems(){
        activeItems.add(new House(c, s));
        activeItems.add(new House(c, s));
        activeItems.add(new House(c, s));
        activeItems.add(new House(c, s));
        activeItems.add(new TimeStop(c, s));
        spawnAll();
    }

    /**
     * Spawns all GameItems that are in activeItems
     * at the beginning of the game.
     */
    private void spawnAll(){
        if(!activeItems.isEmpty()){
            for(GameItem g: activeItems){
                g.spawn(activeItems);
            }
        }
    }

    /**
     * Reduces the cooldown of all GameItems in
     * cooldownItems by 1
     */
    private void reduceCooldown(){
        if(!cooldownItems.isEmpty()){
            for(GameItem g: cooldownItems)
                g.reduceCooldown();
        }
    }

    /**
     * Reduces the stay of all GameItems in
     * activeItems by 1
     */
    private void reduceStay(){
        if(!activeItems.isEmpty()){
            for(GameItem g: activeItems)
                g.reduceStay();
        }
    }

     /**
     * If powerup is activated, decrementActivation
     */
     private void reduceActivation(){
         if(powerup != null){
             powerup.reduceActivation();
         }
     }

    /**
     * @return gameItem that interacted with snakehead, if there is
     *          no such item then null value is passed.
     */
    private GameItem interact(Snake snake){
        if(!activeItems.isEmpty()){
            for(GameItem g: activeItems) {
                if (snake.interact(g)) {
                    return g;
                }
            }
        }
        return null;
    }



    /**
     * If the snake head interacts with a GameItem
     */
    private void interactionLogic(Snake snake){
        GameItem interactedItem = this.interact(snake);
        //Checks if an object interacted with the snake head
        if(interactedItem != null){
            //Removes the interactedItem from activeItems
            activeItems.remove(interactedItem);
            //Resets the Despawn time
            interactedItem.reset();
            //Checks to see if the interactedObject is a Powerup
            if(interactedItem instanceof Powerup){
                powerup = (Powerup)interactedItem;
            }else {
                //add the interactedItem to the cooldownItems
                cooldownItems.add(interactedItem);
                //Sorts the cooldown objects by cooldown time
                Collections.sort(cooldownItems, Comparator.comparing(GameItem::getCooldownRemaining));
            }
        }
    }

    /**
     * If a powerup is activated, checks if the powerup should be deactivated.
     * And if it should be deactivated then it will be deactivated.
     */
    private void checkDeactivate(){
        if(powerup != null && powerup.deactivate()){
            cooldownItems.add(powerup);
            powerup = null;
        }
    }

    /**
     * Check for all occurances of GameItems ready to spawn on
     * the board, and spawn them.
     */
    private void checkRespawn(){
        while (!cooldownItems.isEmpty() && cooldownItems.get(0).respawn()) {
            //initialize the readyObject as the object ready to respawn
            GameItem readyItem = cooldownItems.remove(0);
            //Spawns the object at a position on the gameboard
            readyItem.spawn(activeItems);
            //adds the readyObject to the activeObjects
            activeItems.add(readyItem);
            //sorts the activeObjects based on despawn time
            Collections.sort(activeItems, Comparator.comparing(GameItem::getCooldownRemaining));
        }
    }

    /**
     * Checks all occurance of GameItems that need to cooldown, and makes them cooldonw.
     */
    private void checkDespawn(){
        while (!activeItems.isEmpty() && activeItems.get(0).despawn()) {
            //Initializes the recycleObject as the object that is ready to despawn
            GameItem recycledItem = activeItems.remove(0);
            //Add the recycleObject to the cooldown object
            cooldownItems.add(recycledItem);
            //sorts the cooldown objects based on the cooldown time
            Collections.sort(cooldownItems, Comparator.comparing(GameItem::getStayRemaining));
        }
    }

    /**
     * Draws all the GameItems (not the snake)
     * @param mCanvas drawing tool
     */
    public void drawGameItems(Canvas mCanvas){
        if(!activeItems.isEmpty()){
            for(GameItem g: activeItems) {
                g.draw(mCanvas);
            }
        }
    }

    /**
     * The game Logic that determines what GameItems
     * will appear on the screen during this frame
     */
    public void gameLogic(Snake snake){
        if(powerup == null || !(powerup instanceof TimeStop)){
            reduceCooldown();
            reduceStay();
        }
        reduceActivation();
        checkDeactivate();
        interactionLogic(snake);
        checkDespawn();
        checkRespawn();
    }



}
