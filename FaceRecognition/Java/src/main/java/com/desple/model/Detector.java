package com.desple.model;

import com.desple.model.cascade.Reader;
import com.desple.model.image_processing.Grayscale;
import com.desple.model.image_processing.ImageDetails;
import com.desple.model.structure.*;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Detector {
    private float baseScale; // The initial ratio between the window size and the Haar classifier size (default 2)
    private float scaleInc;  // The scale increment of the window size, at each step (default 1.25).
    private float inc;       // The shift of the window at each sub-step, in terms of percentage of the window size.
    private String cascadeLocation;

    private List<Stage> stages;

    public Detector(String cascadeLocation) {
        this.baseScale = 1.2f;
        this.scaleInc = 1.1f;
        this.inc = 0.05f; // shift of window at each step, in percentage
        this.cascadeLocation = cascadeLocation;
    }

    public void getFaces(BufferedImage image) {
        processImage(image, cascadeLocation, baseScale, scaleInc, inc);
    }

    public void processImage(BufferedImage image, String cascadeLocation, float baseScale, float scaleInc, float inc) {
        // Load the haar feature
        URL imageLocation = ClassLoader.getSystemResource(cascadeLocation);
        OpencvStorage opencvStorage = Reader.readHaarCascade(imageLocation.getFile());

        // Get detector size
        int scanWidth = opencvStorage.getCascade().getWidth();
        int scanHeight = opencvStorage.getCascade().getHeight();

        // Get image details
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // Max scale of the detector = imageSize / detectorSize
        float maxScale = (Math.min((imageWidth + 0.0f) / scanWidth, (imageHeight + 0.0f) / scanHeight));

        // Calculate integral image
        ImageDetails imageDetails = new ImageDetails(image);

        // Start detection
        // For each scale of the detection window
        for (float scale = baseScale; scale < maxScale; scale *= scaleInc) {
            // Calculate the sliding step
            int step = (int)(scale * scanWidth * inc);
            int size = (int)(scale * scanWidth); // Check this

            // For each position of the window on the image, check if we got a face
            for (int x = 0; x < imageWidth - size; x += step) {
                for (int y = 0; y < imageHeight - size; y += step) {
                    // Perform each stage of the detector one the window. If one fails, reject
                    _checkScanArea(baseScale, scanWidth, scanHeight, x, y, opencvStorage.getCascade().getStages(), opencvStorage.getCascade().getFeatures(), image, imageDetails);
                }
            }
        }
    }

    private void _checkScanArea(float scanAreaScale, int scanWindowWidth, int scanWindowHeight, int x, int y, List<Stage> stages, List<Feature> features, BufferedImage image, ImageDetails imageDetails) {
        int stageCount = 0;
        for (Stage s : stages) {
            // Check if the stage passes
            boolean stagePassed = _checkStage(scanAreaScale, scanWindowWidth, scanWindowHeight, x, y, s, features, image, imageDetails);

            // If we did not passed the stage, just return and continue to the next part
            if (!stagePassed) {
                return;
            }

            stageCount++;

            if (stageCount > 5) {
            System.out.println("Stage: " + stageCount);
                System.out.println("X: " + x);
                System.out.println("Y: " + y);
            }
        }

        // If we reach this then we found something that matched
        System.out.println("FOUND SOMETHING: " + x + ":" + y);
    }

    private boolean _checkStage(float scanAreaScale, int scanWindowWidth, int scanWindowHeight, int x, int y, Stage s, List<Feature> features, BufferedImage image, ImageDetails imageDetails) {
        float stageSum = 0.0f;

        // For every classifier, get the value of the classifier
        for (WeakClassifier wc : s.getWeakClassifiers()) {
            stageSum += _calculateClassifierValue(wc, scanAreaScale, scanWindowWidth, scanWindowHeight, x, y, s, features, image, imageDetails);
        }

        // Check if the stage passes
        return (stageSum > s.getStageThreshold());
    }

    private double _calculateClassifierValue(WeakClassifier wc, float scanAreaScale, int scanWindowWidth, int scanWindowHeight, int x, int y, Stage s, List<Feature> features, BufferedImage image, ImageDetails imageDetails) {
        // Get the rectangles
        Feature f = features.get(wc.getFeatureId());
        List<Rect> rectangles = f.getRectangles();

        boolean isLeft = _isLeft(scanAreaScale, scanWindowWidth, scanWindowHeight, x, y, wc, rectangles, image, imageDetails);

        if (isLeft) {
            return wc.getLeftLeafNode();
        }

        return wc.getRightLeafNode();
    }

    private boolean _isLeft(float scanAreaScale, int scanWindowWidth, int scanWindowHeight, int x, int y, WeakClassifier wc, List<Rect> rectangles, BufferedImage image, ImageDetails imageDetails) {
        // Calculate the window area
        int windowAreaWidth = (int)(scanAreaScale * scanWindowWidth);
        int windowAreaHeight = (int)(scanAreaScale * scanWindowHeight);

        // Get Inv Area
        double invArea = 1. / (windowAreaWidth * windowAreaHeight);

        // Get total sum and squared sum of all the pixels in this area
        int totalSum = imageDetails.computeSumSummedAreaTable(x, y, windowAreaWidth, windowAreaHeight);
        int totalSumSquared = imageDetails.computeSumSquaredSummedAreaTable(x, y, windowAreaWidth, windowAreaHeight);

        // Calculate moy and vnorm
        double moy = totalSum * invArea;
        double vnorm = totalSumSquared * invArea - moy * moy;
        vnorm = (vnorm > 1) ? Math.sqrt(vnorm) : 1;

        double sumRectangles = 0.0;

        // For every rectangle, calculate the integral sum + average it
        for (int i = 0; i < rectangles.size(); i++) {
            Rect r = rectangles.get(i);
            // Get the x and y coordinates, make sure to use our current x and y as offset!
//            int x1 = x + (int)(scanAreaScale * r.getXCoord()); // scale * x1
//            int x2 = x + (int)(scanAreaScale * (r.getXCoord() + r.getYCoord())); // scale * (x1 + y1)
//            int y1 = y + (int)(scanAreaScale * r.getX2Coord()); // scale * x2
//            int y2 = y + (int)(scanAreaScale * (r.getX2Coord() + r.getY2Coord())); // scale * (x2 + y2)

            int x1 = x + (int)(scanAreaScale * r.getXCoord()); // scale * x1
            int x2 = x + (int)(scanAreaScale * r.getX2Coord()); // scale * (x1 + y1)
            int y1 = y + (int)(scanAreaScale * r.getYCoord()); // scale * x2
            int y2 = y + (int)(scanAreaScale * r.getY2Coord()); // scale * (x2 + y2)

//            int x1 = 23;
//            int x2 = 26;
//            int y1 = 427;
//            int y2 = 434;

            if (y2 >= 512) {
                System.out.println("Rect Values: " + r.getValues());
                System.out.println("X: " + x);
                System.out.println("Y: " + y);
                System.out.println("X1: " + x1);
                System.out.println("X2: " + x2);
                System.out.println("Y1: " + y1);
                System.out.println("Y2: " + y2);
                System.out.println("ScanArea: " + scanAreaScale);
                System.out.println("X1 Coord: " + r.getXCoord());
                System.out.println("X2 Coord: " + r.getX2Coord());
                System.out.println("Y1 Coord: " + r.getYCoord());
                System.out.println("Y2 Coord: " + r.getY2Coord());
            }

            if (x1 == 23 && x2 == 26 && y1 == 427 && y2 == 434) {
                System.out.println("X1: " + x1);
                System.out.println("X2: " + x2);
                System.out.println("Y1: " + y1);
                System.out.println("Y2: " + y2);
                System.out.println("Rect Sum: " + imageDetails.computeSumSummedAreaTable(x1, y1, x2, y2) * r.getWeight());
            }

            // Calculate the integral image based on these coordinates
            sumRectangles += imageDetails.computeSumSummedAreaTable(x1, y1, x2, y2) * r.getWeight();
        }

        double sumRectanglesSquared = sumRectangles * invArea;

        // Return true if LEFT, false if RIGHT
        return (sumRectanglesSquared < wc.getTreshold() * vnorm);
    }
}
