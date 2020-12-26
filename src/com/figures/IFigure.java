package com.figures;

import com.utils.RealPoint;

import java.awt.*;

public interface IFigure {
    RealPoint getPoint();
    void setPoint(RealPoint point);
    double getWidth();
    void setWidth(double width);
    double getHeight();
    void setHeight(double height);
    /*
    Color getColor();
    void setColor(Color color);
    boolean getIsFill();
    void setIsFill(boolean isFill);
    */
}
