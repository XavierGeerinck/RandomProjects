package com.desple.model.structure;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="cascade")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cascade {
    @XmlAttribute(name="type_id")
    private String typeId;

    @XmlElement(name="stageType")
    private StageType stageType;

    @XmlElement(name="featureType")
    private FeatureType featureType;

    @XmlElement(name="height")
    private int height;

    @XmlElement(name="width")
    private int width;

    @XmlElement(name="stageParams")
    private StageParams stageParams;

    @XmlElement(name="featureParams")
    private FeatureParams featureParams;

    @XmlElement(name="stageNum")
    private int stageNum;

    @XmlElementWrapper(name="stages")
    @XmlElement(name="_")
    private List<Stage> stages = new ArrayList<>();

    @XmlElementWrapper(name="features")
    @XmlElement(name="_")
    private List<Feature> features = new ArrayList<>();

    public Cascade() {

    }

    public Cascade(String typeId, StageType stageType, FeatureType featureType, int height, int width, StageParams stageParams, FeatureParams featureParams, int stageNum, List<Stage> stages, List<Feature> features) {
        this.typeId = typeId;
        this.stageType = stageType;
        this.featureType = featureType;
        this.height = height;
        this.width = width;
        this.stageParams = stageParams;
        this.featureParams = featureParams;
        this.stageNum = stageNum;
        this.stages = stages;
        this.features = features;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public StageType getStageType() {
        return stageType;
    }

    public void setStageType(StageType stageType) {
        this.stageType = stageType;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public StageParams getStageParams() {
        return stageParams;
    }

    public void setStageParams(StageParams stageParams) {
        this.stageParams = stageParams;
    }

    public FeatureParams getFeatureParams() {
        return featureParams;
    }

    public void setFeatureParams(FeatureParams featureParams) {
        this.featureParams = featureParams;
    }

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}