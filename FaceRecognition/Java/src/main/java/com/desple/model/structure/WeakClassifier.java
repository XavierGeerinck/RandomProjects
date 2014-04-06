package com.desple.model.structure;

public class WeakClassifier {
    private InternalNode internalNode;
    private LeafValue leafValue;

    public WeakClassifier(InternalNode internalNode, LeafValue leafValue) {
        this.internalNode = internalNode;
        this.leafValue = leafValue;
    }

    public InternalNode getInternalNode() {
        return internalNode;
    }

    public void setInternalNode(InternalNode internalNode) {
        this.internalNode = internalNode;
    }

    public LeafValue getLeafValue() {
        return leafValue;
    }

    public void setLeafValue(LeafValue leafValue) {
        this.leafValue = leafValue;
    }
}