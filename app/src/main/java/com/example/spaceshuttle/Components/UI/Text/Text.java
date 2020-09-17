package com.example.spaceshuttle.Components.UI.Text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.example.spaceshuttle.Components.UI.Element;
import com.example.spaceshuttle.Constants;

public class Text implements Element {

    private textParameters parameters;

    private Rect drawRect;
    private int startX;
    private int startY;

    private Paint p;

    private String value;
    private String drawValue;
    private String drawText;

    public Text(textParameters parameters){
        this.parameters = parameters;
        this.value = parameters.getDefaultValue();
        this.drawValue = value;
        this.drawText = parameters.getDefaultText();
        prepareText();
    }

    private void prepareText() {
        p = new Paint();
        p.setColor(parameters.getTextColor());
        p.setTypeface(Typeface.MONOSPACE);
        p.setTextSize(parameters.getTextSize());
        p.setTypeface(Constants.fontUse);
        drawRect = new Rect();
        centerElement();
    }

    @Override
    public void draw(Canvas canvas) {
//        p.setColor(Color.RED);
//        canvas.drawRect(parameters.getLeft(), parameters.getTop(), parameters.getLeft() + parameters.getWidth(), parameters.getTop() + parameters.getHeight(), p);
//        p.setColor(parameters.getTextColor());
//        p.setColor(Color.YELLOW);
//        canvas.drawRect(parameters.getLeft(),parameters.getTop(),parameters.getLeft() + parameters.getMarginLeft(), parameters.getTop() + parameters.getHeight(), p);
//        canvas.drawRect(parameters.getLeft() + parameters.getWidth() - parameters.getMarginRight(),parameters.getTop(),parameters.getLeft() + parameters.getWidth(), parameters.getTop() + parameters.getHeight(), p);
//        canvas.drawRect(parameters.getLeft(),parameters.getTop(),parameters.getLeft() + parameters.getWidth(), parameters.getTop() + parameters.getMarginTop(), p);
//        canvas.drawRect(parameters.getLeft(),parameters.getTop() + parameters.getHeight() - parameters.getMarginBot(),parameters.getLeft() + parameters.getWidth(), parameters.getTop() + parameters.getHeight(), p);
//        p.setColor(Color.GREEN);
//        canvas.drawLine(parameters.getLeft() + parameters.getMarginLeft() + (parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight())/2,
//                parameters.getTop() + parameters.getMarginTop(),
//                parameters.getLeft() + parameters.getMarginLeft() + (parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight())/2,
//                parameters.getTop() + parameters.getHeight() - parameters.getMarginBot(),p);
//        canvas.drawLine(parameters.getLeft() + parameters.getMarginLeft(),
//                parameters.getTop() + parameters.getMarginTop() + (parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot())/2,
//                parameters.getLeft() + parameters.getWidth() - parameters.getMarginRight(),
//                parameters.getTop() + parameters.getMarginTop() + (parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot())/2,p);

        p.setColor(parameters.getTextColor());
        canvas.drawText(drawText + drawValue, startX, startY, p);
    }

    @Override
    public void render() {
        drawValue = value;
    }

    public void setValue(int value){
        if(value < 10){
            this.value = "00000" + value;
        }else if(value < 100){
            this.value = "0000" + value;
        }else if(value < 1000){
            this.value = "000" + value;
        }else if(value < 10000){
            this.value = "00" + value;
        }else if(value < 100000){
            this.value = "0" + value;
        }else{
            this.value = String.valueOf(value);
        }
    }

    public int getValue(){
        return Integer.parseInt(value);
    }

    public String getDrawText() {
        return drawText;
    }

    public void setDrawText(String drawText) {
        this.drawText = drawText;
    }

    public void centerElement(){
        p.getTextBounds(drawText + drawValue, 0, drawText.length() + drawValue.length(), drawRect);
        startX = parameters.getLeft() + parameters.getMarginLeft() + (parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight())/2 - drawRect.width()/2;
        startY = parameters.getTop() + parameters.getMarginTop() + (parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot())/2 + drawRect.height()/2;
    }

    public textParameters getParameters() { return parameters; }
}
