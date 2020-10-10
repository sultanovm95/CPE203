package com.company;

import java.awt.*;

public class Triangle implements Shape {

    Point pointA;
    Point pointB;
    Point pointC;
    Color color;

    public Triangle(Point pntA, Point pntB, Point pntC, Color clr) {
        this.pointA = pntA;
        this.pointB = pntB;
        this.pointC = pntC;
        this.color = clr;
    }

    public Point getVertexA() {
        return pointA;
    }

    public Point getVertexB() {
        return pointB;
    }

    public Point getVertexC() {
        return pointC;
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
        double width = Math.abs(pointA.x - pointC.x);
        double height = Math.abs(pointA.y - pointB.y);
        double areaTring = (width * height)/2;

        return areaTring;
    }

    @Override
    public double getPerimeter() {
        double sideAC = Math.sqrt(Math.pow(pointA.x - pointC.x, 2) + Math.pow(pointA.y - pointC.y,2));
        double sideBC = Math.sqrt(Math.pow(pointB.x - pointC.x, 2) + Math.pow(pointB.y - pointC.y,2));
        double sideAB = Math.sqrt(Math.pow(pointA.x - pointC.x, 2) + Math.pow(pointA.y - pointC.y,2));
        double totalPerim = sideAB + sideBC + sideAC;
        return totalPerim;
    }

    @Override
    public void translate(Point pnt) {

    }


}
