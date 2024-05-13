package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class TimeStop extends Powerup{

    public TimeStop(Context context, Screen s){
        super(context, s, 12, 6, 15);
    }
    @Override
    public void initializeBitmap(Context context) {
        mBitmapItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.freezepowerup);
        // Resize the bitmap
        mBitmapItem = Bitmap.createScaledBitmap(mBitmapItem, s.blockSize, s.blockSize, false);
    }

    @Override
    public void activationEffect() {
        //SoundPool logic
    }

}
