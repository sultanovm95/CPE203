package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorkSpace {
    private List<Shape> shapes;

    public WorkSpace() {
        shapes = new ArrayList<>();
    }

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public Shape get(int index) {
       return shapes.get(index);
    }

    public int size() {
        return shapes.size();
    }

    public List<Circle> getCircles() {
        List<Circle> circleList = new ArrayList<>();

        for(int i = 0; i < shapes.size(); i++) {
            if(shapes.get(i) instanceof Circle) {
                circleList.add((Circle) shapes.get(i));
            }
        }
        return circleList;
    }

    public List<Rectangle> getRectangles() {
        List<Rectangle> recList = new ArrayList<>();

        for(int i = 0; i < shapes.size(); i++) {
            if(shapes.get(i) instanceof Rectangle) {
                recList.add((Rectangle) shapes.get(i));
            }
        }
        return recList;
    }

    public List<Triangle> getTriangles() {
        List<Triangle> trianglesList = new ArrayList<>();
        for(int i = 0; i < shapes.size(); i++) {
            if(shapes.get(i) instanceof Triangle) {
                trianglesList.add((Triangle) shapes.get(i));
            }
        }
        return trianglesList;
    }

    public List<ConvexPolygon> getConvexPolygons() {
        List<ConvexPolygon> convexPolygonsList = new ArrayList<>();
        for(int i = 0; i < shapes.size(); i++) {
            if(shapes.get(i) instanceof ConvexPolygon) {
                convexPolygonsList.add((ConvexPolygon) shapes.get(i));
            }
        }
        return convexPolygonsList;
    }

    public List<Shape> getShapesByColor(Color color) {
        List<Shape> selectedShapes = new ArrayList<>();
        for(int i = 0; i <shapes.size(); i++) {
            if(shapes.get(i).getColor() == color) {
                selectedShapes.add(shapes.get(i));
            }
        }
        return  selectedShapes;
    }

    public double getAreaOfAllShapes() {
        double sumArea = 0;
        for(int i = 0; i < shapes.size(); i++) {
            sumArea += shapes.get(i).getArea();
        }
        return sumArea;
    }

    public double getPerimeterOfAllShapes() {
        double sumPerim = 0;
        for(int i = 0; i < shapes.size(); i++) {
            sumPerim += shapes.get(i).getPerimeter();
        }
        return sumPerim;
    }

}
