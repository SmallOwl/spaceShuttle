package com.example.spaceshuttle.Components.UI.SoundButton;

public class buttonParameters {

    private int height;
    private int width;
    private int top;
    private int left;
    private int marginTop;
    private int marginBot;
    private int marginRight;
    private int marginLeft;


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

}
