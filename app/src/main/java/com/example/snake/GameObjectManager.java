package com.example.snake;

import android.content.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * In the future update should consider if snakehead, interact
 * Ideas
 * There should be more negative items added to the game as the score increases.
 * The cooldown time will be the same for every object!
 * It's best to keep the GameObject as an interface... wait actually that's dumb
 * it doesn't matter because the SnakeGame has a dependency relationship with GameObject
 *
 * In update()
 *
 * //Decrements the moves till GameItems in cooldown get spawned
 * while (!cooldown.isEmpty())
 *      for (GameItem g: cooldown)
 *          g.reduceCooldown();
 *
 * //Tells if there was an intersection with the snake head
 * GameItem interactedItem = null;
 *
 * //Decrements the moves till GameItems in activeObjects get despawned
 * while(!activeItems.isEmpty)
 *      for(GameItem g: activeItems)
 *          g.reduceDespawn();
 *          if(g.interact(snakeHead))
 *              interactedItem = g.reset();
 *
 *
 * //checks whether there is any object that interacted with the snake head
 * if (interactedItem != null)
 *
 *      //Removes the GameObject from the arraylist of present GameObjects
 *      activeItems.remove(interactedItem)
 *
 *      //Sorts the activeItems based on despawn time
 *      Collection.sort(activeItems, Comparator.comparing(GameItem:: getStayRemaining())
 *
 *      //adds the interactedItem to the cooldown menu
 *      cooldown.add(interactedItem)
 *
 *      //Sorts the cooldown objects by cooldown time
 *      Collection.sort(cooldown, Comparator.comparing(GameItem::getCooldownRemaining())
 *
 *      //Checks to see if the interactedObject is a Powerup
 *      if (interactedItem.type() == "Powerup")
 *
 *          //Initializes private var to the interactedObject
 *          powerup = interactedItem
 *
 *          //adds the interactedItem to the snake.
 *          mSnake.addPowerup(powerup)
 *
 *      //Checks to see if the interactedObject is a consumable
 *      else if (interactedItem.type() == "Food")
 *
 *          //calls the consume method.
 *          snake.consume(interactedItem.MASS_GAIN)
 *
 *          //plays the eat sound
 *          gameSound.play(gameSound.mEat_ID)
 *
 *          //adjusts the score
 *          stat.addScore(interactedItem.SCORE_GAIN)
 *
 *      //Checks if the interactedObject is an Obstacle
 *      else  if (interactedItem.type() == "Obstacle")
 *           /adds the obstacle to the snake.
 *           obstacles.add(interactedItem)
 *           gameSound.play(gameSound.mCrash_ID);
 *           mPaused = true;
 *
 * //Checks if the powerup is non null value, and if timeup() returns true
 * if( powerup != null && powerup.timeUp())
 *      powerup = null;
 * else
 *      //activates the effect of the powerup, params are undecides as of now...
 *      powerup.activateEffect(this)
 *      gameSound.play(gameSound.mPowerup_ID)
 *
 *
 * //Loops until the first object in the cooldown list, returns false for respawn()
 * while(cooldown.get(0).respawn())
 *      //initialize the readyObject as the object ready to respawn
 *      GameObject readyObject = cooldown.remove(0)
 *      //adds the readyObject to the activeObjects
 *      activeObjects.add(readyObject)
 *      //sorts the activeObjects based on despawn time
 *      Collection.sort(activeObjects, Comparator.comparing(GameObject: despawnTime())
 *
 * while(activeObjects.get(0).despawn())
 *      //Initializes the recycleObject as the object that is ready to despawn
 *      GameObject recycleObject = activeObjects.remove(0)
 *      //Add the recycleObject to the cooldown object
 *      cooldown.add(recycleObject)
 *      //sorts the cooldown objects based on the cooldown time
 *      Collection.sort(cooldown, Comarator.comparing(GameObject:: getCooldownTime())
 *
 *
 *
 *
 * public void reduceCooldown(ArrayList<GameObject> objects){
 *      for(GameObject g: objects){
 *          g.reduceCooldown();
 *      }
 * }
 *
 * public void reduceDespawn(ArrayList<GameObject> objects){
 *      ArrayList<Game
 *      for(GameObject g: objects){
 *          g.reduceDespawn();
 *      }
 * }
 *
 * For Food
 * snake.interact() : boolean   //uneccessary
 * snake.interact(objects) : GameObject   //
 * The returned GameObject, should call its interact(s: Snake) method
 * The GameObject should then call its respawn()
 *
 * For Powerup
 * snake.interact() : boolean
 * snake.interact(objects) : GameObject
 * set returned value equal to interactedObject
 * Check using GameObject.type(interactedObject) to see what ..
 * type the returned GameObject is
 * If type is Powerup, then do the following
 * Set private int counter = interactedObject.getMoveCount() + 1
 * Delete the GameObject from the ArrayList
 * Initialize the private variable powerup with interactedObject
 * If powerup variable was empty then
 *
 *
 * Each concrete GameObject class should have a ...
 * respawn(), can be a few seconds delay or whatnot.
 */

/**
 * Call the last method in this class during the update() method.
 */
public class GameObjectManager {

    /**
     * Consists of all GameItems that are not on the board, and
     * waiting to respawn
     */
    protected ArrayList<GameItem> cooldownItems;

    /**
     * Consists of all GameItems that are currently on the board
     */
    protected ArrayList<GameItem> activeItems;

    /**
     * Consists of all obstacles that have interacted with snake head,
     * and the effect has not deactivated yet.
     */
    protected ArrayList<Obstacle> activatedObstacles;

    /**
     * Consists of a powerup, the snake head interacted with, that has
     * not deactivated yet. Remember, that only one powerup can be
     * activated at a time.
     */
    protected Powerup powerup;

    //The snake
    protected Snake snake;

    private Context c;

    private Screen s;



    public GameObjectManager(Context context, Screen s){
        cooldownItems = new ArrayList<GameItem>();
        activeItems = new ArrayList<GameItem>();
        activatedObstacles = new ArrayList<Obstacle>();
        c = context;
        this.s = s;
    }

    /**
     * The objects that will be added to the activeItems
     */
    public void setup(){
        activeItems.add(new Apple(c, activeItems, s));
        activeItems.add(new Apple(c, activeItems, s));
        activeItems.add(new Apple(c, activeItems, s));
        activeItems.add(new Apple(c, activeItems, s));
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

    public void reduceStatus(){
        reduceStay();
        reduceCooldown();
    }

    /**
     * @return gameItem that interacted with snakehead, if there is
     *          no such item then null value is passed.
     */
    public GameItem interact(){
        if(!activeItems.isEmpty()){
            for(GameItem g: activeItems) {
                if (g.interact(snake.head))
                    return g;
            }
        }
        return null;
    }

    public void sortLogic(){
        GameItem interactedItem = this.interact();

        if(interactedItem != null){
            //Removes the interactedItem from activeItems
            activeItems.remove(interactedItem);

            //sorts the activeItems by despawn time
            Collections.sort(activeItems, Comparator.comparing(GameItem:: getStayRemaining));

            //add the interactedItem to the cooldownItems
            cooldownItems.add(interactedItem);

            //Sorts the cooldown objects by cooldown time
            Collections.sort(cooldownItems, Comparator.comparing(GameItem::getCooldownRemaining));
        }
    }

    public void checkRespawn(){
        while (!cooldownItems.isEmpty() && cooldownItems.get(0).respawn()) {
            //initialize the readyObject as the object ready to respawn
            GameItem readyObject = cooldownItems.remove(0);
            //adds the readyObject to the activeObjects
            activeItems.add(readyObject);
            //sorts the activeObjects based on despawn time
            Collections.sort(activeItems, Comparator.comparing(GameItem::getCooldownRemaining));
        }
    }

    public void checkDespawn(){
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
     * Consists of all of the game logic
     */
    public void gameLogic(){
        reduceStatus();
        sortLogic();
        checkDespawn();
        checkRespawn();
    }



}
