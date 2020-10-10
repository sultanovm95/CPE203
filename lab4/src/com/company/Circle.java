package com.company;

import java.awt.*;

public class Circle implements Shape {

    private double radius;
    Point point;
    Color color;


    public Circle(double rad, Point pnt, Color clr) {
        this.radius = rad;
        this.point = pnt;
        this.color = clr;
    }

    // Returns the radius of the Circle
    public double getRadius() {
        return radius;
    }

    // Sets the radius of the Circle
    public void setRadius(double rad) {
        this.radius = rad;
    }

    // Returns the center of the Circle
    public Point getCenter() {
        return new Point(this.point.x, this.point.y);
    }


    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color clr) {
            this.color = clr;
    }

    @Override
    public double getArea() {
        double areaCircle = Math.PI * Math.pow(radius, 2);
        return areaCircle;
    }

    @Override
    public double getPerimeter() {
        double circumCircle = Math.PI * 2 * radius;
        return circumCircle;
    }

    @Override
    public void translate(Point pnt) {
        this.point = pnt;
    }

}
