package Backend.Objects.NodeTree;

import java.util.ArrayList;

public class TreeActioner {

    public ArrayList<Node> nodes;

    public TreeActioner() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        if (node != null) {
            this.nodes.add(node);
        }
    }

    public void saveNodsWithParent(Node parent) {
        for (Node node : this.nodes) {
            if (node != null) {
                node.setParent(parent);
            } else {
                this.nodes.remove(node);
            }
        }
        this.nodes.clear();
    }

    public void addTmpNode(Node node) {
        if (node != null) {
            this.nodes.add(node);
        }
    }

}
