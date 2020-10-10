package com.company;

import java.awt.*;

public interface Shape {
    Color getColor();
    void setColor(Color clr);
    double getArea();
    double getPerimeter();
    void translate(Point pnt);

}
