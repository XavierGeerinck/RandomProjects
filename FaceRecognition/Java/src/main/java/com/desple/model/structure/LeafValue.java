package com.desple.model.structure;

public class LeafValue {
    private double leftSumAddition;
    private double rightSumAddition;

    public LeafValue(double leftSumAddition, double rightSumAddition) {
        this.leftSumAddition = leftSumAddition;
        this.rightSumAddition = rightSumAddition;
    }

    public double getLeftSumAddition() {
        return leftSumAddition;
    }

    public void setLeftSumAddition(double leftSumAddition) {
        this.leftSumAddition = leftSumAddition;
    }

    public double getRightSumAddition() {
        return rightSumAddition;
    }

    public void setRightSumAddition(double rightSumAddition) {
        this.rightSumAddition = rightSumAddition;
    }
}