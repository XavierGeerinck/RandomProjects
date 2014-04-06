package com.desple.model.structure;

public class Rect {
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;
    private double weight;

    public Rect(int xCoord, int yCoord, int widht, int height, double weight) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = widht;
        this.height = height;
        this.weight = weight;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
