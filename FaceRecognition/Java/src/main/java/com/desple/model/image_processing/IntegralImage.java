package com.desple.model.image_processing;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class IntegralImage {
    private int[][] integralImage = null;

    public IntegralImage(BufferedImage image) {
        int originalImageHeight = image.getHeight();
        int originalImageWidth = image.getWidth();
        integralImage = new int[originalImageHeight][originalImageWidth];
        Raster originalPixels = image.getData();

        int originalPixelValue = 0;
        for (int row = 0; row < originalImageHeight; row++) {
            for (int column = 0; column < originalImageWidth; column++) {
                originalPixelValue = originalPixels.getSample(column, row, 0);

                //For the leftmost pixel, just copy value from original
                if (row == 0 && column == 0) {
                    integralImage[row][column] = originalPixelValue;
                }

                //For the first row, just add the value to the left of this pixel
                else if (row == 0) {
                    integralImage[row][column] = originalPixelValue + integralImage[row][column - 1];
                }

                //For the first column, just add the value to the top of this pixel
                else if (column == 0) {
                    integralImage[row][column] = originalPixelValue + integralImage[row - 1][column];
                }

                //For a pixel that has pixels to its left, above it, and to the left and above diagonally,
                //add the left and above values and subtract the value to the left and above diagonally
                else {
                    integralImage[row][column] = originalPixelValue + integralImage[row][column - 1] + integralImage[row - 1][column] - integralImage[row - 1][column - 1];
                }
            }
        }
    }

    public int computeSum(int x1, int y1, int x2, int y2) {
        int a = x1 > 0 && y1 > 0 ? integralImage[x1-1][y1-1] : 0;
        int b = x1 > 0 ? integralImage[x1-1][y2] : 0;
        int c = y1 > 0 ? integralImage[x2][y1-1] : 0;
        int d = integralImage[x2][y2];
        return a + d - b - c;
    }
}
