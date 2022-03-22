package Backend.Objects.NodeTree;

import java.util.ArrayList;

public class TreeActioner {

    private Node root;

    public TreeActioner() {
        this.root = null;
    }

    /**
     * Print in console the tree
     *
     * @param root main node
     * @param parent the parent of actual node
     * @param tab n visual spaces \t
     */
    public void printTree(Node root, String tab) {
        if (root != null) {
            System.out.println(tab + root.toString());
            if (root.getChildren() != null && root.getChildren().size() > 0) {
                root.getChildren().forEach(child -> {
                    printTree(child, tab + "\t");
                });
            } else {
                root.setChildren(null);
            }
        }
    }

    /**
     * Check common nodes into other tree
     *
     * @param otherRoot the other tree root
     * @return an array with common nodes, null if doesn't exist
     */
    public ArrayList<Node[]> searchCommons(Node otherRoot) {
        ArrayList<Node[]> common = new ArrayList<>(); // reset array each time to avoid duplicates
        searchCommons(otherRoot, common);
        return common.size() > 0 ? common : null;
    }

    /**
     * Search common nodes between actual tree and other tree root
     *
     * @param otherRoot the other tree to compare with
     * @param common an array to save common nodes
     */
    private void searchCommons(Node otherRoot, ArrayList<Node[]> common) {
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

    private boolean isNodeReaded(ArrayList<Node[]> common, Node nodeSearch) {
        if (common != null) {
            return common.stream().anyMatch(nodes -> (nodes[0].equals(nodeSearch) || nodes[1].equals(nodeSearch)));
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
    private void existInTree(Node search, Node current, ArrayList<Node[]> common) {
        if (search.equals(current)) {
            if (!isNodeReaded(common, search)) {
                common.add(new Node[]{search, current});
            }
        } else {
            if (current.getChildren() != null && current.getChildren().size() > 0) {
                current.getChildren().forEach(child -> {
                    existInTree(search, child, common);
                });
            }
        }
    }

    /**
     * Update all children nodes and set a new parent
     *
     * @param parent the master node
     * @param children the master node children
     */
    public void setParents(Node parent, ArrayList<Node> children) {
        if (children != null) {
            children.stream().filter(child -> (child != null)).forEachOrdered(child -> {
                child.setParent(parent);
            });
        }
    }

    // GETTERS AND SETTERS + node create
    public Node createNode(String type, String variable) {
        return createNode(type, variable, null);
    }

    public Node createNode(String type, String variable, Node parent) {
        return new Node(type.toUpperCase(), variable, parent);
    }

    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

}
