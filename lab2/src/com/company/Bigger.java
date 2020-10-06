package com.company;

public class Bigger {
    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon) {
        double bigger = Util.perimeter(circle);

        if(Util.perimeter(rectangle) > bigger) {
            bigger = Util.perimeter(rectangle);
        }

        if(Util.perimeter(polygon) > bigger) {
            bigger = Util.perimeter(polygon);
        }

        return bigger;

    }

}
