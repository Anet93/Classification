package com.example.student.classification;

import java.util.ArrayList;
import java.util.Collections;
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

    public double getMaxHeight() {
        double height = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double x1 = points.get(i).getY();
                double x2 = points.get(j).getY();
                double temp = Math.abs(x1 - x2);
                if (temp > height)
                    height = temp;
            }
        }
        return height;
    }

    public double getMaxWidth() {
        double width = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double y1 = points.get(i).getX();
                double y2 = points.get(j).getX();
                double temp = Math.abs(y1 - y2);
                if (temp > width)
                    width = temp;
            }
        }
        return width;
    }

    public double getDiagonal() {
        return Math.sqrt(Math.pow(getMaxHeight(), 2) + Math.pow(getMaxWidth(), 2));
    }

    public double getDerivativeA() {
        return Math.pow(Math.max(getMaxHeight(), getMaxWidth()), 2);
    }

    public double getDerivativeB() {
        return Math.pow(Math.min(getMaxHeight(), getMaxWidth()), 2);
    }

    public double f6() {
        return Math.sqrt(1 - Math.pow(getDerivativeB(), 2) / Math.pow(getDerivativeA(), 2));
    }

    public double f7() {
        return getDerivativeA() / getDerivativeB();
    }

    public double getMinX() {
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < points.size() - 1; i++) {
            double x = points.get(i).getX();
            if (x < min)
                min = x;
        }
        return min;
    }

    public double getMinY() {
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            double y = points.get(i).getX();
            if (y < min)
                min = y;
        }
        return min;
    }

    public double getMaxX() {
        double max = Integer.MIN_VALUE;
        for (int i = 0; i < points.size(); i++) {
            double x = points.get(i).getX();
            if (x > max)
                max = x;
        }
        return max;
    }

    public double getMaxY() {
        double max = Integer.MIN_VALUE;
        for (int i = 0; i < points.size(); i++) {
            double y = points.get(i).getX();
            if (y > max)
                max = y;
        }
        return max;
    }

    public double f12() {
        return (getMinX() + 0.5 * (getMaxX() - getMinX()));
    }

    public double f13() {
        return (getMinY() + 0.5 * (getMaxY() - getMinY()));
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
        //Number od Strokes
        features.add(Double.valueOf(strokes));
//        //Height
//        double height = getMaxHeight();
//        features.add(height);
//        //Width
//        double width = getMaxWidth();
//        features.add(width);

        //Diagonal
        double diagonal = getDiagonal();
        features.add(diagonal);
        //Not concentrically
        double nc = f6();
        features.add(nc);
        //ratio min max
        double ratio = f7();
        features.add(ratio);
        //central x
        double centralX = f12();
        features.add(centralX);
        //central y
        double centralY = f13();
        features.add(centralY);

        return features;
    }

}
