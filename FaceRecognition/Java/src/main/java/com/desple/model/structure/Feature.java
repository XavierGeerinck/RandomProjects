package com.desple.model.structure;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Feature {
    @XmlElementWrapper(name="rects")
    @XmlElement(name="_")
    private List<Rect> rectangles = new ArrayList<>();

    public Feature() {

    }

    public List<Rect> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<Rect> rectangles) {
        this.rectangles = rectangles;
    }
}