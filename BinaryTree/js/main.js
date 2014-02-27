function insertNode(rootNode, node) {
    // If we don't have a root node yet, then set it
    if (!rootNode) {
        return node;
    }
    
    // Add new node
    // If < then add right
    // If > then add left
    if (rootNode.value < node.value) {
        // If the right node has been set already, repeat but then with the rightnode as the rootnode
        if (rootNode.rightNode) {
            this.insertNode(rootNode.rightNode, node);
        } else {
            rootNode.setRightNode(node);
        }
    } else {
        if (rootNode.leftNode) {
            this.insertNode(rootNode.leftNode, node);    
        } else {
            rootNode.setLeftNode(node);   
        }
    }
    
    return rootNode;
}

function printTree(rootNode) {
    if (rootNode.leftNode) {
        printTree(rootNode.leftNode);    
    }
    
    console.log(rootNode.value);
    
    if (rootNode.rightNode) {
        printTree(rootNode.rightNode);    
    }
}

function calculateHeight(rootNode) {
    if (rootNode) {
        return 1 + Math.max(calculateHeight(rootNode.leftNode), calculateHeight(rootNode.rightNode));    
    } else {
        return 0;    
    }
}
//
//var BinaryTree = function () {
//    this.rootNode = null;
//};
//
//BinaryTree.prototype.load = function (valuesLocation) {
//    var xhr = new XMLHttpRequest();
//    xhr.open("GET", valuesLocation, false); // false gets it synch
//    xhr.send();
//    
//    this.values = JSON.parse(xhr.responseText);
//};
//
//BinaryTree.prototype.insertNode = function(rootNode, node) {
//
//};
//
//BinaryTree.prototype.evaluateTree = function(tree) {
//    
//};
//
//BinaryTree.prototype.toString = function () {
//    if (this.leftNode) console.log(this.leftNode.toString());
//    console.log(this.value);
//    if (this.rightNode) console.log(this.rightNode.toString());
//};