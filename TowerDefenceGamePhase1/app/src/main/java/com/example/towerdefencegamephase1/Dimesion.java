package com.example.towerdefencegamephase1;

//a class to hold dimensions
public class Dimesion {
    private int width;
    private int height;

    public Dimesion(int width, int height){
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int length) {
        this.height = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
