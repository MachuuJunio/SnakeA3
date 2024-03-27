package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;
public abstract class GameObject {
    private Point range;

    public GameObject(){

    }
    abstract void draw(Canvas canvas, Paint paint);
}
