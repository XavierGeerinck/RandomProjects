package com.desple.model.structure;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * InternalNodes structure:
 * 0 : node.left  : int
 * 1 : node.right : int
 * 2 : featureId  : int
 * 3 : treshhold  : double
 *
 * LeafValues structure:
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WeakClassifier {
    @XmlList()
    @XmlElement(name="internalNodes")
    private List<String> internalNodes;

    @XmlList()
    @XmlElement(name="leafValues")
    private List<String> leafValues;

    public WeakClassifier() {
    }

    public List<String> getInternalNodes() {
        return internalNodes;
    }

    public void setInternalNodes(List<String> internalNodes) {
        this.internalNodes = internalNodes;
    }

    public List<String> getLeafValues() {
        return leafValues;
    }

    public void setLeafValues(List<String> leafValues) {
        this.leafValues = leafValues;
    }

    public int getLeftNode() {
        return Integer.parseInt(this.internalNodes.get(0));
    }

    public int getRightNode() {
        return Integer.parseInt(this.internalNodes.get(1));
    }

    public int getFeatureId() {
        return Integer.parseInt(this.internalNodes.get(2));
    }

    public double getTreshold() {
        return Double.parseDouble(this.internalNodes.get(3));
    }

    public double getLeftLeafNode() {
        return Double.parseDouble(this.leafValues.get(0));
    }

    public double getRightLeafNode() {
        return Double.parseDouble(this.leafValues.get(1));
    }
}