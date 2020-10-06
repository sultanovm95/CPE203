package com.company;

public class Rectangle {
    final private Point topLeft;
    final private Point bottomRight;

    public Rectangle(Point cord1XY, Point cord2XY) {
        this.topLeft = cord1XY;
        this.bottomRight = cord2XY;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double perimeter() {
        double width = bottomRight.getX() - topLeft.getX() ;
        double length = topLeft.getY() - bottomRight.getY();
        double perimeter = 2*(width + length);

        return perimeter;
    }
}
