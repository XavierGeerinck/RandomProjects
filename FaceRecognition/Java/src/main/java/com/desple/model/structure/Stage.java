package com.desple.model.structure;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stage {
    @XmlElement(name="maxWeakCount")
    private int maxWeakCount;

    @XmlElement(name="stageThreshold")
    private double stageThreshold;

    @XmlElementWrapper(name="weakClassifiers")
    @XmlElement(name="_")
    private List<WeakClassifier> weakClassifiers = new ArrayList<>();

    public Stage() {

    }

    public Stage(int maxWeakCount, double stageThreshold, List<WeakClassifier> weakClassifiers) {
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

    public List<WeakClassifier> getWeakClassifiers() {
        return weakClassifiers;
    }
}