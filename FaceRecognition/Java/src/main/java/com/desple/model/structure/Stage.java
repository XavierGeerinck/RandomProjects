package com.desple.model.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="_")
@XmlAccessorType(XmlAccessType.FIELD)
public class Stage {
    @XmlElement(name="maxWeakCount")
    private int maxWeakCount;

    @XmlElement(name="stageThreshold")
    private double stageThreshold;

    @XmlElement(name="weakClassifiers")
    private WeakClassifier[] weakClassifiers;

    public Stage() {

    }

    public Stage(int maxWeakCount, double stageThreshold, WeakClassifier[] weakClassifiers) {
        this.maxWeakCount = maxWeakCount;
        this.stageThreshold = stageThreshold;
        this.weakClassifiers = weakClassifiers;
    }

    public int getMaxWeakCount() {
        return maxWeakCount;
    }

    public double getStageThreshold() {
        return stageThreshold;
    }

    public WeakClassifier[] getWeakClassifiers() {
        return weakClassifiers;
    }
}