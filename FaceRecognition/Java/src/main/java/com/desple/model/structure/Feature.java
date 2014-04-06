package com.desple.model.structure;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="_")
public class Feature {
    private Rect[] rectangles;

    public Feature() {

    }

    public Feature(Rect[] rectangles) {
        this.rectangles = rectangles;
    }

    public Rect[] getRectangles() {
        return rectangles;
    }

    public void setRectangles(Rect[] rectangles) {
        this.rectangles = rectangles;
    }
}