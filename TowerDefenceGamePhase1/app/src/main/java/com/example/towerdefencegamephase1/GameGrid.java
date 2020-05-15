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

    private Rect selectedTower;
    private Rect[] guideCells = new Rect[2];
   // private ArrayList<Rect> selectedTools = new ArrayList<>();


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

        if(guideCells[0] != null){
            for (Rect rect : guideCells) {
                createHitBox(rect,canvas);
            }
        }

        if(selectedTower != null){
            createHitBox(selectedTower,canvas);
        }

    }

    public void drawSelectedTowerHitbox(Canvas canvas){
        if(selectedTower != null){
            createHitBox(selectedTower,canvas);
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
    //if it finds a match it adds the touched cell and the cell directly on top
    //to create an array of cells to then draw later
    public Rect[] checkGrid(float touchedX, float touchedY){
        Rect[] touchedCells = new Rect[2];

        for(int i = 0; i < gridDimensions.getHeight(); i++) {
            for (int k = 0; k < gridDimensions.getWidth(); k++) {
                if (gridCells[i][k].contains((int) touchedX , (int) touchedY )){
                    touchedCells[0] = gridCells[i][k];
                    System.out.println("----Cell:" + "[" + i + "]" +"[" + k + "]" + "Touched ----");
                    touchedCells[1]= gridCells[i - 1][k] ;

                    break;
                }
            }
        }
        return touchedCells;
    }

    //draws a Hitbox around the passed cell (Rectangle)
    public void createHitBox(Rect rect, Canvas canvas){
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        canvas.drawRect(rect,paint);
    }


    public void invalidateGuideCells(){
        guideCells = new Rect[2];
    }

    //sets the touched cell to null so that the touch Hitbox is disabled and
    //is not drawn in the draw method


    public void invalidateTouchedTool(){
        selectedTower = null;
    }

    public void setSelectedTower(Rect rect){
        selectedTower = rect;
    }

    public void invalidateHitboxes(){
        selectedTower = null;
        invalidateGuideCells();
    }

    //Getters for grid information

    public Rect getSelectedTower() {
        return selectedTower;
    }

    public int getGridWidth() {
        return this.gridDimensions.getWidth();
    }

    public int getGridHeight() {
        return this.gridDimensions.getHeight();
    }

    public void createGuideBox(Position position){
        guideCells = checkGrid(position.x, position.y);
    }

    public int getCellHeight() {
        return blockSize.getHeight();
    }

    public int getCellWidth() {
        return blockSize.getWidth();
    }


}
