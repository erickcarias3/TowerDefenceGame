package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;

public class Enemy extends GameObject {

    private Heading direction;

    public Enemy(float startingX, float startingY, Bitmap enemyBitmap){
        location = new Position();
        location.x = startingX;
        location.y = startingY;
        this.objectBitmap = enemyBitmap;
        direction = Heading.RIGHT;
    }

    public void move(){
        switch (direction) {
            case UP:
                location.y-= 10;
                break;

            case RIGHT:
                location.x+= 10;
                break;

            case DOWN:
                location.y+= 10;
                break;

            case LEFT:
                location.x-= 10;
                break;
        }
    }

    public void setDirection(Heading heading){
        this.direction = heading;
    }

    public Heading getDirection() {
        return direction;
    }

    public void setPosition(Position newPosition){
        this.location = newPosition;
    }

    public Position getLocationOnMap(){
        return location;
    }

    public void drawSelf(Canvas canvas){
        canvas.drawBitmap(objectBitmap,location.x,location.y, null );
    }

}
