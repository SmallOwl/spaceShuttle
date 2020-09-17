package com.example.spaceshuttle.Components.UI.Bar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.spaceshuttle.Components.UI.Element;

public class Bar implements Element {

    private barParameters parameters;

    private Bitmap frameBitmap;
    private Paint p;

    private float cellWidth;
    private float cellValue;

    private int value;
    private int drawValue;

    public Bar(barParameters parameters){
        this.parameters = parameters;
        value = parameters.getDefaultValue();
        createFrame();
    }

    private void createFrame() {
       cellWidth = (float)(parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight()
                - 2*parameters.getBorder() - parameters.getCellBorder()*(parameters.getCellCount() - 1))/parameters.getCellCount();
        if(cellWidth%1 != 0){
            parameters.setWidth((int) (parameters.getWidth() - cellWidth%1*parameters.getCellCount()));
            cellWidth = (int) cellWidth;
        }
        frameBitmap = Bitmap.createBitmap(parameters.getWidth(), parameters.getHeight(), Bitmap.Config.ARGB_8888);
        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(parameters.getBorderColor());
        p.setStrokeWidth(parameters.getBorder());
        Canvas canvas = new Canvas(frameBitmap);
        canvas.drawRect(parameters.getMarginLeft() + parameters.getBorder()/2,
                parameters.getMarginTop() + parameters.getBorder()/2,
                parameters.getWidth() - parameters.getMarginRight() - parameters.getBorder()/2,
                parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder()/2, p);
        cellValue = (parameters.getMaxValue() - parameters.getMinValue())/parameters.getCellCount();
        for(float i = parameters.getMarginLeft() + parameters.getBorder();
            i < parameters.getWidth() - parameters.getMarginRight() - parameters.getBorder();
            i = i + cellWidth + parameters.getCellBorder()){
            p.setStrokeWidth(parameters.getInnerBorder());
            p.setColor(parameters.getInnerBorderColor());
            canvas.drawRect(i + parameters.getInnerBorder()/2,
                    parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder()/2,
                    i + cellWidth - parameters.getInnerBorder()/2,
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder()/2, p);
            p.setStrokeWidth(parameters.getCellBorder());
            p.setColor(parameters.getBorderColor());
            canvas.drawLine(i + cellWidth + parameters.getCellBorder()/2,
                    parameters.getMarginTop() + parameters.getBorder(),
                    i + cellWidth + + parameters.getCellBorder()/2,
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() + 1, p);
            p.setStyle(Paint.Style.FILL);
            p.setColor(parameters.getInnerColor());
            canvas.drawRect(i + parameters.getInnerBorder(),
                    parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder(),
                    i + cellWidth - parameters.getInnerBorder(),
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(frameBitmap,parameters.getLeft(), parameters.getTop(),null);
        p.setColor(parameters.getCellColor());
        p.setStyle(Paint.Style.FILL);
        int endCell = (int) Math.ceil(drawValue / cellValue);
        int startX = parameters.getLeft() + parameters.getMarginLeft() + parameters.getBorder();
        float interval = (cellWidth + parameters.getCellBorder());
        if(drawValue != 0){
            for(int i = 0; i < endCell; i++){
                canvas.drawRect(startX + parameters.getInnerBorder() + interval * i,
                        parameters.getTop() + parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder(),
                        startX + interval * i + cellWidth - parameters.getInnerBorder(),
                        parameters.getTop() + parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
            }
        }
    }

    @Override
    public void render() {
        drawValue = value;
    }

    public void setValue(int value){
        if(value > parameters.getMaxValue()){
            this.value = parameters.getMaxValue();
        }else if(value < parameters.getMinValue()){
            this.value = parameters.getMinValue();
        }else{
            this.value = value;
        }
    }

    public int getValue(){
        return value;
    }

    public barParameters getParameters(){
        return parameters;
    }
}
