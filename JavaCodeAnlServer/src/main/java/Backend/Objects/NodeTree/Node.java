package Backend.Objects.NodeTree;

import java.util.ArrayList;

public class Node {

    // id
    private String variable;
    private String type;
    private Node parent;
    private ArrayList<String> attributes;
    private String value;
    private ArrayList<Node> children;

    public Node() {
        this(null, null);
    }

    public Node(String type, String variable) {
        this(type, variable, null);
    }

    public Node(String type, String variable, Node parent) {
        this.type = type;
        this.variable = variable;
        this.parent = parent;
        this.attributes = new ArrayList<>();
        this.value = null;
        this.children = null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariable() {
        return this.variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<String> getAttribute() {
        return this.attributes;
    }

    public void setAttribute(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addAttributes(String... attributes) {
        for (String attribute : attributes) {
            this.attributes.add(attribute);
        }
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        String attributes = "";
        String children = "";
        attributes = this.attributes == null ? "" : this.attributes.stream().map(attr -> attr + ", ").reduce(attributes, String::concat);
        children = this.children == null ? "" : this.children.stream().map(node -> node.variable + ", ").reduce(children, String::concat);

        String msg = "Node [variable=" + this.variable + ", parent= " + (this.parent == null ? null : this.parent.getVariable())
                + ", type=" + this.type + ", value=" + value + ", \n\tattibutes=[" + attributes + "]"
                + ", \n\tchildren=[" + children + "]" + "]";
        return msg;
    }

}
