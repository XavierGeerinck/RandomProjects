package com.desple.view;

import com.desple.model.ImageProcessing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ShowImage extends JFrame {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512 + 50; // the 50 is for the buttons

    private ImageProcessing modelImageProcessing;

    private ImageCanvas canvas;
    private JButton btnLoadImage;
    private JButton btnStart;

    public ShowImage(ImageProcessing modelImageProcessing) {
        this.modelImageProcessing = modelImageProcessing;

        // Set BoxLayout
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        _initComponents();
        _addComponents();

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private void _initComponents() {
        btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _process();
            }
        });

        btnLoadImage = new JButton("Load Image");
        btnLoadImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL imageLocation = ClassLoader.getSystemResource("faces/default_face_512.jpg");
                    canvas.loadImage(imageLocation.getFile());
                } catch (IOException e1) {
                    System.out.println("Could not load the image");
                }
            }
        });

        canvas = new ImageCanvas();
    }

    private void _addComponents() {
        Container contentPane = this.getContentPane();
        contentPane.add(btnLoadImage);
        contentPane.add(btnStart);
        contentPane.add(canvas);
    }

    private void _process() {
        canvas.setImage(modelImageProcessing.convertToGrayscale(canvas.getImage()));
    }
}
