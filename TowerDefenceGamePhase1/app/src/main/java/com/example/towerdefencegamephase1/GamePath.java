package com.example.towerdefencegamephase1;

import android.graphics.Path;
import android.graphics.Rect;

//the game path is used to hold enemy path information
public class GamePath {

    private Path roadPath;
    private int numberOfPoints = 8;
    private TurningPoint[] mapPoints;

    public GamePath(Rect[][] mapCells, int cellWidth){
        mapPoints = new TurningPoint[numberOfPoints];

        createPath(mapCells, cellWidth);
    }

    //created the turning poing array
    private void addTurningPoint(int positionInArray, Position position, Heading heading){
        mapPoints[positionInArray] = new TurningPoint(position, heading);
        addToPath(positionInArray, position);
    }

    //creates the path
    private void addToPath(int positionInRoadMap, Position position){
        if(positionInRoadMap == 0){
            roadPath.moveTo(position.x,position.y);
        }
        else{
            roadPath.lineTo(position.x, position.y);
            roadPath.moveTo(position.x,position.y);
        }
    }

    public Path getPath() {
        return roadPath;
    }

    public Position getStart(){
        return mapPoints[0];
    }

    public int follow(Enemy enemy){
        return nextPosition(enemy.getLocationOnMap() , enemy.getSpeed(), enemy.getNextTurn());
    }

    //updates the position with the next turn that is anticipated
    public int nextPosition(Position location, int speed, int nextPointOnMap){
        Heading direction = mapPoints[nextPointOnMap - 1].getTurningDirection();

        switch (direction) {
            case UP:
                if(location.y - speed <= mapPoints[nextPointOnMap].y){
                    location.x = mapPoints[nextPointOnMap].x;
                    location.y = mapPoints[nextPointOnMap].y;
                    nextPointOnMap += 1;
                }
                else{
                    location.y-= speed;
                }
                break;

            case RIGHT:
                if(location.x + speed >= mapPoints[nextPointOnMap].x){
                    location.x = mapPoints[nextPointOnMap].x;
                    location.y = mapPoints[nextPointOnMap].y;
                    nextPointOnMap += 1;
                }
                else{
                    location.x+= speed;
                }
                break;

            case DOWN:
                if(location.y + speed >= mapPoints[nextPointOnMap].y){
                    location.x = mapPoints[nextPointOnMap].x;
                    location.y = mapPoints[nextPointOnMap].y;
                    nextPointOnMap += 1;
                }
                else{
                    location.y+= speed;

                }
                break;

            case LEFT:
                if(location.x - speed <= mapPoints[nextPointOnMap].x){
                    location.x = mapPoints[nextPointOnMap].x;
                    location.y = mapPoints[nextPointOnMap].y;
                    nextPointOnMap += 1;
                }
                else{
                    location.x-= speed;
                }
                break;
        }

        if(nextPointOnMap == mapPoints.length  && location.x >= mapPoints[mapPoints.length - 1].x){
            nextPointOnMap = 1;
        }

        return nextPointOnMap;

    }

    /*
         These are the hardcoded points on the map that create the path following the bitmap image
         the path is created from the left of the screen to right of the screen
         the starting point is the leftmost point while the end is the rightmost point
      */
    private void createPath(Rect[][] gridCells, int cellWidth){
        int positionInArray = 0;

        roadPath = new Path();
        Position position = new Position();

        //starting point (Start of path)
        position.x = (float) gridCells[10][0].centerX() - cellWidth/2;
        position.y = (float) gridCells[10][0].centerY();
        addTurningPoint(positionInArray, position, Heading.RIGHT);
        positionInArray++;

        //first up
        position.x = (float) gridCells[10][3].centerX();
        position.y = (float) gridCells[10][3].centerY();
        addTurningPoint(positionInArray, position, Heading.UP);
        positionInArray++;

        //first right turn
        position.x = (float) gridCells[4][3].centerX();
        position.y = (float) gridCells[4][3].centerY();
        addTurningPoint(positionInArray, position, Heading.RIGHT);
        positionInArray++;


        //second down turn
        position.x = (float) gridCells[4][7].centerX();
        position.y = (float) gridCells[4][7].centerY();
        addTurningPoint(positionInArray, position, Heading.DOWN);
        positionInArray++;


        //third right turn
        position.x = (float) gridCells[12][7].centerX();
        position.y = (float) gridCells[12][7].centerY();
        addTurningPoint(positionInArray, position, Heading.RIGHT);
        positionInArray++;

        //third left turn
        position.x = (float) gridCells[12][12].centerX();
        position.y = (float) gridCells[12][12].centerY();
        addTurningPoint(positionInArray, position, Heading.UP);
        positionInArray++;

        //third right turn
        position.x = (float) gridCells[8][12].centerX();
        position.y = (float) gridCells[8][12].centerY();
        addTurningPoint(positionInArray, position, Heading.RIGHT);
        positionInArray++;

        //ending point(End of path)
        position.x = (float) gridCells[8][19].centerX() + cellWidth/2;
        position.y = (float) gridCells[8][19].centerY();
        addTurningPoint(positionInArray, position, Heading.RIGHT);


    }

}
