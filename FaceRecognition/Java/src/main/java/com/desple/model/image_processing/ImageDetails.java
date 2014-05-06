package com.desple.model.image_processing;

import java.awt.image.BufferedImage;

public class ImageDetails {
    private int width;
    private int height;
    private int[][] greyscaleImage = null;
    private int[][] summedAreaTable = null;
    private int[][] squaredSummedAreaTable = null;


    public ImageDetails(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.greyscaleImage = new int[image.getWidth()][image.getHeight()];
        this.squaredSummedAreaTable = new int[image.getWidth()][image.getHeight()];
        this.summedAreaTable = new int[image.getWidth()][image.getHeight()];

        // calculate the details
        _calculateDetails(image);
    }

    private void _calculateDetails(BufferedImage image) {
        for (int x = 0; x < width; x ++) {
            int col = 0;
            int col2 = 0;

            for (int y = 0; y < height; y++) {
                // Get rgb
                int rgb = image.getRGB(x, y);

                // Extract the RGB values separately
                int red = (rgb & 0x00ff0000) >> 16;
                int green = (rgb & 0x0000ff00) >> 8;
                int blue = rgb & 0x000000ff;

                // Convert them to a greyscale pixel
                int greyscale = (30 * red + 59 * green + 11 * blue) / 100;

                // Save our SAT, Squared SAT and GreyScale
                greyscaleImage[x][y] = greyscale;
                summedAreaTable[x][y] = (x > 0 ? summedAreaTable[x - 1][y] : 0) + col + greyscale;
                squaredSummedAreaTable[x][y] = (x > 0 ? squaredSummedAreaTable[x - 1][y] : 0) + col2 + greyscale * greyscale;

                col += greyscale;
                col2 += greyscale * greyscale;
            }
        }
    }

    public int computeSumSquaredSummedAreaTable(int x1, int y1, int x2, int y2) {
        // Get the SAT points
        int a = squaredSummedAreaTable[x2][y2];
        int b = squaredSummedAreaTable[x1][y2];
        int c = squaredSummedAreaTable[x2][y1];
        int d = squaredSummedAreaTable[x1][y1];

        // Calculate the formula result
        int sum = a - b - c + d;

        // Return the result
        return sum;
    }

    public int computeSumSummedAreaTable(int x1, int y1, int x2, int y2) {
        // Get the SAT points
        int a = summedAreaTable[x2][y2];
        int b = summedAreaTable[x1][y2];
        int c = summedAreaTable[x2][y1];
        int d = summedAreaTable[x1][y1];

        // Calculate the formula result
        int sum = a - b - c + d;

        // Return the result
        return sum;
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

    public int[][] getGreyscaleImage() {
        return greyscaleImage;
    }

    public void setGreyscaleImage(int[][] greyscaleImage) {
        this.greyscaleImage = greyscaleImage;
    }

    public int[][] getSummedAreaTable() {
        return summedAreaTable;
    }

    public void setSummedAreaTable(int[][] summedAreaTable) {
        this.summedAreaTable = summedAreaTable;
    }

    public int[][] getSquaredSummedAreaTable() {
        return squaredSummedAreaTable;
    }

    public void setSquaredSummedAreaTable(int[][] squaredSummedAreaTable) {
        this.squaredSummedAreaTable = squaredSummedAreaTable;
    }
}
