package com.desple.model.structure;

public class InternalNode {
    private int left;
    private int right;
    private int featureId;
    private double threshold;

    public InternalNode(int left, int right, int featureId, double threshold) {
        this.left = left;
        this.right = right;
        this.featureId = featureId;
        this.threshold = threshold;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}