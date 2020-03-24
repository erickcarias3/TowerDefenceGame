package com.example.towerdefencegamephase1;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

class PreLollipopSoundEngine implements SoundStrategy {

    // For playing the sound effects
    private SoundPool mSP;

    // Variables for holding the sound files
    // private int mSoundName_ID = -1; ...

    public PreLollipopSoundEngine(Context context) {
        mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void playSound1() {

    }

    @Override
    public void playSound2() {

    }
}
