package com.example.student.classification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2015-10-14.
 */
public class Features {
    private List<Point> points;
    private int strokes;

    public Features(List<Point> points, int strokes) {
        this.points = points;
        this.strokes = strokes;
    }

    public double getFirstLastDist() {
        double distFL = 0;
        Point pointF = points.get(0);
        Point pointL = points.get(points.size() - 1);
        distFL = Math.sqrt(Math.pow(pointF.getX() - pointL.getX(), 2) + Math.pow(pointF.getY() - pointL.getY(), 2));
        return distFL;
    }

    public double getLength() {
        double length = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            Point point1 = points.get(i);
            Point point2 = points.get(i + 1);
            length = length + Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
        }
        return length;
    }

    public double getFirstLastAngleX() {
        double angleFLX = 0;
        Point pointF = points.get(0);
        Point pointL = points.get(points.size() - 1);
        angleFLX = (pointL.getX() - pointF.getX()) / getFirstLastDist();
        return angleFLX;
    }

    public double getFirstLastAngleY() {
        double angleFLY = 0;
        Point pointF = points.get(0);
        Point pointL = points.get(points.size() - 1);
        angleFLY = (pointL.getY() - pointF.getY()) / getFirstLastDist();
        return angleFLY;
    }


    public List<Double> calculateFeatures() {
        List<Double> features = new ArrayList();
        // Length
        double length = getLength();
        features.add(length);
        // First - Last distance
        double distFL = getFirstLastDist();
        features.add(distFL);
        //Angle First - Last X
        double angleFLX = getFirstLastAngleX();
        features.add(angleFLX);
        //Angle First - Last Y
        double angleFLY = getFirstLastAngleY();
        features.add(angleFLY);
        //
        features.add(Double.valueOf(strokes));

        return features;
    }

}
