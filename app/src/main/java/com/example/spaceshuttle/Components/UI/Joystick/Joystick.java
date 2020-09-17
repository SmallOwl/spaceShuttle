package com.example.spaceshuttle.Components.UI.Joystick;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.spaceshuttle.Components.UI.Element;

public class Joystick implements Element{

    private joystickParameters parameters;

    private Paint p;

    private float centerX;
    private float centerY;
    private float posX;
    private float posY;
    private float drawCenterX;
    private float drawCenterY;
    private float drawPosX;
    private float drawPosY;

    private int valueAngle;
    private int value;

    private boolean visible;

    public Joystick(joystickParameters parameters){
        this.parameters = parameters;
        valueAngle = parameters.getDefaultValueAngle();
        value = parameters.getDefaultValue();
        visible = false;
        p = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        if(visible){
            p.setColor(parameters.getJoystickInnerColor());
            canvas.drawCircle(drawCenterX,drawCenterY,parameters.getMaxJoystickR(),p);
            p.setColor(parameters.getJoystickColor());
            canvas.drawCircle(drawPosX,drawPosY,parameters.getMinJoystickR(),p);
        }
    }

    @Override
    public void render() {
        if(visible){
            drawCenterX = centerX;
            drawCenterY = centerY;
            drawPosX = posX;
            drawPosY = posY;
        }
    }

    public void touch(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            centerX = event.getX();
            centerY = event.getY();
            posX = centerX;
            posY = centerY;
            visible = true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            posX = event.getX();
            posY = event.getY();
            if(posX > centerX + parameters.getMaxJoystickR() || posX < centerX - parameters.getMaxJoystickR() ||
                    posY > centerY + parameters.getMaxJoystickR() || posY < centerY - parameters.getMaxJoystickR()){
                double r = Math.sqrt((posX - centerX)*(posX - centerX) + (posY - centerY)*(posY - centerY));
                double angleSin = Math.sin((posX - centerX)/r);
                double angleCos = Math.cos((posY - centerY)/r);
                posX = centerX + (float) ((posX - centerX)/r*parameters.getMaxJoystickR());
                posY = centerY + (float) ((posY - centerY)/r*parameters.getMaxJoystickR());
            }
            double r = Math.sqrt((posX - centerX)*(posX - centerX) + (posY - centerY)*(posY - centerY));
            valueAngle = (int) Math.toDegrees(Math.asin((posX - centerX)/r));
            value = (int) (r/parameters.getJoystickCoef());
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            valueAngle = parameters.getDefaultValueAngle();
            value = parameters.getDefaultValue();
            visible = false;
        }
    }

    public int getValueAngle() {
        return valueAngle;
    }
    public int getValue() {
        return value;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
