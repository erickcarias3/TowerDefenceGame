package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

public class MapBackground {

    Bitmap map;

    public MapBackground(Context context,DisplayManger displayManger){
        map = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.map);
        map = Bitmap.createScaledBitmap(map,displayManger.getScreenWidth(),displayManger.getScreenHeight(),true);

    }

    public Bitmap getMap() {
        return map;
    }
    private Bitmap getScaledBitMapBaseOnScreenSize(DisplayMetrics metrics){

        Bitmap scaledBitmap=null;
        try {


            int width = map.getWidth();
            int height = map.getHeight();

            float scaleWidth = metrics.scaledDensity;
            float scaleHeight = metrics.scaledDensity;

            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

            // recreate the new Bitmap
            scaledBitmap = Bitmap.createBitmap(map, 0, 0, width, height, matrix, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return scaledBitmap;
    }
}
