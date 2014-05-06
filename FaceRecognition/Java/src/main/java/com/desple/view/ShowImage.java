package com.desple.view;

import com.desple.controller.ImageProcessing;
import com.desple.model.Detector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class ShowImage extends JFrame {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512 + 24 + 50; // the 24 is for the preview, the 50 is for the buttons

    private static final String TXT_START = "Start";
    private static final String TXT_LOAD_IMAGE = "Load Image";

    private Detector model;

    private ImageCanvas canvas;
    private PreviewImageCanvas previewCanvas;
    private JButton btnLoadImage;
    private JButton btnStart;

    public ShowImage(Detector model) {
        this.model = model;

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
        btnStart = new JButton(TXT_START);
        btnLoadImage = new JButton(TXT_LOAD_IMAGE);

        canvas = new ImageCanvas();
        previewCanvas = new PreviewImageCanvas();
    }

    private void _addComponents() {
        Container contentPane = this.getContentPane();
        contentPane.add(btnLoadImage);
        contentPane.add(btnStart);
        contentPane.add(canvas);
        contentPane.add(previewCanvas);
    }

    public void addLoadImageListener(ActionListener loadImage) {
        btnLoadImage.addActionListener(loadImage);
    }

    public void addStartListener(ActionListener start) {
        btnStart.addActionListener(start);
    }

    public ImageCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(ImageCanvas canvas) {
        this.canvas = canvas;
    }

    public PreviewImageCanvas getPreviewCanvas() {
        return previewCanvas;
    }

    public void setPreviewCanvas(PreviewImageCanvas previewCanvas) {
        this.previewCanvas = previewCanvas;
    }
}