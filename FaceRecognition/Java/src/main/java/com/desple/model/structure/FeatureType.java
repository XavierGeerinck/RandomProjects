package com.desple.model.structure;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum FeatureType {
    HAAR;

    public String value() {
        return name();
    }

    public static FeatureType fromValue(String v) {
        return valueOf(v);
    }
}
