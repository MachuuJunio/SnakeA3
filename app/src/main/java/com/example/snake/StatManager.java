package com.example.snake;

import java.util.Random;

public class StatManager {
    /**
     * Progressive Difficulty
     *
     * Increases obstacles every 10,000 frames
     *
     */

    private int round;
    protected int mScore;
    private int frameCount;

    private GameObjectManager g;

    /**
     * Basically says that there is a new round every minute.
     */
    private final int FRAMES_TILL_NEW_ROUND = 10 * 60;

    public StatManager(GameObjectManager gameObjectManager){
        mScore = 0;
        frameCount = 0;
        g = gameObjectManager;
    }

    /**
     * Addresses the unlikely scenerio someone has survived for
     * 59,652 hours playing the game...
     */
    private void resetFrameCount(){
        if(frameCount == (int)(Integer.MAX_VALUE) - 2){
            //code to make the game over
        }
    }

    /**
     * @return whether the game has reached the next round
     */
    private boolean isNextRound(){
        return (frameCount % FRAMES_TILL_NEW_ROUND == round);
    }

    /**
     * Method that adds a new object once a new round is added!
     * It adds a random object of type Food, Obstacle, or Powerup
     * Chance of new Food obj:  20%
     * Chance of new Obstacle obj : 60%
     * Chance of new Powerup obj: 20%
     */
    private void addGameItem(){
        final int FIRST_CHANGE = 10;
        final int SECOND_CHANGE = 20;
        final int THIRD_CHANGE = 30;

        int choice = (int)(Math.random() * 10);
        if(round < FIRST_CHANGE){
            if(choice <= 5){
                //new food
                //randomized food
            }
            else if(choice <= 7){
                //new obstacle
                //randomized obstacle
            }
            else if(choice <= 10){
                //new powerup
                //randomized powerup
            }
        }


    }


    public void incrementFrameCount(){
        frameCount++;
    }

    public void incrementScore(){
        mScore++;
    }

    public void addScore(int add){ mScore += add;}

    public void reset(){
        mScore = 0;
        frameCount = 0;
    }




    public void createObjRules(){

    }
}
