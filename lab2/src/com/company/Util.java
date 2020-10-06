package com.company;

import java.util.List;

public class Util {
    public static double perimeter(Circle circle) {
        double perimeter = 2 * Math.PI * circle.getRadius();

        return perimeter;
    }

    public static double perimeter(Rectangle rectangle) {
        double width = rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX() ;
        double length = rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY();
        double perimeter = 2*(width + length);

        return perimeter;
    }

    public static double perimeter(Polygon polygon) {
        List<Point> myList = polygon.getPoints();
        double totalRadius = 0;
        for(int i = 0; i < myList.size() ; i++) {
            if( i == (myList.size() -1)) {
                    totalRadius += calcRadius(myList.get(i), myList.get(0));
            } else {
                totalRadius += calcRadius(myList.get(i), myList.get(i + 1));
            }
        }

        return totalRadius;
    }

    private static double calcRadius(Point pnt1, Point pnt2) {
        double x = pnt1.getX() - pnt2.getX();
        double y = pnt1.getY() - pnt2.getY();
        double radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        return radius;
    }
}

