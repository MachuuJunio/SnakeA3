package com.example.snake;

public class StatManager {
    protected int mScore;
    private int moveCount;

    public StatManager(){
        mScore = 0;
        moveCount = 0;
    }

    public void incrementMoveCount(){
        moveCount++;
    }

    public void incrementScore(){
        mScore++;
    }

    public void reset(){
        mScore = 0;
        moveCount = 0;
    }
}
