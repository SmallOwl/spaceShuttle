package com.example.spaceshuttle.Components.UI.Bar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spaceshuttle.Components.UI.Element;

public class stepBar implements Element {

    private barParameters parameters;

    private Bitmap frameBitmap;
    private Paint p;

    private float cellWidth;
    private float cellHeight;
    private float cellValue;

    private int value;
    private int drawValue;

    public stepBar(barParameters parameters){
        this.parameters = parameters;
        value = parameters.getDefaultValue();
        prepareSize();
        createFrame();
    }

    private void prepareSize() {
        cellWidth = (float)(parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight()
                - 2*parameters.getBorder() - parameters.getBorder()*(parameters.getCellCount() - 1))/parameters.getCellCount();
        if(cellWidth%1 != 0){
            cellWidth = (int) cellWidth;
        }
        if(cellWidth%2 != 0){
            cellWidth = cellWidth - 1;
        }
        cellHeight = (float)((parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot()
                - 2*parameters.getBorder())/Math.ceil((float)parameters.getCellCount()/2));
        if(cellHeight%1 != 0){
            cellHeight = (int) cellHeight;
        }
        if(parameters.getBorder()%2 != 0){
            parameters.setBorder(parameters.getBorder() - 1);
        }
        if(parameters.getInnerBorder()%2 != 0){
            parameters.setInnerBorder(parameters.getInnerBorder() - 1);
        }
    }

    private void createFrame() {
        frameBitmap = Bitmap.createBitmap(parameters.getWidth(), parameters.getHeight(), Bitmap.Config.ARGB_8888);
        p = new Paint();
        Canvas canvas = new Canvas(frameBitmap);
        cellValue = (parameters.getMaxValue() + Math.abs(parameters.getMinValue()))/parameters.getCellCount();
        float cellTop = 0;
        for(float i = parameters.getWidth()/2;
            i < parameters.getWidth() - parameters.getMarginRight() - parameters.getBorder();
            i = i + cellWidth + parameters.getBorder()){
            p.setStyle(Paint.Style.STROKE);
            p.setColor(parameters.getBorderColor());
            p.setStrokeWidth(parameters.getBorder());
            canvas.drawRect(i - cellWidth/2 - parameters.getBorder()/2,
                    parameters.getMarginTop() + parameters.getBorder()/2 + cellTop,
                    i + cellWidth/2 + parameters.getBorder()/2,
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder()/2, p);
            canvas.drawRect(parameters.getWidth() - i - cellWidth/2 - parameters.getBorder()/2,
                    parameters.getMarginTop() + parameters.getBorder()/2 + cellTop,
                    parameters.getWidth() - i + cellWidth/2 + parameters.getBorder()/2,
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder()/2, p);
            p.setStrokeWidth(parameters.getInnerBorder());
            p.setColor(parameters.getInnerBorderColor());
            canvas.drawRect(i - cellWidth/2 + parameters.getInnerBorder()/2,
                    parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder()/2 + cellTop,
                    i + cellWidth/2 - parameters.getInnerBorder()/2,
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder()/2, p);
            canvas.drawRect(parameters.getWidth() - i - cellWidth/2 + parameters.getInnerBorder()/2,
                    parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder()/2 + cellTop,
                    parameters.getWidth() - i + cellWidth/2 - parameters.getInnerBorder()/2,
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder()/2, p);
            p.setStyle(Paint.Style.FILL);
            p.setColor(parameters.getInnerColor());
            canvas.drawRect(parameters.getWidth() - i - cellWidth/2 + parameters.getInnerBorder(),
                    parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder() + cellTop,
                    parameters.getWidth() - i + cellWidth/2 - parameters.getInnerBorder(),
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
            canvas.drawRect(i - cellWidth/2 + parameters.getInnerBorder(),
                    parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder() + cellTop,
                    i + cellWidth/2 - parameters.getInnerBorder(),
                    parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
            cellTop = cellTop + cellHeight;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(frameBitmap,parameters.getLeft(), parameters.getTop(),null);
        p.setColor(parameters.getCellColor());
        p.setStyle(Paint.Style.FILL);
        int startX = parameters.getLeft() + parameters.getWidth()/2;
        float interval = (cellWidth + parameters.getBorder());
        float cellTop = 0;
        int endCell = 0;
        canvas.drawRect(startX - cellWidth/2 + parameters.getInnerBorder(),
                parameters.getTop() + parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder() + cellTop,
                startX + cellWidth/2 - parameters.getInnerBorder(),
                parameters.getTop() + parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
        if(drawValue >= 0){
            endCell = (int) Math.ceil((drawValue - 10) / cellValue);
            for(int i = 0; i <= endCell; i++){
                canvas.drawRect(startX + interval * i - cellWidth/2 + parameters.getInnerBorder(),
                        parameters.getTop() + parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder() + cellTop,
                        startX + interval * i + cellWidth/2 - parameters.getInnerBorder(),
                        parameters.getTop() + parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
                cellTop = cellTop + cellHeight;
            }
        }else if(drawValue < 0){
            endCell = (int) Math.floor((drawValue + 10)/ cellValue);
            for(int i = 0; i >= endCell; i--){
                canvas.drawRect( startX + interval * i - cellWidth/2 + parameters.getInnerBorder(),
                        parameters.getTop() + parameters.getMarginTop() + parameters.getBorder() + parameters.getInnerBorder() + cellTop,
                        startX + interval * i + cellWidth/2 - parameters.getInnerBorder(),
                        parameters.getTop() + parameters.getHeight() - parameters.getMarginBot() - parameters.getBorder() - parameters.getInnerBorder(), p);
                cellTop = cellTop + cellHeight;
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
