package com.example.towerdefencegamephase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


import java.util.ArrayList;

//logic for the towers and the shot animation
public class defenceManager {
    private Tower testTower;
    private ArrayList<RockProjectile> shots = new ArrayList<>();
    private Context context;
    private GameMap gameMap;

    public defenceManager(Context context, GameMap gameMap){
        this.context = context;
        this.gameMap = gameMap;


    }

    //places tower when a user lifts finger
    public void setTower( Rect[] occupiedCells, ArrayList<Tower> allTowers){
        testTower.placeTower(occupiedCells);
        allTowers.add(testTower);
    }

    //creates a new tower
    public void createTower(Context context, Position startingPosition, GameMap gameMap, String defenceType ){

        Bitmap newDefenceBitmap;
        int defenceHeight = gameMap.getCellHeight() * 2 ;
        int defenceWidth = gameMap.getCellWidth();

        if(defenceType == "tower") {
            newDefenceBitmap = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.tower);

        }
        else if(defenceType == "catapult"){
            newDefenceBitmap = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.catapult);
        }
        else{
            newDefenceBitmap = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.archer);
        }

        newDefenceBitmap = Bitmap.createScaledBitmap(newDefenceBitmap, defenceHeight, defenceWidth, true);
        testTower = new Tower(startingPosition.x,startingPosition.y, newDefenceBitmap);

    }

    public void updateCreatedTowerPosition(Position newPosition){
        testTower.setPosition(newPosition);
    }

    public void drawTowers(Canvas viewCanvas, ArrayList<Tower> allTowers) {
        try{
            testTower.draw(viewCanvas);
            for(Tower tower: allTowers){
                tower.draw(viewCanvas);

            }
        }
        catch(NullPointerException o){
            return;
        }
    }

    public void drawShots(Canvas viewCanvas){
        for(RockProjectile shot: shots ){
            shot.draw(viewCanvas);
        }
    }

    public void updateShots(){

        ArrayList<RockProjectile> hitShots = new ArrayList<>();
        if(shots.size() > 0){
            for(RockProjectile shot: shots ){
                if(!shot.getHitStatus()){
                    shot.updateLocation();
                }else{
                    hitShots.add(shot);
                }
            }

            for(RockProjectile hitShot: hitShots){
                shots.remove(hitShot);
            }

        }


    }

    public void checkTowerTargeting(ArrayList<Tower> allTowers , ArrayList<Enemy> spawnedEnemies ){
        for(Tower tower: allTowers) {
            for (Enemy enemy : spawnedEnemies) {
                if(tower.contains((int) enemy.location.x,(int) enemy.location.y) ){
                    tower.addTarget(enemy);
                }
            }
            tower.createTargetEnemy();
        }
    }

    //fires shots appropriately for each tower
    public void fireShots(ArrayList<Tower> allTowers){
        for(Tower tower: allTowers){
            Enemy targetEnemy = tower.getCurrentTarget();
            if(tower.checkShotStatus() && targetEnemy != null){
                createShot(targetEnemy,tower);
            }
        }
    }

    //spawns in a new RockProjectile
    private void createShot(Enemy enemy, Tower tower ){
        int projectileHeight = gameMap.getCellHeight()/ 2 ;
        int projectileWidth = gameMap.getCellWidth()/2;

        int explosionHeight = gameMap.getCellHeight();

        Bitmap newExplosionBitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.explosion);

        Bitmap newProjectileBitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.rock);

        newProjectileBitmap = Bitmap.createScaledBitmap(newProjectileBitmap,projectileHeight,projectileWidth,true);


        newExplosionBitmap = Bitmap.createScaledBitmap(newExplosionBitmap,explosionHeight,projectileWidth,true);


        shots.add(new RockProjectile(tower.getLocationOnMap().x, tower.getLocationOnMap().y, newProjectileBitmap , newExplosionBitmap ,enemy , gameMap.getPath()));
    }

}

