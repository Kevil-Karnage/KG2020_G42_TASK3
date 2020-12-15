package com.Rectangle;

import com.utils.RealPoint;

public class Rectangle{
    private RealPoint point;
    private int width;
    private int height;

    public Rectangle(RealPoint point, int width, int height) {
        this.point = point;
        this.width = width;
        this.height = height;
    }

    public Rectangle(double x, double y, int width, int height) {
        this.point = new RealPoint(x, y);
        this.width = width;
        this.height = height;
    }

    public RealPoint getPoint() {
        return point;
    }

    public void setPoint(RealPoint point) {
        this.point = point;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
