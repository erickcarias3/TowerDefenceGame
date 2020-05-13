package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class RockProjectile extends GameObject {

    private int speed = 5;
    private int pos = 0;
    private boolean hit = false;
    private Bitmap explosionBitmap;
    Position pointA , pointB;
    private Enemy targetedEnemy;
    private ArrayList<Position> pointList = new ArrayList<>();


    public RockProjectile(float startingX, float startingY, Bitmap shotBitmap, Bitmap explosionBitmap, Enemy targetedEnemy , GamePath enemyPath){
        super(startingX, startingY, shotBitmap);
        pointA = new Position();
        pointB = new Position();

        pointA.x = startingX;
        pointA.y = startingY;
        pointB.x = targetedEnemy.location.x;
        pointB.y = targetedEnemy.location.y;

        this.targetedEnemy = targetedEnemy;
        this.explosionBitmap = explosionBitmap;

        createShotPath(enemyPath);
    }



    private void createShotPath(GamePath path){

        int testPosition = targetedEnemy.getNextTurn();
        for(int i = 0; i <= speed ; i++){
            testPosition = path.nextPosition(pointB,targetedEnemy.getSpeed(),testPosition);
        }

        float diffX = pointB.x - pointA.x;
        float diffY = pointB.y - pointA.y;

        float intervalX = diffX / speed + 1;
        float intervalY = diffY / speed + 1;
        for(int i = 0; i <= speed; i++){
            pointList.add(new Position(pointA.x + intervalX * i, pointA.y + intervalY * i));
        }

    }

    public boolean getHitStatus(){
        return hit;
    }

    public void explode(){
        objectBitmap = explosionBitmap;
        targetedEnemy.takeShot();
    }

    public void updateLocation(){

        setPosition(pointList.get(pos));
        System.out.println("X:" + pointList.get(pos).x + " Y:" + pointList.get(pos).y);
        pos++;

        if(pos >= pointList.size()){
            hit = true;
            explode();
        }

    }

}
