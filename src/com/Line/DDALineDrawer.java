package com.Line;

import com.utils.PixelDrawer;
import com.utils.ScreenPoint;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pd;
    public DDALineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (x1 > x2) {
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }
            double k = dy / dx;
            for (int j = x1; j <= x2; j++) {
                double i = k * (j - x1) + y1;
                pd.setPixel(j, (int) i, Color.RED);
            }
        } else {
            if (y1 > y2) {
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }
            double kObr = dx / dy;
            for (int j = y1; j <= y2; j++) {
                double i = kObr * (j - y1) + x1;
                pd.setPixel((int) i, j, Color.BLUE);
            }
        }
    }

    @Override
    public void drawLine(ScreenPoint sp1, ScreenPoint sp2) {
        drawLine(sp1.getX(), sp1.getY(), sp2.getX(), sp2.getY());
    }
}