package com.desple.model.image_processing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

public class Grayscale {
    public static BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        Graphics g = convertedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return convertedImage;
    }
}
