package com;

import com.Line.*;
import com.Rectangle.*;
import com.Rectangle.Rectangle;
import com.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements
        MouseListener,
        MouseMotionListener,
        MouseWheelListener {
    private DrawPanel dp;

    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter converter = new ScreenConverter(
            0, 0, 800, 600, 800, 600);

    private Line xAxis = new Line(1, 1, 1, 0);
    private Line yAxis = new Line(1, 1, 0, -1);

    private ScreenPoint prevDrag;

    private Line currentLine = null;

    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private Rectangle currentRectangle = null;
    private boolean[] isMoveRectangle = new boolean[5]; //up, down, left, right, all
    private boolean isDrawRectangle = false;
    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener( this);

        for (int i = 0; i < isMoveRectangle.length; i++) {
            isMoveRectangle[i] = false;
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param g
     * @since 1.7
     */
    @Override
    public void paint(Graphics g) {
        converter.setScreenW(getWidth());
        converter.setScreenH(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.createGraphics();
        gr.setColor(Color.white);
        gr.fillRect(0,0,getWidth(), getHeight());
        gr.dispose();
        g.drawImage(bi, 0,0, null);

        PixelDrawer pd = new BufferedImagePixelDrawer(bi);

        LineDrawer ld = new DDALineDrawer(pd);
        RectangleDrawer rd = new DDARectangleDrawer(ld);
        /**/
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);


        if (lines.size() != 0) {

        }
        for(Line l: lines) {
            ld.drawLine(converter.realToScreen(l.getP1()), converter.realToScreen(l.getP2()));
        }
        if (currentLine != null) {
            drawLine(ld, currentLine);
        }

        if (rectangles.size() != 0) {
            for (Rectangle r: rectangles) {
                rd.drawRectangle(converter.realToScreen(r.getPoint()), r.getWidth(), r.getHeight());
            }
        }
        if (currentRectangle != null) {
            drawRectangle(rd, currentRectangle);
        }
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(converter.realToScreen(xAxis.getP1()), converter.realToScreen(yAxis.getP1()));

    }

    private void drawRectangle(RectangleDrawer rd, Rectangle rect) {
        rd.drawRectangle(converter.realToScreen(rect.getPoint()), rect.getWidth(), rect.getHeight());
    }

    private void updateRectangleDates(Rectangle rect, RealPoint point) {
        RealPoint realPoint = rect.getPoint();
        RealPoint realPoint2 = point;
        int width = converter.r2sWidth((int) (realPoint2.getX() - realPoint.getX()));
        int height = converter.r2sHeight((int) (realPoint2.getY() - realPoint.getY()));

        rect.setPoint(realPoint);
        rect.setWidth(width);
        rect.setHeight(height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = current;
            if (isDrawRectangle) {
                isDrawRectangle = false;
                isMoveRectangle[4] = false;
                rectangles.add(currentRectangle);
                currentRectangle = null;
            } else {
                isDrawRectangle = true;
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON1) {
            if (isDrawRectangle) {
                if (isMoveRectangle[4]) {
                    setIsMoveRectangle(current);
                } else {
                    currentRectangle = new Rectangle(
                            converter.screenToReal(new ScreenPoint(e.getX(), e.getY())), 0, 0);
                }
            }
            else {
                currentLine = new Line(
                        converter.screenToReal(new ScreenPoint(e.getX(), e.getY())),
                        converter.screenToReal(new ScreenPoint(e.getX(), e.getY()))
                );
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            prevDrag = new ScreenPoint(e.getX(), e.getY());
            if (isDrawRectangle) {
                if (isMoveRectangle[4]) {
                    if (isMoveRectangle[0]) {
                        moveUp(e.getY());
                        isMoveRectangle[0] = false;
                    } else if (isMoveRectangle[1]) {
                        moveDown(e.getY());
                        isMoveRectangle[1] = false;
                    } else if (isMoveRectangle[2]) {
                        moveLeft(e.getX());
                        isMoveRectangle[2] = false;
                    } else if (isMoveRectangle[3]) {
                        moveRight(e.getX());
                        isMoveRectangle[3] = false;
                    } else {
                        rectangles.add(currentRectangle);
                        currentRectangle = null;
                        isMoveRectangle[4] = false;
                    }
                } else {
                    updateRectangleDates(currentRectangle, converter.screenToReal(new ScreenPoint(e.getX(), e.getY())));
                    isMoveRectangle[4] = true;
                }
            } else {
                lines.add(currentLine);
                currentLine = null;
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(
                e.getX(), e.getY());
        if (isDrawRectangle) {
            if (isMoveRectangle[4]) {
                /*if (isMoveRectangle[0]) {
                    moveUp(e.getY());
                } else if (isMoveRectangle[1]) {
                    moveDown(e.getY());
                } else if (isMoveRectangle[2]) {
                    moveLeft(e.getX());
                } else if (isMoveRectangle[3]) {
                    moveRight(e.getX());
                }*/
            } else {
                updateRectangleDates(
                        currentRectangle,
                        converter.screenToReal(current)
                );
            }
        } else {
            if (prevDrag != null) {
                ScreenPoint delta = new ScreenPoint(
                        current.getX() - prevDrag.getX(),
                        current.getY() - prevDrag.getY());
                RealPoint deltaReal = converter.screenToReal(delta);
                RealPoint zeroReal = converter.screenToReal(new ScreenPoint(0, 0));
                RealPoint vector = new RealPoint(
                        deltaReal.getX() - zeroReal.getX(),
                        deltaReal.getY() - zeroReal.getY());
                converter.setX(converter.getX() - vector.getX());
                converter.setY(converter.getY() - vector.getY());
                prevDrag = current;
            }
            if (currentLine != null) {
                currentLine.setP2(converter.screenToReal(current));
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved (MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks > 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        converter.setW(converter.getW() * scale);
        converter.setH(converter.getH() * scale);
        repaint();
    }

    private void setIsMoveRectangle(ScreenPoint pointMouse) {
        RealPoint pMouse = converter.screenToReal(pointMouse);
        RealPoint pRect = currentRectangle.getPoint();
        int wRect = converter.s2rWidth(currentRectangle.getWidth());
        int hRect = converter.s2rHeight(currentRectangle.getHeight());
        if (pRect.getX() - 5 < pMouse.getX() && pMouse.getX() < pRect.getX() + wRect + 5) {
            if (Math.abs(pMouse.getY() - pRect.getY()) <= 5) {
                isMoveRectangle[4] = true;
                isMoveRectangle[0] = true;
            } else if (Math.abs(pMouse.getY() - (pRect.getY() + hRect)) <= 5) {
                isMoveRectangle[4] = true;
                isMoveRectangle[1] = true;
            } else {
                setAllMoveRectangleFalse();
            }
        } else if (pRect.getY() < pMouse.getY() && pMouse.getY() < pRect.getY()) {
            if (Math.abs(pMouse.getX() - pRect.getX()) <= 5) {
                isMoveRectangle[4] = true;
                isMoveRectangle[2] = true;
            } else if (Math.abs(pMouse.getX() - (pRect.getX()) + wRect) <= 5) {
                isMoveRectangle[4] = true;
                isMoveRectangle[3] = true;
            } else {
                setAllMoveRectangleFalse();
            }
        } else {
            setAllMoveRectangleFalse();
        }
    }

    private void setAllMoveRectangleFalse() {
        for (boolean b: isMoveRectangle) {
            b = false;
        }
    }

    private void moveUp(int y) {
        RealPoint point1 = currentRectangle.getPoint();
        int width = converter.s2rWidth(currentRectangle.getWidth());
        int height = converter.s2rHeight(currentRectangle.getHeight());
        RealPoint point2 = new RealPoint(point1.getX() + width, point1.getY() + height);

        int newY = (int) converter.s2rY(y);

        point1.setY(newY);
        currentRectangle.setPoint(point1);
        updateRectangleDates(currentRectangle, point2);
    }

    private void moveDown(int y) {
        RealPoint point1 = currentRectangle.getPoint();
        int width = converter.s2rWidth(currentRectangle.getWidth());
        int height = converter.s2rHeight(currentRectangle.getHeight());
        RealPoint point2 = new RealPoint(point1.getX() + width, y + height);

        updateRectangleDates(currentRectangle, point2);
    }

    private void moveLeft(int x) {
        RealPoint point1 = currentRectangle.getPoint();
        int width = converter.s2rWidth(currentRectangle.getWidth());
        int height = converter.s2rHeight(currentRectangle.getHeight());
        RealPoint point2 = new RealPoint(point1.getX() + width, point1.getY() + height);

        int newX = (int) converter.s2rY(x);

        point1.setX(newX);
        currentRectangle.setPoint(point1);
        updateRectangleDates(currentRectangle, point2);
    }

    private void moveRight(int x) {
        RealPoint point1 = currentRectangle.getPoint();
        int width = converter.s2rWidth(currentRectangle.getWidth());
        int height = converter.s2rHeight(currentRectangle.getHeight());
        RealPoint point2 = new RealPoint(x + width, point1.getY() + height);

        updateRectangleDates(currentRectangle, point2);
    }
}
