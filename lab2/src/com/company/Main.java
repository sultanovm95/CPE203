package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Circle circle = new Circle(new Point(2, 2), 2.0);
        Rectangle rectangle = new Rectangle(new Point(-5, 4), new Point(5, -10));
        Polygon polygon = new Polygon(new Point(0, 0), new Point(3, 1),
                new Point(1, 4), new Point(-1, 4));


        System.out.println(Util.perimeter(circle));
        System.out.println(Util.perimeter(rectangle));
        System.out.println(Util.perimeter(polygon));

        System.out.println("Longest is: " + Bigger.whichIsBigger(circle,rectangle,polygon));

    }
}
