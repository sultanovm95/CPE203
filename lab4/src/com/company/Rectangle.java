package com.company;

import java.awt.*;

public class Rectangle implements Shape {

    private double width;
    private double height;
    Point point;
    Color color;

    public Rectangle(double width, double height, Point pnt, Color clr) {
        this.width = width;
        this.height = height;
        this.point = pnt;
        this.color = clr;
    }

    // Returns the width of the Rectangle
    public double getWidth() {
        return width;
    }

    // Sets the width of the Rectangle
    public void setWidth(double width) {
        this.width = width;
    }

    // Returns the height of the Rectangle
    public double getHeight() {
        return this.height;
    }

    // Sets the height of the Rectangle
    public void setHeight(double height) {
        this.height = height;
    }

    // Returns the Point representing the top left corner of the Rectangle
    public Point getTopLeft() {
        return new Point(point.x, point.y);
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
        double areaPerimeter = width * height;
        return areaPerimeter;
    }

    @Override
    public double getPerimeter() {
        double perimeter = 2*(width + height);
        return perimeter;
    }

    @Override
    public void translate(Point pnt) {

    }

}
