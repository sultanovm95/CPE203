package com.company;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    final private List<Point> pnts;
    public Polygon(Point... points) {
        this.pnts = new ArrayList<Point>();
        for(Point point : points) {
            pnts.add(point);
        }
    }

    public Polygon(List<Point> myPolyList) {
        this.pnts = myPolyList;
    }

    public List<Point> getPoints() {
        return pnts;
    }

    public double perimeter() {
        List<Point> myList = pnts;
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


