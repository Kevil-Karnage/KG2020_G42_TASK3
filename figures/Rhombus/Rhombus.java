package com.figures.Rhombus;

import com.figures.IFigure;
import com.utils.RealPoint;

import java.awt.*;

public class Rhombus implements IFigure {
    private RealPoint point;
    private double width;
    private double height;

    public Rhombus(RealPoint point, double width, double height) {
        this.point = point;
        this.width = width;
        this.height = height;
    }

    @Override
    public RealPoint getPoint() {
        return point;
    }

    @Override
    public void setPoint(RealPoint point) {
        this.point = point;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }
}