package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

class Apple extends Food implements GameObject {

    // The location of the apple on the grid
    // Not in pixels

    /// Set up the apple in the constructor
    Apple(Context context, Screen s){
        super(context, s, 2, 10, 1, 1);
    }

    @Override
    public void initializeBitmap(Context context) {
        mBitmapItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate);
        // Resize the bitmap
        mBitmapItem = Bitmap.createScaledBitmap(mBitmapItem, s.blockSize, s.blockSize, false);
    }
}