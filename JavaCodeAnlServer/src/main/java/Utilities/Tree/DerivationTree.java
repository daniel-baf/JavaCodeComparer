package Utilities.Tree;

import java.util.ArrayList;

public class DerivationTree<T> {

    private Node<T> root;

    public DerivationTree() {
        this.root = null;
    }

    /**
     * Check common nodes into other tree
     *
     * @param otherRoot the other tree root
     * @return an array with common nodes, null if doesn't exist
     */
    public ArrayList<CommonData<T>> searchCommons(Node<T> otherRoot) {
        ArrayList<CommonData<T>> commonNodes = new ArrayList<>();
        searchCommonsK(this.root, commonNodes, otherRoot);

//        searchCommons(otherRoot, common);
        return commonNodes.size() > 0 ? commonNodes : null;
    }

    /**
     * Search common nodes between actual tree and other tree root
     *
     * @param otherRoot the other tree to compare with
     * @param common an array to save common nodes
     */
    private void searchCommonsK(Node<T> currentNode, ArrayList<CommonData<T>> common, Node<T> externalTreeSlice) {
        // start search
        if (currentNode != null && this.root != null) {
            // check data
            CommonData<T> newNode = searchNode(currentNode.getData(), externalTreeSlice, common);
            if (newNode != null) { // save data if there is at least 1 node matched
                newNode.setParent(currentNode.getParent());
                common.add(newNode);
            }
            if (currentNode.getChildren() != null && currentNode.getChildren().size() > 0) {
                // recursive call
                currentNode.getChildren().forEach(child -> {
                    searchCommonsK(child, common, externalTreeSlice);
                });
            }
        }
    }

    /**
     * Check if a node exists into the actual tree
     *
     * @param search the node to search
     * @param current the node we are looking
     * @param common an array to save common nodes
     */
    private CommonData<T> searchNode(T search, Node<T> externalTreeSlice, ArrayList<CommonData<T>> common) {
        CommonData commonData = isDataPreviousFound(common, search); // check previous data found
        if (commonData != null) { // previously found
            return new CommonData<>(search, commonData.getMatchedNodes());
        } else {
            // find nodes
            ArrayList<Node<T>> matchedNodes = new ArrayList<>();
            findMatchedNodes(matchedNodes, externalTreeSlice, search);
            if (matchedNodes.size() > 0) {
                return new CommonData<>(search, matchedNodes);
            }
        }
        return null;
    }

    private void findMatchedNodes(ArrayList<Node<T>> matchedNodes, Node<T> currentNode, T data) {
        if (currentNode != null) {
            if (currentNode.getData().equals(data)) {
                matchedNodes.add(currentNode);
            }
            if (currentNode.getChildren() != null && currentNode.getChildren().size() > 0) {
                currentNode.getChildren().forEach(_child -> {
                    findMatchedNodes(matchedNodes, _child, data);
                });
            }
        }
    }

    private CommonData<T> isDataPreviousFound(ArrayList<CommonData<T>> common, T toFind) {
        if (common != null) {
            for (CommonData<T> commonData : common) {
                if (toFind.equals(commonData.getData())) {
                    return commonData;
                }
            }
        }
        return null;
    }

    /**
     * Update all children nodes and set a new parent
     *
     * @param parent the master node
     * @param children the master node children
     */
    public void setParents(Node<T> parent, ArrayList<Node<T>> children) {
        if (children != null) {
            children.stream().filter(child -> (child != null)).forEachOrdered(child -> {
                child.setParent(parent);
            });
        }
    }

    // GETTERS AND SETTERS + node create
    public Node<T> createNode(T data) {
        return createNode(data, null);
    }

    public Node<T> createNode(T data, Node<T> parent) {
        return new Node<>(data, parent);
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

}
