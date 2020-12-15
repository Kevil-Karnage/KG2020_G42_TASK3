package com.Rectangle;

import com.Line.LineDrawer;
import com.utils.ScreenPoint;

public class DDARectangleDrawer implements RectangleDrawer {
    private LineDrawer ld;

    public DDARectangleDrawer(LineDrawer ld) {
        this.ld = ld;
    }

    @Override
    public void drawRectangle(ScreenPoint point, int width, int height) {
        int x1 = point.getX();
        int x2 = point.getX() + width;
        int y1 = point.getY();
        int y2 = point.getY() + height;

        ScreenPoint point2 = new ScreenPoint(x2, y1);
        ScreenPoint point3 = new ScreenPoint(x2, y2);
        ScreenPoint point4 = new ScreenPoint(x1, y2);

        ld.drawLine(point, point2);
        ld.drawLine(point2, point3);
        ld.drawLine(point3, point4);
        ld.drawLine(point4, point);
    }


}
