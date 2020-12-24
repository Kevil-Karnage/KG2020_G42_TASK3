package com.figures.Rectangle;

import com.figures.IFigure;
import com.utils.RealPoint;

public class Rectangle implements IFigure {
    private RealPoint point;
    private double width;
    private double height;

    public Rectangle(RealPoint point, double width, double height) {
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
