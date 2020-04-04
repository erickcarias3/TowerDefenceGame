package com.example.towerdefencegamephase1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class GameGrid {

    private Dimesion gridDimensions;
    private Dimesion blockSize;
    private Paint paint = new Paint();
    protected Rect[][] gridCells;
    private Rect touchedCell;
    private Rect touchedTool;
    private ArrayList<Rect> selectedTools = new ArrayList<>();


    public GameGrid(int numColumns, int numRows, int screenWidth, int screenHeight) {

        gridDimensions = new Dimesion(numRows,numColumns);
        gridCells = new Rect[numColumns][numRows];
        blockSize = new Dimesion(screenWidth / numColumns, screenHeight/ numRows);
        createGrid();

    }

    //This function draws all the grid gridCells in the 2D array
    //it traverses each cell 1 by 1 and draws its borders individually
    //It also checks if there is a touched cell that needs to be drawn
    public void drawGrid(Canvas canvas){

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);

        for(int i = 0; i < gridDimensions.getHeight(); i++){
            for(int k = 0; k < gridDimensions.getWidth(); k++){
                canvas.drawRect(gridCells[i][k],paint);
            }
        }

        if(touchedCell != null){
            createHitBox(touchedCell, canvas);
        }

        if(touchedTool != null){
            createHitBox(touchedTool,canvas);
        }

        if(!selectedTools.isEmpty()){
            for (Rect tool : selectedTools) {
                createHitBox(tool,canvas);
            }
        }



    }


    //This is logic for the initialization of the grid
    public void createGrid(){
        int x = 0;
        int y = 0;
        for(int i = 0; i < gridDimensions.getHeight(); i++){
            for(int k = 0; k < gridDimensions.getWidth(); k++){
                gridCells[i][k] = new Rect(x,y,x+blockSize.getWidth(),y+blockSize.getHeight());
                x = blockSize.getWidth() + x + 1 ;
                if(k == gridDimensions.getWidth() - 1){
                    x = 0;
                }
            }
            y = blockSize.getHeight() + y + 1 ;
        }
    }

    //this function checks whether two touched points are inside any of the rectangles in the 2D Matrix
    //if it finds a match it sets the found cell as the touched cell in the grid
    public Rect checkGrid(float touchedX, float touchedY){


        for(int i = 0; i < gridDimensions.getHeight(); i++) {
            for (int k = 0; k < gridDimensions.getWidth(); k++) {
                if (gridCells[i][k].contains((int) touchedX , (int) touchedY )){
                    touchedCell = gridCells[i][k];
                    System.out.println("----Cell:" + "[" + i + "]" +"[" + k + "]" + "Touched ----");
                    break;
                }
            }
        }
        return touchedCell;
    }

    //draws a Hitbox around the passed cell (Rectangle)
    public void createHitBox(Rect rect, Canvas canvas){
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        canvas.drawRect(rect,paint);
    }

    public void addToSelectedToolArray(float xFound,float yFound){
        Rect selectedToolLocation = checkGrid(xFound,yFound);
        invalidateTouchedCell();
        selectedTools.add(selectedToolLocation);

    }

    public void invalidateSelectedTools(){
        selectedTools.clear();
    }

    //sets the touched cell to null so that the touch Hitbox is disabled and
    //is not drawn in the draw method
    public void invalidateTouchedCell(){
        touchedCell = null;
    }

    public void invalidateTouchedTool(){touchedTool = null;}

    public void setTouchedTool(Rect rect){
        touchedTool = rect;
    }

    public void invalidateHitboxes(){
        touchedCell = null;
        touchedTool = null;
        selectedTools.clear();
    }

    //Getters for grid information

    public Rect getTouchedTool() {
        return touchedTool;
    }

    public int getGridWidth() {
        return this.gridDimensions.getWidth();
    }

    public ArrayList<Rect> getSelectedGridSquares(){
        return selectedTools;
    }

    public int getGridHeight() {
        return this.gridDimensions.getHeight();
    }

    public int getCellHeight() {
        return blockSize.getHeight();
    }

    public int getCellWidth() {
        return blockSize.getWidth();
    }


}
