package com.example.towerdefencegamephase1;

import java.util.LinkedHashMap;
import java.util.Map;
import android.graphics.Path;
import android.graphics.Rect;

public class RoadMap {
    private Path roadPath;
    private Position start = new Position();
    private LinkedHashMap<Position,Heading> turningPoints;

    public RoadMap(Rect[][] mapCells, int cellWidth){
        turningPoints = new LinkedHashMap();

        createPath(mapCells, cellWidth);


    }

    public void addTurningPoint(Position position, Heading heading){
        turningPoints.put(position, heading);
    }

    public void addToPath(float x, float y){

    }

    public Path getPath() {
        return roadPath;
    }

    public Position getStart(){
        return start;
    }

    public Heading checkTurningPoints(Position position, Heading direction){

        if(turningPoints.containsKey(position)){
            return turningPoints.get(position);
        }
        else {
            return direction;
        }
    }

    /*
         These are the hardcoded points on the map that create the path following the bitmap image
         the path is created from the left of the screen to right of the screen
         the starting point is the leftmost point while the end is the rightmost point
      */
    private void createPath(Rect[][] gridCells, int cellWidth){

        roadPath = new Path();
        Position position = new Position();

        //starting point (Start of path)
        start.x = (float) gridCells[10][0].centerX() - cellWidth/2;
        start.y = (float) gridCells[10][0].centerY();
        addTurningPoint(position, Heading.RIGHT);
        roadPath.moveTo(start.x,start.y);

        //first up
        position.x = (float) gridCells[10][3].centerX();
        position.y = (float) gridCells[10][3].centerY();
        addTurningPoint(position, Heading.UP);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);

        //first right turn
        position.x = (float) gridCells[4][3].centerX();
        position.y = (float) gridCells[4][3].centerY();
        addTurningPoint(position, Heading.RIGHT);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);


        //second down turn
        position.x = (float) gridCells[4][7].centerX();
        position.y = (float) gridCells[4][7].centerY();
        addTurningPoint(position, Heading.DOWN);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);


        //third right turn
        position.x = (float) gridCells[12][7].centerX();
        position.y = (float) gridCells[12][7].centerY();
        addTurningPoint(position, Heading.RIGHT);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);

        //third left turn
        position.x = (float) gridCells[12][12].centerX();
        position.y = (float) gridCells[12][12].centerY();
        addTurningPoint(position, Heading.UP);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);

        //third right turn
        position.x = (float) gridCells[8][12].centerX();
        position.y = (float) gridCells[8][12].centerY();
        addTurningPoint(position, Heading.RIGHT);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);

        //ending point(End of path)
        position.x = (float) gridCells[8][19].centerX() + cellWidth/2;
        position.y = (float) gridCells[8][19].centerY();
        addTurningPoint(position, Heading.RIGHT);
        roadPath.lineTo(position.x, position.y);
        roadPath.moveTo(position.x,position.y);

    }

}
