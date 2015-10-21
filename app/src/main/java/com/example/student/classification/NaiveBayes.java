package com.example.student.classification;

import java.util.List;

/**
 * Created by student on 2015-10-21.
 */
public class NaiveBayes {
    private double[][] means;
    private double[][] variances;
    private double[] classDistribution;
    private List<Double> classes;
    private boolean istrained;
    private double varianceNoice;

    public NaiveBayes() {
        istrained = false;
        varianceNoice = 0.00001;
    }

    public void buildClassifier(DataCollection data) {
        means = new double[data.numberOfClasses()][data.numberOfAttributes()];
        variances = new double[data.numberOfClasses()][data.numberOfAttributes()];
        classDistribution = new double[data.numberOfClasses()];
        classes = data.getClasses();
        for (int i = 0; i < data.numberOfClasses(); i++) {
            classDistribution[i] = ((double) data.numOfRecords(i)) / ((double) data.numOfRecords());
            for (int j = 0; j < data.numberOfAttributes(); j++) {
                means[i][j] = data.getMeanValue(j, i);
                variances[i][j] = data.getVarValue(j, i) + varianceNoice;
            }
        }
        istrained = true;
    }

    public boolean isTrained() {
        return istrained;
    }

    public double classifyInstance(Features f) {
        double currentp = 0;
        int currentID = 0;
        List<Double> featureValues = f.calculateFeatures();
        for (int i = 0; i < classes.size(); i++) {
            double py = classDistribution[i];
            for (int j = 0; j < featureValues.size(); j++) {
                py = py * (1 / (Math.sqrt(2 * Math.PI * variances[i][j]))) * Math.exp(-1 / (2 * variances[i][j]) * Math.pow((featureValues.get(j) - means[i][j]), 2));
            }
            if (py > currentp) {
                currentp = py;
                currentID = i;
            }
        }
        return classes.get(currentID);
    }
}
