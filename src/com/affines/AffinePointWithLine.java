package com.affines;

import com.figures.Line.Line;
import com.utils.RealPoint;

public class AffinePointWithLine implements IAffine {
    private int k;
    private Line basedLine;

    public AffinePointWithLine(Line basedLine, int k) {
        this.k = k;
        this.basedLine = basedLine;
    }

    @Override
    public RealPoint transform(RealPoint point, int koef) {
        RealPoint lp1 = basedLine.getP1();
        RealPoint lp2 = basedLine.getP2();

        // y = k*x + b
        // - прямая, на которой лежат lp1 и lp2
        double k = (lp2.getY() - lp1.getY()) / (lp2.getX() - lp1.getX());
        double b = lp1.getY() - k * lp1.getX();

        // y = newK*x + newB
        // - прямая, на которой лежит point, перпендикулярная первой
        double newK = - 1/k;
        double newB = point.getY() - newK * point.getX();

        // координаты точки пересечения этих прямых
        double xMeet = (k - newK)/(newB - b);
        double yMeet = newK * xMeet + newB;

        // разницы между координатами точки пересечения и координатами точки,
        // для которой необходимо найти новые координаты
        double detX = point.getX() - xMeet;
        double detY = point.getY() - yMeet;

        //новые координаты точки
        RealPoint newPoint = new RealPoint(point.getX() + koef * detX, point.getY() + koef * detY);
        return newPoint;
    }

    @Override
    public RealPoint transform(RealPoint point) {
        return transform(point, k);
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public Line getBasedLine() {
        return basedLine;
    }

    public void setBasedLine(Line basedLine) {
        this.basedLine = basedLine;
    }
}
