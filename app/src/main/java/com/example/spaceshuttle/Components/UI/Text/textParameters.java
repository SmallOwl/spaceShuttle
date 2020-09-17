package com.example.spaceshuttle.Components.UI.Text;

public class textParameters {

    private int height;
    private int width;
    private int top;
    private int left;
    private int marginTop;
    private int marginBot;
    private int marginRight;
    private int marginLeft;

    private int textColor;
    private float textSize;

    private String defaultText;
    private String defaultValue;
    private int valueLength;

    public int getHeight() {
            return height;
        }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
            return width;
        }

    public void setWidth(int width) {
            this.width = width;
        }

    public int getMargin() {
            return marginTop;
        }

    public int getMarginBot() {
            return marginBot;
        }

    public int getMarginRight() {
            return marginRight;
        }

    public int getMarginLeft() {
            return marginLeft;
        }

    public int getMarginTop() {
            return marginTop;
        }

    public void setMargin(int margin) {
            this.marginTop = margin;
            this.marginBot = margin;
            this.marginLeft = margin;
            this.marginRight = margin;
        }

    public void setMargin(int marginTop, int marginBot, int marginLeft, int marginRight) {
            this.marginTop = marginTop;
            this.marginBot = marginBot;
            this.marginLeft = marginLeft;
            this.marginRight = marginRight;
        }

    public String getDefaultValue() {
            return defaultValue;
        }

    public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

    public int getTop() {
            return top;
        }

    public void setTop(int top) {
            this.top = top;
        }

    public int getLeft() {
            return left;
        }

    public void setLeft(int left) {
            this.left = left;
        }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public int getValueLength() {
        return valueLength;
    }

    public void setValueLength(int valueLength) {
        this.valueLength = valueLength;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }
}
