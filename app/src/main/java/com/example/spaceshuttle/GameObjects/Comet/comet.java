package com.example.spaceshuttle.GameObjects.Comet;

import com.example.spaceshuttle.Constants;

public class comet {

    private int[][] cometMatrix = {
            {0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0},
            {0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,1,1,1,1,2,1,1,1,2,2,1,1,1,0,0},
            {0,0,1,2,1,1,2,1,2,1,1,1,2,2,1,0,0},
            {0,1,1,1,2,1,1,2,1,2,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,0},
            {1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,1,1},
            {1,1,1,1,2,2,2,1,1,1,1,1,1,2,2,2,1},
            {1,1,1,2,2,2,2,1,1,1,1,1,1,2,2,2,1},
            {1,1,1,2,2,2,1,1,2,2,1,1,1,2,2,2,1},
            {1,1,1,2,2,2,1,2,2,2,2,1,1,2,2,1,1},
            {0,1,1,2,2,2,1,1,2,2,1,1,1,2,2,1,0},
            {0,1,1,1,2,2,2,1,1,1,1,1,2,2,1,1,0},
            {0,0,1,1,1,2,2,2,1,1,1,2,2,1,1,0,0},
            {0,0,1,1,1,1,2,2,2,2,2,2,1,1,1,0,0},
            {0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0},
    };

    private float posX;
    private float posY;
    private float speedX;
    private float speedY;
    private int angle;
    private int weight;

    public comet(){
        this.weight = Constants.defaultCometWeight;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
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

    public int[][] getCometMatrix() {
        return cometMatrix;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
