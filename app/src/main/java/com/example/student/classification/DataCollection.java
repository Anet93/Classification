package com.example.student.classification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2015-10-14.
 */
public class DataCollection {
    private List<double[]> rawData;
    private List<Double> classes;
    private int numAttributes;

    public DataCollection() {
        rawData = new ArrayList();
        classes = new ArrayList();
    }
}
