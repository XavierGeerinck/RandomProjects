package com.desple.model.structure;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 0: xCoord : int
 * 1: yCoord : int
 * 2: width  : int
 * 3: height : int
 * 4: weight : double
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Rect {
    @XmlList
    @XmlValue
    private List<String> values;

    public Rect() {
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int getXCoord() {
        return Integer.parseInt(this.values.get(0));
    }

    public int getYCoord() {
        return Integer.parseInt(this.values.get(1));
    }

    public int getWidth() {
        return Integer.parseInt(this.values.get(2));
    }

    public int getHeight() {
        return Integer.parseInt(this.values.get(3));
    }

    public double getWeight() {
        return Double.parseDouble(this.values.get(4));
    }
}
