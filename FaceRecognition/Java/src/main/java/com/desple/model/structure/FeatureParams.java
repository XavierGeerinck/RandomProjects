package com.desple.model.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="featureParams")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeatureParams {
    @XmlElement(name="maxCatCount")
    private int maxCatCount;

    public FeatureParams() {

    }

    public FeatureParams(int maxCatCount) {
        this.maxCatCount = maxCatCount;
    }

    public int getMaxCatCount() {
        return maxCatCount;
    }
}
