package Backend.Objects.NodeTree;

import java.util.ArrayList;

public class TreeActioner {

    private Node root;

    public TreeActioner() {
        this.root = null;
    }

    public void setParents(Node parent, ArrayList<Node> children) {
        if (children != null) {
            children.stream().filter(child -> (child != null)).forEachOrdered(child -> {
                child.setParent(parent);
            });
        }
    }

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

    public void printTree(Node root, String parent) {
        if (root != null) {
            System.out.println(parent.toUpperCase() + ": " + root.toString());
            if (root.getChildren() != null && root.getChildren().size() > 0) {
                root.getChildren().forEach(child -> {
                    printTree(child, root.getVariable());
                });
            }
        }
    }
}
