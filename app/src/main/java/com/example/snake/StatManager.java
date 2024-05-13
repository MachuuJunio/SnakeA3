package com.example.snake;

public class StatManager {
    protected int mScore;
    private int moveCount;
    public int[] leaderBoard = {0,0,0};
    public boolean toggleDead;
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
        updateLeaderBoard();
        mScore = 0;
        moveCount = 0;
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
