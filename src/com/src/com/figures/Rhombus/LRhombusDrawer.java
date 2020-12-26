package com.figures.Rhombus;

import com.figures.Line.LineDrawer;
import com.utils.ScreenPoint;

public class LRhombusDrawer implements RhombusDrawer {
    private LineDrawer ld;

    public LRhombusDrawer(LineDrawer ld) {
        this.ld = ld;
    }

    @Override
    public void drawRhombus(ScreenPoint point, int width, int height) {
        int x = point.getX();
        int y = point.getY();

        ScreenPoint pointUp = new ScreenPoint(x + width / 2, y);
        ScreenPoint pointRight = new ScreenPoint(x + width, y + height / 2);
        ScreenPoint pointDown = new ScreenPoint(x + width / 2, y + height);
        ScreenPoint pointLeft = new ScreenPoint(x, y + height / 2);

        ld.drawLine(pointUp, pointRight);
        ld.drawLine(pointRight, pointDown);
        ld.drawLine(pointDown, pointLeft);
        ld.drawLine(pointLeft, pointUp);
    }
}
