package com;

import com.affines.AffinePointWithDot;
import com.affines.AffinePointWithLine;
import com.affines.IAffine;
import com.figures.Line.*;
import com.figures.Rhombus.LRhombusDrawer;
import com.figures.Rhombus.Rhombus;
import com.figures.Rhombus.RhombusDrawer;
import com.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel implements
        MouseListener,
        MouseMotionListener,
        MouseWheelListener {
    private DrawPanel dp;

    private ScreenConverter converter = new ScreenConverter(
            0, 0, 800, 600, 800, 600);

    private Line xAxis = new Line(1, 1, 1, 0);
    private Line yAxis = new Line(1, 1, 0, -1);

    private ScreenPoint prevDrag = null;



    private ArrayList<Line> lines = new ArrayList<>();
    private Line currentLine = null;

    private List<Rhombus> rhombuses = new ArrayList<>();
    private Rhombus currentRhombus = null;
    private boolean isDrawRhombus = false;

    //private IAffine currentAffine = new AffinePointWithDot(new RealPoint(100, 100), 2);
    private IAffine currentAffine = new AffinePointWithLine(new Line(new RealPoint(100, 100), new RealPoint(500, 500)), 2);
    private boolean isAffineAll = false;


    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener( this);

    }

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
        LRhombusDrawer rd = new LRhombusDrawer(ld);
        /**/
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);

        if (lines.size() != 0) {
            for(Line l: lines) {
                ld.drawLine(converter.realToScreen(l.getP1()), converter.realToScreen(l.getP2()));
            }
        }
        if (currentLine != null) {
            drawLine(ld, currentLine);
        }

        if (rhombuses.size() != 0) {
            for (Rhombus r: rhombuses) {
                if (isAffineAll) {
                    transformRhombusWithDot(r, currentAffine, ld);
                } else {
                    rd.drawRhombus(
                            converter.realToScreen(r.getPoint()),
                            converter.realToScreenWidth(r.getWidth()),
                            converter.realToScreenHeight(r.getHeight())
                    );
                }
            }
        }
        if (currentRhombus != null) {
            drawRhombus(rd, currentRhombus);
        }
        if (currentAffine.getClass().equals(AffinePointWithDot.class)) {
            drawPoint(pd, converter.realToScreen(((AffinePointWithDot) currentAffine).getBasedPoint()));
        } else if (currentAffine.getClass().equals(AffinePointWithLine.class)) {
            drawLine(ld, ((AffinePointWithLine) currentAffine).getBasedLine());
        }

        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(converter.realToScreen(l.getP1()), converter.realToScreen(l.getP2()));

    }

    private void drawRhombus(RhombusDrawer rd, Rhombus rhomb) {        //рисуем прямоугольник
        rd.drawRhombus(
                converter.realToScreen(rhomb.getPoint()),
                converter.realToScreenWidth(rhomb.getWidth()),
                converter.realToScreenHeight(rhomb.getHeight())
        );
    }

    private void updateDateRhombus(Rhombus rhombus, ScreenPoint point) {
        RealPoint realPoint = converter.screenToReal(point);
        double width = realPoint.getX() - rhombus.getPoint().getX();
        double height = realPoint.getY() - rhombus.getPoint().getY();

        rhombus.setWidth(width);
        rhombus.setHeight(height);
    }

    private void drawPoint(PixelDrawer pd, ScreenPoint point) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                pd.setPixel(point.getX() + i, point.getY() + j, new Color(0, 0, 0));
            }
        }
        pd.setPixel(point.getX(), point.getY(), new Color(0, 255, 0));
    }

    private void transformRhombusWithDot(Rhombus rhombus, IAffine affine, LineDrawer ld) {
        RealPoint rP = rhombus.getPoint();                          //левая верхняя точка ромба
        double rW = rhombus.getWidth();                             //ширина ромба
        double rH = rhombus.getHeight();                            //высота ромба

        RealPoint pointUp = new RealPoint(rP.getX() + rW/2, rP.getY());             // верхняя точка ромба
        RealPoint pointRight = new RealPoint(rP.getX() + rW, rP.getY() + rH/2);  // правая точка ромба
        RealPoint pointDown = new RealPoint(rP.getX() + rW/2, rP.getY() + rH);   // нижняя точка ромба
        RealPoint pointLeft = new RealPoint(rP.getX(), rP.getY() + rH/2);             // левая точка ромба

        pointUp = affine.transform(pointUp);                                // изменённая верхняя точка
        pointRight = affine.transform(pointRight);                          // изменённая правая точка
        pointDown = affine.transform(pointDown);                            // изменённая нижняя точка
        pointLeft = affine.transform(pointLeft);                            // изменённая левая точка

        Line LU = new Line(pointLeft, pointUp);     // LU - ЛинияЛВ(лево-верх)
        drawLine(ld, LU);
4        Line UR = new Line(pointUp, pointRight);    // UR - верх-право
        drawLine(ld, UR);
        Line RD = new Line(pointRight, pointDown);  // RD - право-низ
        drawLine(ld, RD);
        Line DL = new Line(pointDown, pointLeft);   // DL - низ-лево
        drawLine(ld, DL);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (isDrawRhombus) {
                isDrawRhombus = false;
            } else {
                isDrawRhombus = true;
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (isAffineAll) {
                isAffineAll = false;
            } else {
                isAffineAll = true;
            }
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = current;

        }
        else if (e.getButton() == MouseEvent.BUTTON1) {
            if (isDrawRhombus) {
                currentRhombus = new Rhombus(converter.screenToReal(new ScreenPoint(e.getX(), e.getY())), 0, 0);
            } else {
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
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            if (isDrawRhombus) {
                updateDateRhombus(currentRhombus, current);
                rhombuses.add(currentRhombus);
                currentRhombus = null;
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
}
