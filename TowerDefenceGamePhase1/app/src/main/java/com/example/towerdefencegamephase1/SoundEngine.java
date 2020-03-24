package com.example.towerdefencegamephase1;

public class SoundEngine {
    private SoundStrategy strategy;

    SoundEngine(SoundStrategy strategy) {
        this.strategy = strategy;
    }

    public void strategyPlaySound1() {
        strategy.playSound1();
    }

    public void strategyPlaySound2() {
        strategy.playSound2();;
    }
}
