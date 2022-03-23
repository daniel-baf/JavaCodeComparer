package Utilities.Tree;

import java.util.ArrayList;

public class DerivationTree<T> {

    private Node<T> root;

    public DerivationTree() {
        this.root = null;
    }

    public void print() {
        print(this.root, "");
    }

    private void print(Node<T> current, String tab) {
        if (current != null) {
            System.out.println(tab + current.toString());
            if (current.getChildren() != null && current.getChildren().size() > 0) {
                current.getChildren().forEach(node -> {
                    print(node, tab + "\t");
                });
            }
        }
    }

    /**
     * Check common nodes into other tree
     *
     * @param otherRoot the other tree root
     * @return an array with common nodes, null if doesn't exist
     */
    public ArrayList<Node<T>[]> searchCommons(Node<T> otherRoot) {
        ArrayList<Node<T>[]> common = new ArrayList<>(); // reset array each time to avoid duplicates
        searchCommons(otherRoot, common);
        return common.size() > 0 ? common : null;
    }

    /**
     * Search common nodes between actual tree and other tree root
     *
     * @param otherRoot the other tree to compare with
     * @param common an array to save common nodes
     */
    private void searchCommons(Node<T> otherRoot, ArrayList<Node<T>[]> common) {
        // start searching
        if (otherRoot != null && this.root != null) {
            existInTree(otherRoot, this.root, common);
            if (otherRoot.getChildren() != null && otherRoot.getChildren().size() > 0) {
                otherRoot.getChildren().forEach(child -> {
                    searchCommons(child, common);
                });
            }
        }
    }

    private boolean isNodeReaded(ArrayList<Node<T>[]> common, Node<T> nodeSearch) {
        if (common != null) {
            return common.stream().anyMatch(nodes -> (nodes[0].getData().equals(nodeSearch.getData()) || nodes[1].getData().equals(nodeSearch.getData())));
        }
        return false;
    }

    /**
     * Check if a node exists into the actual tree
     *
     * @param search the node to search
     * @param current the node we are looking
     * @param common an array to save common nodes
     */
    private void existInTree(Node<T> search, Node<T> current, ArrayList<Node<T>[]> common) {
        if (search.getData().equals(current.getData())) {
            if (!isNodeReaded(common, search)) {
                common.add(new Node[]{search, current});
            }
        } else if (current.getChildren() != null && current.getChildren().size() > 0) {
            current.getChildren().forEach(_item -> {
                existInTree(search, _item, common);
            });
        }
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
