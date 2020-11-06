package com.example.spaceshuttle.Components.UI.Hand;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class handParameters {

    private int height;
    private int width;
    private int top;
    private int left;
    private int marginTop;
    private int marginBot;
    private int marginRight;
    private int marginLeft;
    private List<Point> trajectory;


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

    public List<Point> getTrajectory() {
        return trajectory;
    }

    public void addTrajectory(Point trajectoryPoint) {
        if(trajectory == null){
            trajectory = new ArrayList<Point>();
        }
        trajectory.add(trajectoryPoint);
    }
}
