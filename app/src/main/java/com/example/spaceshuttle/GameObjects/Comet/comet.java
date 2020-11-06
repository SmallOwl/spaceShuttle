package com.example.spaceshuttle.GameObjects.Comet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.spaceshuttle.Constants;

public class comet {

    private static int[][] cometMatrix = {
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
    private static Bitmap bmpBigDraw;
    private static Bitmap bmpSmallDraw;

    public comet(){
        this.weight = Constants.defaultCometWeight;
    }

    public static void initCometBitmap(int scaleSmallCoef, int scaleBigCoef){
        Paint p = new Paint();
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL);
        bmpSmallDraw = Bitmap.createBitmap(Constants.cometWidth*scaleSmallCoef,Constants.cometHeight*scaleSmallCoef, Bitmap.Config.ARGB_8888);
        bmpBigDraw = Bitmap.createBitmap(Constants.cometWidth*scaleBigCoef,Constants.cometHeight*scaleBigCoef, Bitmap.Config.ARGB_8888);
        Canvas canvasSmall = new Canvas(bmpSmallDraw);
        Canvas canvasBig = new Canvas(bmpBigDraw);
        for(int i = 0; i < Constants.cometHeight; i ++){
            for(int j = 0; j < Constants.cometWidth; j ++){
                if(cometMatrix[i][j] == 1){
                    p.setColor(Constants.cometDarkColor);
                }else if(cometMatrix[i][j] == 2){
                    p.setColor(Constants.cometLightColor);
                }else{
                    p.setColor(Color.TRANSPARENT);
                }
                canvasSmall.drawRect(i*scaleSmallCoef, j*scaleSmallCoef, i*scaleSmallCoef + scaleSmallCoef, j*scaleSmallCoef + scaleSmallCoef,p);
                canvasBig.drawRect(i*scaleBigCoef, j*scaleBigCoef, i*scaleBigCoef + scaleBigCoef, j*scaleBigCoef + scaleBigCoef,p);
            }
        }
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

    public static Bitmap getSmallComet(){ return bmpSmallDraw; }
    public static Bitmap getBigComet(){ return bmpBigDraw; }
}
