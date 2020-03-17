package com.example.towerdefencegamephase1;

import android.graphics.Point;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/*
       This class stores information on the current display information
       converts display information into pixes
*/
public class DisplayManger {

    //number of pixes with respect to the display size
    private int numberHorizontalPixels;
    private int numberVerticalPixels;
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    public DisplayManger(WindowManager window) {
        Display display = window.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        window.getDefaultDisplay().getMetrics(displayMetrics);

        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
    }

    //getters to access pixel count
    public int getScreenWidth() { return numberHorizontalPixels; }
    public int getScreenHeight() { return numberVerticalPixels; }

    public DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }
}
