package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class House extends Food{

    // The location of the apple on the grid
    // Not in pixels

    /// Set up the apple in the constructor
    House(Context context, Screen s){
        super(context, s, 1, 4, 1, 1);
    }

    @Override
    public void initializeBitmap(Context context) {
        mBitmapItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.movershouse1);
        // Resize the bitmap
        mBitmapItem = Bitmap.createScaledBitmap(mBitmapItem, s.blockSize, s.blockSize, false);
    }
}