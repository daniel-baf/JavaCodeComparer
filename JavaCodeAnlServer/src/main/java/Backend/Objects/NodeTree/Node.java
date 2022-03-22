package Backend.Objects.NodeTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a derivation tree node, save the variable, the type,
 * the parent, the attributes and children
 *
 * @author jefemayoneso
 */
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

    public ArrayList<String[]> getGrandpa() {
        ArrayList<String[]> parents = new ArrayList<>();
        getGrandpa(parents);
        return parents != null && parents.size() > 0 ? parents : null;
    }

    /**
     * Return an arrayList with all parents and grandparents, the ArrayList
     * contains a String[2] where String[0]=GRANDPA TYPE & String[1] = GRANDPA
     * VARIABLE
     *
     * @return
     */
    private void getGrandpa(ArrayList<String[]> parents) {
        if (this.parent != null) {
            parents.add(new String[]{this.parent.type, this.parent.variable});
            this.parent.getGrandpa(parents);
        }
    }

    @Override
    public String toString() {
        String attributesString = "";
        String childrenString = "";
        attributesString = this.attributes == null ? ""
                : this.attributes.stream().map(attr -> attr + ", ").reduce(attributesString, String::concat);
        childrenString = this.children == null ? ""
                : this.children.stream().map(node -> node.variable + ", ").reduce(childrenString, String::concat);

        String msg = "Node [variable=" + this.variable + ", parent= "
                + (this.parent == null ? null : this.parent.getVariable())
                + ", type=" + this.type + ", value=" + value + ", tattibutes=[" + attributesString + "]" + ", children=["
                + childrenString + "]" + "]";
        return msg;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        // check name
        if (!variable.equals(other.variable)) {
            return false;
        } else { // same name, check type
            if ("FILE".equals(type) || "MASTER".equals(type)) {
                return false;
            } else if ("FILE".equals(other.type) || "MASTER".equals(other.type)) {
                return false;
            }
            if (!type.equals(other.type)) { // no same type
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.variable);
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
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
        this.attributes.addAll(Arrays.asList(attributes));
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

}
