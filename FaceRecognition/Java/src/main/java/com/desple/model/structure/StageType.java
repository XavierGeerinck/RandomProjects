package com.desple.model.structure;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum StageType {
    BOOST;

    public String value() {
        return name();
    }

    public static StageType fromValue(String v) {
        return valueOf(v);
    }
}
