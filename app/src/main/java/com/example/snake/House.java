package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class House extends Food implements GameObject {

    // The location of the apple on the grid
    // Not in pixels

    /// Set up the apple in the constructor
    House(Context context, Screen s){
        super(context, s, 1, 6, 1, 1);
    }

    @Override
    public void initializeBitmap(Context context) {
        mBitmapItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate);
        // Resize the bitmap
        mBitmapItem = Bitmap.createScaledBitmap(mBitmapItem, s.blockSize, s.blockSize, false);
    }
}