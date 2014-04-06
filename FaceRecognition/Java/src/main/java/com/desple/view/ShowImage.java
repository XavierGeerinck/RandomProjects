package com.desple.view;

import com.desple.controller.ImageProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class ShowImage extends JFrame {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512 + 50; // the 50 is for the buttons

    private ImageProcessing imageProcessing;

    private ImageCanvas canvas;
    private JButton btnLoadImage;
    private JButton btnStart;

    public ShowImage(ImageProcessing imageProcessing) {
        this.imageProcessing = imageProcessing;

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
                    canvas.loadImage(imageProcessing.getImagePath());
                } catch (IOException e1) {
                    e1.printStackTrace();
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
        imageProcessing.processImage(this.canvas.getImage());
    }
}
