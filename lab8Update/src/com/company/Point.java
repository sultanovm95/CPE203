package com.company;

public class Point {
    double x,y;
    int z;

    public Point(Double x, Double y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
/*
    public Point multiplyPoint(double multiplier) {
        return new Point(x * multiplier, y * multiplier, z);
    }
*/

    public Point multiplyPoint(double multiplier) {
        return new Point(x * multiplier, y * multiplier, z);
    }

    public Point translate(double xTran, double yTran) {
         return new Point(x + xTran, y + yTran, z);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
