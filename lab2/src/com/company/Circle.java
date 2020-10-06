package com.company;

public class Circle {
    final private Point point;
    final private double radius;

    public Circle(Point cordXY, double rad) {
        this.point = cordXY;
        this.radius = rad;
    }

    public Point getCenter() {
        return point;
    }

    public double getRadius() {
        return radius;
    }

    public double perimeter() {
        double perimeter = 2 * Math.PI * radius;

        return perimeter;
    }
}
