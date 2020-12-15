package com.utils;

public class ScreenConverter {
    private double x, y, w, h;
    private int screenW, screenH;

    public ScreenConverter(double x, double y, double w, double h, int screenW, int screenH) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.screenW = screenW;
        this.screenH = screenH;
    }

    public ScreenPoint realToScreen (RealPoint p) {
        int px = (int) ((p.getX() - x) * screenW / w);
        int py = (int) ((p.getY() - y) * screenH / h);
        return new ScreenPoint(px, py);
    }

    public RealPoint screenToReal (ScreenPoint s) {
        int px = (int) (s.getX() * w / screenW + x);
        int py = (int) (s.getY() * h / screenH + y);
        return new RealPoint(px, py);
    }

    public int s2rWidth(int width) {
        return (int) (width * w / screenW);
    }

    public int s2rHeight(int height) {
        return (int) (height * h / screenH);
    }

    public int r2sWidth(int width) {
        return (int) (width * screenW / w);
    }

    public int r2sHeight(int height) {
        return (int) (height * screenH / h);
    }

    public int r2sX(double X) {
        return (int) ((X - x) * screenW / w);
    }

    public int r2sY(double Y) {
        return (int) ((Y - y) * screenH / h);
    }

    public double s2rX(int X) {
        return X * w / screenW + x;
    }

    public double s2rY(int Y) {
        return Y * h / screenH + y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public int getScreenW() {
        return screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setScreenW(int screenW) {
        this.screenW = screenW;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }

}
