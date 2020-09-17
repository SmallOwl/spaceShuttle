package com.example.spaceshuttle.GameObjects.Shuttle;

import com.example.spaceshuttle.Constants;

import java.util.Random;

public class shuttle {

    private final int[][] shuttleMatrix = {
                                     {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,1,2,2,2,1,0,0,0,0,0,0},
                                     {0,0,0,0,0,1,2,2,2,2,2,1,0,0,0,0,0},
                                     {0,0,0,0,0,1,2,2,2,2,2,1,0,0,0,0,0},
                                     {0,0,0,0,0,1,2,2,2,2,2,1,0,0,0,0,0},
                                     {0,0,0,0,0,0,1,2,2,2,1,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0},
                                     {0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0},
                                     {0,0,0,0,1,2,2,2,2,2,2,2,1,0,0,0,0},
                                     {0,0,0,1,1,2,2,2,2,2,2,2,1,1,0,0,0},
                                     {0,0,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0},
                                     {0,1,0,0,0,0,0,1,2,1,0,0,0,0,0,1,0},
                                     {0,1,0,0,0,0,1,2,2,2,1,0,0,0,0,1,0},
                                     {0,1,0,0,0,1,2,2,2,2,2,1,0,0,0,1,0},
                                     {0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0},
                                     {1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1}};

    private final int[][][] fireMatrix = {{{0,1,1,2,1,1,0},
                                     {0,0,0,1,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0}},

                                    {{0,1,2,1,2,1,0},
                                     {0,0,1,2,1,0,0},
                                     {0,0,0,1,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0}},

                                    {{1,2,1,2,1,2,1},
                                     {0,1,2,1,2,1,0},
                                     {0,0,1,2,1,0,0},
                                     {0,0,0,1,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0}},

                                    {{1,2,1,2,1,2,1},
                                     {0,1,2,1,2,1,0},
                                     {0,1,1,2,1,1,0},
                                     {0,0,1,1,1,0,0},
                                     {0,0,0,1,0,0,0},
                                     {0,0,0,0,0,0,0},
                                     {0,0,0,0,0,0,0}},

                                    {{1,2,1,2,1,2,1},
                                     {0,1,2,1,2,1,0},
                                     {0,1,1,2,1,1,0},
                                     {0,0,1,2,1,0,0},
                                     {0,0,1,1,1,0,0},
                                     {0,0,0,1,0,0,0},
                                     {0,0,0,0,0,0,0}}};

    private float posX;
    private float posY;
    private int engine;
    private int engineNeed;
    private int maxEngine;
    private int minEngine;
    private int fuel;
    private int maxFuel;
    private int minFuel;
    private int angle;
    private int angleNeed;
    private int maxAngle;
    private int minAngle;
    private float speedX;
    private float speedY;
    private int weight;

    public void init(){
        engine = Constants.defaultEngine;
        maxEngine = Constants.maxEngine;
        minEngine = Constants.minEngine;
        fuel = Constants.defaultMinFuel + new Random().nextInt(Constants.defaultMaxFuel - Constants.defaultMinFuel);
        maxFuel = Constants.maxFuel;
        minFuel = Constants.minFuel;
        angle = Constants.defaultAngle;
        maxAngle = Constants.maxAngle;
        minAngle = Constants.minAngle;
        posX = Constants.defaultShuttleX;
        posY = Constants.defaultShuttleY;
        speedX = Constants.defaultShuttleSpeedX;
        speedY = Constants.defaultShuttleSpeedY;
        weight = Constants.defaultShuttleWeight;
    }

    public float getPosX() {
        return posX;
    }
    public float getPosY() {
        return posY;
    }
    public void setPosX(float posX) {
        this.posX = posX;
    }
    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getEngine() {
        return engine;
    }
    public int getMaxEngine() {
        return maxEngine;
    }
    public int getMinEngine() {
        return minEngine;
    }
    public int getFuel() {
        return fuel;
    }
    public int getMaxFuel() {
        return maxFuel;
    }
    public int getMinFuel() {
        return minFuel;
    }
    public int getAngle() {
        return angle;
    }
    public int getMaxAngle() {
        return maxAngle;
    }
    public int getMinAngle() {
        return minAngle;
    }
    public void setEngine(int engine) {
        if(engine > minEngine && engine < maxEngine){
            this.engine = engine;
        }else if(engine <= minEngine){
            this.engine = minEngine;
        }else if(engine >= maxEngine){
            this.engine = maxEngine;
        }
        if(fuel == 0){
            this.engine = 0;
        }else if(fuel <= getFireLevel()){
            setMaxFuelEngine();
        }
    }
    public void setFuel(int fuel) {
        if(fuel > minFuel && fuel < maxFuel){
            this.fuel = fuel;
        }else if(fuel <= minFuel){
            this.fuel = minFuel;
        }else if(fuel >= maxFuel){
            this.fuel = maxFuel;
        }
    }
    public void setAngle(int angle) {
        if(angle > minAngle && angle < maxAngle){
            this.angle = angle;
        }else if(angle <= minAngle){
            this.angle = minAngle;
        }else if (angle >= maxAngle){
            this.angle = maxAngle;
        }
    }
    private void setMaxFuelEngine() {
        while(fuel < getFireLevel() + 1){
            engine--;
        }
    }

    public int getAngleNeed() {
        return angleNeed;
    }
    public void setAngleNeed(int angleNeed) {
        if(angleNeed > minAngle && angleNeed < maxAngle){
            this.angleNeed = angleNeed;
        }else if(angleNeed <= minAngle){
            this.angleNeed = minAngle;
        }else if (angleNeed >= maxAngle){
            this.angleNeed = maxAngle;
        }
    }
    public int getEngineNeed() {
        return engineNeed;
    }
    public void setEngineNeed(int engineNeed) {
        if(engineNeed > minEngine && engineNeed < maxEngine){
            this.engineNeed = engineNeed;
        }else if(engineNeed <= minEngine){
            this.engineNeed = minEngine;
        }else if(engineNeed >= maxEngine){
            this.engineNeed = maxEngine;
        }
        if(fuel == 0){
            this.engineNeed = 0;
        }
    }

    public float getSpeedX() {
        return speedX;
    }
    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }
    public float getSpeedY() {
        return speedY;
    }
    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getFireLevel(){
        if(engine <= 0){
            return 0;
        }else{
            return engine/21;
        }
    }

    public int[][] getShuttleMatrix() {
        return shuttleMatrix;
    }

    public int[][][] getFireMatrix() {
        return fireMatrix;
    }
}
