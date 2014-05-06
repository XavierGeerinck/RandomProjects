package com.desple.model;

import com.desple.model.cascade.Reader;
import com.desple.model.image_processing.Grayscale;
import com.desple.model.image_processing.ImageDetails;
import com.desple.model.structure.*;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class Detector {
    public Detector() {

    }

    public void getFaces(BufferedImage image, String cascadeLocation) {
        float baseScale = 1.0f;
        float scaleInc = 1.1f;
        float inc = 0.05f; // shift of window at each step, in percentage

        processImage(image, cascadeLocation, baseScale, scaleInc, inc);
    }

    public void processImage(BufferedImage image, String cascadeLocation, float baseScale, float scaleInc, float inc) {
        // Calculate integral image
        ImageDetails imageDetails = new ImageDetails(image);

        // Load the haar feature
        URL imageLocation = ClassLoader.getSystemResource(cascadeLocation);
        OpencvStorage opencvStorage = Reader.readHaarCascade(imageLocation.getFile());

        int scanWidth = opencvStorage.getCascade().getWidth();
        int scanHeight = opencvStorage.getCascade().getHeight();

        double scaleIncrease = 1.1;
        double maxScale = (Math.min((image.getWidth() + 0.f) / scanWidth,(image.getHeight() + 0.0f) / scanHeight));

        for (double scale = baseScale; scale < maxScale; scale *= scaleIncrease) {
            // calculate the step
            int step = (int)Math.round(scale * scanWidth * inc);
            int size = (int)Math.round(scale * scanWidth); // Check this

            for (int x = 0; x < image.getWidth() - size; x += step) {
                for (int y = 0; y < image.getHeight() - size; y += step) {
                    _checkScanArea(baseScale, scanWidth, scanHeight, x, y, opencvStorage.getCascade().getStages(), opencvStorage.getCascade().getFeatures(), image, imageDetails);
                }
            }
        }
    }

    private void _checkScanArea(float scanAreaScale, int scanWindowWidth, int scanWindowHeight, int x, int y, List<Stage> stages, List<Feature> features, BufferedImage image, ImageDetails imageDetails) {
        // For every rectangle, check the stage
        for (Stage s : stages) {
            boolean stagePassed = _checkStage(scanAreaScale, scanWindowWidth, scanWindowHeight, x, y, s, features, image, imageDetails);

            // If we did not passed the stage, just return and continue to the next part
            if (!stagePassed) {
                return;
            }
        }

        // If we reach this then we found something that matched
        System.out.println("FOUND SOMETHING: " + x + ":" + y);
    }

    private boolean _checkStage(float scanAreaScale, int scanWindowWidth, int scanWindowHeight, int x, int y, Stage s, List<Feature> features, BufferedImage image, ImageDetails imageDetails) {
        double stageSum = 0.0;

        // For every stage, go through the classifiers
        for (WeakClassifier wc : s.getWeakClassifiers()) {
            stageSum += _calculateClassifierValue(wc, scanAreaScale, scanWindowWidth, scanWindowHeight, x, y, s, features, image, imageDetails);
        }

        //System.out.println("Result: " + stageResult);
        //System.out.println("Treshold: " + s.getStageThreshold());
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
            int x1 = x + (int)(scanAreaScale * r.getXCoord()); // scale * x1
            int x2 = x + (int)(scanAreaScale * (r.getXCoord() + r.getYCoord())); // scale * (x1 + y1)
            int y1 = y + (int)(scanAreaScale * (r.getXCoord() + r.getWidth())); // scale * x2
            int y2 = y + (int)(scanAreaScale * ((r.getXCoord() + r.getWidth()) + (r.getYCoord() + r.getHeight()))); // scale * (x2 + y2)

            // Calculate the integral image based on these coordinates
            sumRectangles += imageDetails.computeSumSummedAreaTable(x1, y1, x2, y2) * r.getWeight();
        }

        double sumRectanglesSquared = sumRectangles * invArea;

        // Return true if LEFT, false if RIGHT
        return (sumRectanglesSquared < wc.getTreshold() * vnorm);
    }
}
