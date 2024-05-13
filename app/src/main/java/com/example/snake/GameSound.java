package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.SurfaceView;

import java.io.IOException;

public class GameSound extends SurfaceView {
    protected SoundPool mSP;
    protected final int mEat_ID;
    protected final int mCrash_ID;

    protected final int mFreeze_ID;
    protected final int mBgMusic_ID;



    public GameSound(Context context) {
        super(context);

        //Checks whether SDK_VERSION is atleast lollipop
        boolean isAtLeastLollipopVersion = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

        initializeSoundPool(isAtLeastLollipopVersion);

        AssetManager assetManager = context.getAssets();
        mEat_ID = assignSound("get_apple.ogg", assetManager);
        mCrash_ID = assignSound("snake_death.ogg", assetManager);
        mBgMusic_ID = assignSound("bgmusic.ogg", assetManager);
        mFreeze_ID = assignSound("freezesound.ogg", assetManager);
    }

    /**
     * Loods a sound for asset file descriptor if the file exists
     * @param fileName the local name of the file
     * @param assetManager provides access to resources
     * @return a meaningful integer value if the file exist, else -1
     */
    private int assignSound(String fileName, AssetManager assetManager){
        try {
            AssetFileDescriptor descriptor = assetManager.openFd(fileName);
            return mSP.load(descriptor, 0);
        }catch(IOException e){
            return -1;
        }
    }

    /**
     * Plays sound based on the id
     * @param id an integer that specifies what sound should be played
     */
    protected void play(int id){
       mSP.play(id, 1, 1, 0, 0, 1);
    }

    /**
     * Initializes the SoundPool depending on whether the device is running an android version that
     * is atleast Lollipop (API level 21)
     * @param isAtLeastLollipopVersion whether the device is running Android version later than
     *                                 or equivalent to API level 21.
     */
    private void initializeSoundPool(boolean isAtLeastLollipopVersion) {
        if (isAtLeastLollipopVersion) {
            atleastLollipop();
        } else {
            beforeLollipop();
        }
    }

    /**
     * Initializes the Soundpool for Android versions earier than Lollipop
     */
    private void atleastLollipop(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSP = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    /**
     * Initializes the SoundPool for Android versions later or at Lollipop
     */
    private void beforeLollipop(){
        mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }
}
