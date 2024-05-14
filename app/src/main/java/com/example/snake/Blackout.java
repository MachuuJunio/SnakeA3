
package com.example.snake;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Blackout extends Obstacle{
    public Blackout(Context context, Screen s){
        super(context, s, 3, 6, 10);
    }

    @Override
    public void initializeBitmap(Context context) {
        mBitmapItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
        mBitmapItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackouticon);
        // Resize the bitmap
        mBitmapItem = Bitmap.createScaledBitmap(mBitmapItem, s.blockSize, s.blockSize, false);
    }

    @Override
    public void activationEffect() {

    }
}
