package com.company;

public class Point {

    final private double x;
    final private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        double radius = 0.0;
        radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return radius;
    }

    public double getAngle() {
        double angle = Math.atan2(y,x);
        return angle;
    }

    public Point rotate90() {
        double rotatedX = y;
        double rotatedY = -x;
        return new Point(rotatedX, rotatedY);
    }
}
