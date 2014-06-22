package com.desple.controller;

import com.desple.model.Detector;
import com.desple.view.ShowImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ImageProcessing {
    private ShowImage view;
    private Detector model;

    private static final String FILE_LOCATION = ClassLoader.getSystemResource("faces/lena.jpg").getFile();

    public ImageProcessing(ShowImage view, Detector model) {
        this.view = view;
        this.model = model;

        this.view.addLoadImageListener(new LoadImageActionListener());
        this.view.addStartListener(new StartActionListener());
    }

    class LoadImageActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.getCanvas().loadImage(FILE_LOCATION);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.getFaces(view.getCanvas().getImage());
        }
    }
}