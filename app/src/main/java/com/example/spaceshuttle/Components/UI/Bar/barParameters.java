package com.example.spaceshuttle.Components.UI.Bar;

public class barParameters {

    private int height;
    private int width;
    private int top;
    private int left;
    private int marginTop;
    private int marginBot;
    private int marginRight;
    private int marginLeft;
    private int border;
    private int innerBorder;
    private int cellBorder;

    private int borderColor;
    private int innerColor;
    private int innerBorderColor;
    private int cellColor;

    private int cellCount;
    private int minValue;
    private int maxValue;
    private int defaultValue;

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

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getInnerBorder() {
        return innerBorder;
    }

    public void setInnerBorder(int innerBorder) {
        this.innerBorder = innerBorder;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getInnerColor() {
        return innerColor;
    }

    public void setInnerColor(int innerColor) {
        this.innerColor = innerColor;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void setCellCount(int cellCount) {
        this.cellCount = cellCount;
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

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
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

    public int getCellBorder() {
        return cellBorder;
    }

    public void setCellBorder(int cellBorder) {
        this.cellBorder = cellBorder;
    }

    public int getInnerBorderColor() {
        return innerBorderColor;
    }

    public void setInnerBorderColor(int innerBorderColor) {
        this.innerBorderColor = innerBorderColor;
    }

    public int getCellColor() {
        return cellColor;
    }

    public void setCellColor(int cellColor) {
        this.cellColor = cellColor;
    }
}
