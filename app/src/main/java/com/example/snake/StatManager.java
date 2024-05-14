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
    private int moveCount;
    private int frameCount;
    public int[] leaderBoard = {0,0,0};
    public boolean toggleDead;

    /**
     * Basically says that there is a new round every minute.
     */
    private final int FRAMES_TILL_NEW_ROUND = 10 * 60;

    public StatManager(){
        mScore = 0;
        frameCount = 0;
    }
    public void incrementMoveCount(){
        moveCount++;
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
        updateLeaderBoard();
        mScore = 0;
        frameCount = 0;
    }


    public void updateLeaderBoard(){
        if(mScore > leaderBoard[0]){
            leaderBoard[2] = leaderBoard[1];
            leaderBoard[1] = leaderBoard[0];
            leaderBoard[0] = mScore;
        }
        else if(mScore > leaderBoard[1]){
            leaderBoard[2] = leaderBoard[1];
            leaderBoard[1] = mScore;
        }
        else if(mScore > leaderBoard[2]) {
            leaderBoard[2] = mScore;
        }
    }
}
