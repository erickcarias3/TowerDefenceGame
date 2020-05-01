package com.example.towerdefencegamephase1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Stack;

public class Tower extends GameObject {
    Rect[] occupiedCells = new Rect[2];
    float targetRadius = 100;
    Paint paint = new Paint();
    Targeting aimPriority = Targeting.closest;
    ArrayList<Enemy> enemiesWithinRadius = new ArrayList<>();
    Enemy currentTarget = null;


    public Tower(float startingX, float startingY, Bitmap towerBitmap){
        super(startingX, startingY, towerBitmap);
    }

    public void placeTower(Rect[] occupiedCells) {
        this.occupiedCells = occupiedCells;
        Position newPosition = new Position(occupiedCells[1].centerX() , occupiedCells[1].centerY());
        setPosition(newPosition);
    }

    public void addTarget(Enemy newTarget){
        enemiesWithinRadius.add(newTarget);
    }

    public boolean contains(int x, int y){
        if ((x - location.x) * (x - location.x) +
                (y - location.y) * (y - location.y) <= targetRadius * targetRadius){

            System.out.println("enemy within radius added to target list");
            return true;
        }
        return false;
    }

    public void createTargetEnemy(){
        if(currentTarget == null){
            if(enemiesWithinRadius.isEmpty()){
                return;
            }else{
                findNewTarget();
            }
        }
        else{
            if(enemiesWithinRadius.contains(currentTarget)){
                enemiesWithinRadius.clear();
                return;
            }
            else if(enemiesWithinRadius.isEmpty()){
                currentTarget = null;
            }
            else if(enemiesWithinRadius.size() == 1){
                currentTarget = enemiesWithinRadius.get(0);
            }
            else{
                findNewTarget();
            }
        }
        enemiesWithinRadius.clear();

    }

    private void findNewTarget(){

        if(aimPriority == Targeting.closest){
            Enemy closestEnemy = enemiesWithinRadius.get(0);
            for(int i = 1; i < enemiesWithinRadius.size(); i++){
                if(getLengthFromTower(closestEnemy) > getLengthFromTower(enemiesWithinRadius.get(i))){
                    closestEnemy = enemiesWithinRadius.get(i);
                }
            }
            currentTarget = closestEnemy;
        }
        else{
            System.out.println("error");
        }


    }

    private double getLengthFromTower(Enemy enemy){
        double distance;
        distance = Math.sqrt((enemy.location.x - this.location.x) * ( enemy.location.x - this.location.x) +
                        (enemy.location.y - this.location.y) * (enemy.location.y - this.location.y)
                );
        return distance;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(objectBitmap,
                location.x - ((float) objectBitmap.getWidth()/2), (int) location.y - ((float) objectBitmap.getHeight()/4),
                null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(location.x,location.y,targetRadius, paint );

        if(currentTarget!=null){
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1f);
            canvas.drawLine(this.location.x,this.location.y,currentTarget.location.x,currentTarget.location.y, paint);
            System.out.println("drawing shot");
        }
    }
}
