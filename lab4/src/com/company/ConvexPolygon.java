package com.company;

import java.awt.*;

public class ConvexPolygon implements Shape {

    Point[] points;
    Color color;

    public ConvexPolygon(Point[] points, Color clr) {
        this.points = points;
        this.color = clr;
    }

    public Point getVertex(int index) {
        return points[index];
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color clr) {
        this.color = clr;
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }

    @Override
    public void translate(Point pnt) {

        for(int i = 0; i < points.length; i++) {
            getVertex(i).x = pnt.x;
            getVertex(i).y = pnt.y;
        }

    }

}
