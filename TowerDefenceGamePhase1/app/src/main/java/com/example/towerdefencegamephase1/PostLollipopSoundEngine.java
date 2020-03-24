package com.example.towerdefencegamephase1;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class PostLollipopSoundEngine implements SoundStrategy {

    // for playing sound effects
    private SoundPool mSP;
    // Variables to store the sound files in memory
    //private int mSoundName_ID = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    PostLollipopSoundEngine(Context context) {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSP = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();
/*
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }
*/
    }

    public void playSound1() {
        //mSP.play(mEat_ID, 1, 1, 0, 0, 1);
    }

    public void playSound2() {
        //mSP.play(mCrashID, 1, 1, 0, 0, 1);
    }
// Add the playSoundName()s
}
