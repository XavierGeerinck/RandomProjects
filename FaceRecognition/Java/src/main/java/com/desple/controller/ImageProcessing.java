package com.desple.controller;

import com.desple.model.cascade.Reader;
import com.desple.model.image_processing.Grayscale;
import com.desple.model.image_processing.IntegralImage;
import com.desple.model.structure.Cascade;
import com.desple.model.structure.OpencvStorage;
import com.desple.model.structure.Stage;

import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageProcessing {
    public ImageProcessing() {
    }

    public void processImage(BufferedImage image) {
        // Convert image to grayscale
        image = Grayscale.convertToGrayscale(image);

        // Calculate integral image
        IntegralImage integralImage = new IntegralImage(image);

        // Load the haar feature
        URL imageLocation = ClassLoader.getSystemResource("haar_cascades/haarcascade_frontalface_default.xml");
        OpencvStorage opencvStorage = Reader.readHaarCascade(imageLocation.getFile());

        for (Stage s : opencvStorage.getCascade().getStages()) {
            System.out.println(s.getMaxWeakCount());
        }
    }

    public String getImagePath() {
        URL imageLocation = ClassLoader.getSystemResource("faces/default_face_512.jpg");
        return imageLocation.getFile();
    }
}