package com.example.spaceshuttle.Components.UI.Hand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.example.spaceshuttle.Components.UI.Element;
import com.example.spaceshuttle.Constants;

public class Hand implements Element {

    private handParameters parameters;

    private final int handWidth = 17;
    private final int handHeight = 17;

    private Paint p;
    private int scaleCoef;
    private float xStep;
    private float yStep;
    private Point lastTrajectory;
    private Point newTrajectory;


    private final int[][] handMatrix = {
            {0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,2,1,2,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,2,1,2,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,2,1,2,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,2,1,2,2,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,2,1,2,1,2,2,0,0,0,0},
            {0,0,0,0,0,0,0,2,1,1,1,2,1,2,2,0,0},
            {0,0,0,0,2,2,0,2,1,1,1,1,1,2,1,2,0},
            {0,0,0,2,1,1,2,2,1,1,1,1,1,1,1,1,2},
            {0,0,0,0,2,1,1,2,1,1,1,1,1,1,1,1,2},
            {0,0,0,0,0,2,1,1,1,1,1,1,1,1,1,1,2},
            {0,0,0,0,0,0,2,1,1,1,1,1,1,1,1,1,2},
            {0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2,0},
            {0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,0},
            {0,0,0,0,0,0,0,0,2,2,1,1,1,1,2,2,0},
            {0,0,0,0,0,0,0,0,0,2,1,1,1,1,2,0,0},
            {0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0}};

    public Hand(handParameters parameters, Context context){
        this.parameters = parameters;
        init(context);
    }

    private void init(Context context) {
        int height = (parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot());
        int width = (parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight());
        if(height < width){
            scaleCoef = height/handHeight;
        }else{
            scaleCoef = width/handWidth;
        }
        parameters.setMargin((parameters.getHeight() - scaleCoef*handHeight)/2,
                (parameters.getHeight() - scaleCoef*handHeight)/2,
                (parameters.getWidth() - scaleCoef*handWidth)/2,
                (parameters.getWidth() - scaleCoef*handWidth)/2);
        p = new Paint();
        p.setStyle(Paint.Style.FILL);
        lastTrajectory = parameters.getTrajectory().get(0);
        parameters.setTop(lastTrajectory.y);
        parameters.setHeight(lastTrajectory.x);
        newTrajectory = parameters.getTrajectory().get(1);
        xStep = (newTrajectory.x - lastTrajectory.x)/(handWidth*3);
        yStep = (newTrajectory.y - lastTrajectory.y)/(handHeight*3);
    }

    @Override
    public void draw(Canvas canvas) {
        int left = parameters.getLeft() + parameters.getMarginLeft();
        for(int i = 0; i < handWidth; i++){
            int top = parameters.getTop() + parameters.getMarginTop();
            for(int j = 0; j < handHeight; j++){
                if(handMatrix[j][i] == 1){
                    p.setColor(Constants.handDarkColor);
                }else if(handMatrix[j][i] == 2){
                    p.setColor(Constants.handLightColor);
                }else{
                    p.setColor(Color.TRANSPARENT);
                }
                canvas.drawRect(left, top, left + scaleCoef, top + scaleCoef, p);
                top = top + scaleCoef;
            }
            left = left + scaleCoef;
        }
    }

    @Override
    public void render() {
        if(parameters.getTrajectory().size() >= 2){
            if(newTrajectory != null &&
                            ((lastTrajectory.x <= newTrajectory.x && parameters.getLeft() >= newTrajectory.x) ||
                            (lastTrajectory.x >= newTrajectory.x && parameters.getLeft() <= newTrajectory.x)) &&
                            ((lastTrajectory.y <= newTrajectory.y && parameters.getTop() >= newTrajectory.y) ||
                            (lastTrajectory.y >= newTrajectory.y && parameters.getTop() <= newTrajectory.y))){
                lastTrajectory = newTrajectory;
                int newTrajectoryIndex = parameters.getTrajectory().indexOf(lastTrajectory) + 1;
                if(newTrajectoryIndex == parameters.getTrajectory().size()){
                    newTrajectoryIndex = 1;
                    lastTrajectory = parameters.getTrajectory().get(0);
                    parameters.setTop(lastTrajectory.y);
                    parameters.setHeight(lastTrajectory.x);
                }
                newTrajectory = parameters.getTrajectory().get(newTrajectoryIndex);
                xStep = (float)(newTrajectory.x - lastTrajectory.x)/(handWidth*3);
                yStep = (float)(newTrajectory.y - lastTrajectory.y)/(handHeight*3);
                if(xStep < 0 && xStep > -1){
                    xStep = -1;
                }else if(xStep > 0 && xStep < 1){
                    xStep = 1;
                }
                if(yStep < 0 && yStep > -1){
                    yStep = -1;
                }else if(yStep > 0 && yStep < 1){
                    yStep = 1;
                }
                Log.d("staps","lastTrajectory_x:\t" + lastTrajectory.x);
                Log.d("staps","lastTrajectory_y:\t" + lastTrajectory.y);
                Log.d("staps","newTrajectory_x:\t" + newTrajectory.x);
                Log.d("staps","newTrajectory_y:\t" + newTrajectory.y);
                Log.d("staps","step_x:\t" + xStep);
                Log.d("staps","step_y:\t" + yStep);
                Log.d("staps","-------------------------------------------");
            }else{
                parameters.setTop((int) (parameters.getTop() + yStep));
                parameters.setLeft((int) (parameters.getLeft() + xStep));
            }
        }
    }
}
