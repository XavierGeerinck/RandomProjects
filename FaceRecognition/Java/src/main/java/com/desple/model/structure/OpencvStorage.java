package com.desple.model.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="opencv_storage")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpencvStorage {
    @XmlElement(name="cascade")
    private Cascade cascade;

    public OpencvStorage() {

    }

    public Cascade getCascade() {
        return cascade;
    }
}