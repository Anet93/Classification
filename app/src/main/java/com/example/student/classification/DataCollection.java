package com.example.student.classification;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public void build(InputStream inputStream) {
        try {
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null) {
                    String[] stringRecord = receiveString.split(",");
                    numAttributes = stringRecord.length - 1;
                    double[] doubleRecord = new double[stringRecord.length];
                    for (int i = 0; i < doubleRecord.length; i++) {
                        doubleRecord[i] = Double.parseDouble(stringRecord[i]);
                    }
                    if (!classes.contains(doubleRecord[doubleRecord.length - 1])) {
                        classes.add(doubleRecord[doubleRecord.length - 1]);
                    }
                    rawData.add(doubleRecord);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int numOfRecords() {
        return rawData.size();
    }

    public int numOfRecords(int classIndex) {
        int numOfRecords = 0;
        for (int i = 0; i < rawData.size(); i++) {
            double[] record = rawData.get(i);
            if (record[numAttributes] == classes.get(classIndex)) {
                numOfRecords++;
            }
        }
        return numOfRecords;
    }

    public int numberOfAttributes() {
        return numAttributes;
    }

    public int numberOfClasses() {
        return classes.size();
    }

    public List<Double> getClasses() {
        return classes;
    }

    public double getMeanValue(int attributeIndex, int classIndex) {
        double mean = 0;
        for (int i = 0; i < rawData.size(); i++) {
            double[] record = rawData.get(i);
            if (record[numAttributes] == classes.get(classIndex)) {
                mean = mean + record[attributeIndex];
            }
        }
        mean = mean / ((double) numOfRecords(classIndex));
        return mean;
    }

    public double getVarValue(int attributeIndex, int classIndex) {
        double var = 0;
        double mean = getMeanValue(attributeIndex, classIndex);
        for (int i = 0; i < rawData.size(); i++) {
            double[] record = rawData.get(i);
            if (record[numAttributes] == classes.get(classIndex)) {
                double diff = (record[attributeIndex] - mean);
                var = var + Math.pow(diff, 2);
            }
        }
        var = var / ((double) numOfRecords(classIndex));
        return var;
    }
}
