package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * This class is responsible for
 * encapsulating all instances of GameItem,
 * and the management of all GameItem objects
 */
public class GameObjectManager {

    /**
     * Consists of all GameItems that are not on the screen, waiting to respawn
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
     * Powerup that is activated
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
     * @return GameItem that interacts with snake head
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
     * Game logic for snake head interaction with a GameItem
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
     * If powerup is activated, deactivates the powerup once expired
     */
    private void checkDeactivate(){
        if(powerup != null && powerup.deactivate()){
            GameItem expiredItem = (GameItem)powerup;
            cooldownItems.add(expiredItem);
            Collections.sort(cooldownItems, Comparator.comparing(GameItem::getCooldownRemaining));

            powerup = null;
        }
    }

    /**
     * Enables spawning of all GameItems that have finished cooling down
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
            Collections.sort(activeItems, Comparator.comparing(GameItem::getStayRemaining));

            Log.d("GameObjectManager", "Item respawned and moved to active: " + readyItem);
        }
    }

    /**
     * Enables the isolation of all GameItems that have stayed on the Screen for their
     * allotted number of frames
     */
    private void checkDespawn(){
        while (!activeItems.isEmpty() && activeItems.get(0).despawn()) {
            //Initializes the recycleObject as the object that is ready to despawn
            GameItem recycledItem = activeItems.remove(0);
            //Add the recycleObject to the cooldown object
            cooldownItems.add(recycledItem);
            //sorts the cooldown objects based on the cooldown time
            Collections.sort(cooldownItems, Comparator.comparing(GameItem::getCooldownRemaining));

            Log.d("GameObjectManager", "Item despawned and moved to cooldown: " + recycledItem);
        }
    }

    /**
     * A method that reduces the stay, and immediately check to see if its
     * time for active GameItems to say byebye..
     */
    private void isolateExpiredItems(){
        if(powerup == null || !(powerup instanceof TimeStop)) {
            reduceStay();
            checkDespawn();
        }
    }


    /**
     * A method that reduces the cooldown, and immediately check to see if it
     * is time for isolated GameItems to say hihi
     */
    private void deployReadyItems(){
        if(powerup == null || !(powerup instanceof TimeStop)) {
            reduceCooldown();
            checkRespawn();
        }
    }

    /**
     * Manages the activation time of powerup, and deactivated it once it has expired
     */
    private void managePowerup(){
        reduceActivation();
        checkDeactivate();
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
     * Game logic that determines which GameItems are shown on the screen in current frame.
     */
    public void gameLogic(Snake snake){
        isolateExpiredItems();
        deployReadyItems();
        managePowerup();
        interactionLogic(snake);
    }


    }



