package com.desple.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCanvas extends JPanel {
    private BufferedImage image;

    public ImageCanvas() {
        image = null;
    }

    public Dimension getPreferredSize() {
        return new Dimension(512, 512);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.image != null) {
            g.drawImage(this.image, 0, 0, 512, 512, this);
        }
    }

    public void loadImage(String imageLocation) throws IOException {
        this.image = ImageIO.read(new File(imageLocation));
        repaint();
    }
}
