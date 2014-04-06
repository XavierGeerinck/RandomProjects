package com.desple.model.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="stageParams")
@XmlAccessorType(XmlAccessType.FIELD)
public class StageParams {
    @XmlElement(name="maxWeakCount")
    private int maxWeakCount;

    public StageParams() {

    }

    public StageParams(int maxWeakCount) {
        this.maxWeakCount = maxWeakCount;
    }

    public int getMaxWeakCount() {
        return maxWeakCount;
    }
}
