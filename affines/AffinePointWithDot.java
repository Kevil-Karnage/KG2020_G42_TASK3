package com.affines;

import com.utils.RealPoint;

public class AffinePointWithDot implements IAffine {
    private int k;
    private RealPoint basedPoint;

    public AffinePointWithDot(RealPoint basedPoint) {
        this.basedPoint = basedPoint;
    }

    public AffinePointWithDot(RealPoint basedPoint, int k) {
        this.k = k;
        this.basedPoint = basedPoint;
    }

    @Override
    public RealPoint transform(RealPoint point, int k) {
        double detY = point.getY() - basedPoint.getY();
        double detX = point.getX() - basedPoint.getX();

        detY *= k;
        detX *= k;

        point.setX(basedPoint.getX() + detX);
        point.setY(basedPoint.getY() + detY);
        return point;
    }

    @Override
    public RealPoint transform(RealPoint point) {
        return transform(point, k);
    }

    public int getK() {
        return k;
    }

    public RealPoint getBasedPoint() {
        return basedPoint;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setBasedPoint(RealPoint basedPoint) {
        this.basedPoint = basedPoint;
    }
}
