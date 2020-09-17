package com.example.spaceshuttle.Components.UI.Joystick;

public class joystickParameters {

    private int joystickInnerColor;
    private int joystickColor;

    private int defaultValueAngle;
    private int defaultValue;
    private int minValue;
    private int maxValue;

    private float joystickCoef;

    private float minJoystickR;
    private float maxJoystickR;


    public int getJoystickColor() {
        return joystickColor;
    }

    public void setJoystickColor(int joystickColor) {
        this.joystickColor = joystickColor;
    }


    public float getJoystickCoef() {
        return joystickCoef;
    }

    public void setJoystickCoef(float joystickCoef) {
        this.joystickCoef = joystickCoef;
    }

    public float getMinJoystickR() {
        return minJoystickR;
    }

    public void setMinJoystickR(float minJoystickR) {
        this.minJoystickR = minJoystickR;
    }

    public float getMaxJoystickR() {
        return maxJoystickR;
    }

    public void setMaxJoystickR(float maxJoystickR) {
        this.maxJoystickR = maxJoystickR;
    }

    public int getDefaultValueAngle() {
        return defaultValueAngle;
    }

    public void setDefaultValueAngle(int defaultValueAngle) {
        this.defaultValueAngle = defaultValueAngle;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getJoystickInnerColor() {
        return joystickInnerColor;
    }

    public void setJoystickInnerColor(int joystickInnerColor) {
        this.joystickInnerColor = joystickInnerColor;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
