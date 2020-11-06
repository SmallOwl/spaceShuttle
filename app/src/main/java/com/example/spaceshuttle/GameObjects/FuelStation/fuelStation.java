package com.example.spaceshuttle.GameObjects.FuelStation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.spaceshuttle.Constants;

public class fuelStation {

    private static int[][] fuelStationMatrix = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,1,2,1,2,1,2,1,2,1,2,1,1,0,0,0,0},
            {0,1,1,2,1,2,1,2,1,2,1,2,1,0,0,0,0},
            {0,1,2,1,2,1,2,1,2,1,2,1,1,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0},
            {0,0,0,1,0,0,0,0,0,0,1,0,0,1,1,0,0},
            {0,0,0,1,0,1,1,1,1,1,1,0,0,1,0,0,0},
            {0,0,0,1,0,1,2,2,2,2,1,1,1,1,0,0,0},
            {0,0,0,1,0,1,2,2,2,2,1,0,2,1,0,0,0},
            {0,0,0,1,0,1,1,1,1,1,1,0,2,1,0,0,0},
            {0,0,0,1,0,1,1,1,1,1,1,0,2,1,0,0,0},
            {0,0,0,1,0,1,1,2,2,1,1,2,1,0,0,0,0},
            {0,0,0,1,0,1,1,1,1,1,1,1,0,0,0,0,0},
            {0,0,0,1,0,1,2,2,2,2,1,0,0,0,0,0,0},
            {0,0,0,1,0,1,1,1,1,1,1,0,0,0,0,0,0},
    };

    private int x;
    private int y;

    private static Bitmap bmpBigDraw;
    private static Bitmap bmpSmallDraw;

    public static void initFuelStationBitmap(int scaleSmallCoef, int scaleBigCoef){
        Paint p = new Paint();
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL);
        bmpSmallDraw = Bitmap.createBitmap(Constants.fuelStationWidth*scaleSmallCoef,Constants.fuelStationHeight*scaleSmallCoef, Bitmap.Config.ARGB_8888);
        bmpBigDraw = Bitmap.createBitmap(Constants.fuelStationWidth*scaleBigCoef,Constants.fuelStationHeight*scaleBigCoef, Bitmap.Config.ARGB_8888);
        Canvas canvasSmall = new Canvas(bmpSmallDraw);
        Canvas canvasBig = new Canvas(bmpBigDraw);
        for(int i = 0; i < Constants.fuelStationHeight; i ++){
            for(int j = 0; j < Constants.fuelStationWidth; j ++){
                if(fuelStationMatrix[i][j] == 1){
                    p.setColor(Constants.fuelStationDarkColor);
                }else if(fuelStationMatrix[i][j] == 2){
                    p.setColor(Constants.fuelStationLightColor);
                }else{
                    p.setColor(Color.TRANSPARENT);
                }
                canvasSmall.drawRect(i*scaleSmallCoef, j*scaleSmallCoef, i*scaleSmallCoef + scaleSmallCoef, j*scaleSmallCoef + scaleSmallCoef,p);
                canvasBig.drawRect(i*scaleBigCoef, j*scaleBigCoef, i*scaleBigCoef + scaleBigCoef, j*scaleBigCoef + scaleBigCoef,p);
            }
        }
    }

    public static Bitmap getBmpBigDraw() {
        return bmpBigDraw;
    }

    public static Bitmap getBmpSmallDraw() {
        return bmpSmallDraw;
    }

    public static int[][] getFuelStationMatrix() {
        return fuelStationMatrix;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
