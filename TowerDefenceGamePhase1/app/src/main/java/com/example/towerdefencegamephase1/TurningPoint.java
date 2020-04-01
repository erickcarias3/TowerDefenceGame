package com.example.towerdefencegamephase1;

public class TurningPoint extends Position {
    private Heading turningDirection;
    public TurningPoint(Position setPosition, Heading turningDirection){
        this.turningDirection = turningDirection;
        this.x = setPosition.x;
        this.y = setPosition.y;
    }

    public Heading getTurningDirection() {
        return turningDirection;
    }
}
