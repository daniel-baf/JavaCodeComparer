package Utilities.Tree;

import java.util.ArrayList;

import Backend.Objects.JavaPjcts.JavaData;

/**
 * This class represents a derivation tree node, save the variable, the type,
 * the parent, the attributes and children
 *
 * @author jefemayoneso
 */
public class Node<T> {

    // id
    private Node<T> parent;
    private ArrayList<Node<T>> children;
    private T data;

    public Node() {
        this(null, null);
    }

    public Node(T data) {
        this(data, null);
    }

    public Node(T data, Node parent) {
        this.parent = parent;
        this.data = data;
        this.children = null;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public ArrayList<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node<T>> children) {
        this.children = children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        int childrens = this.children == null ? 0 : this.children.size();
        String datak = this.data == null ? null : this.data.toString();
        String parentk = null;
        try {
            Node<JavaData> p = (Node<JavaData>) this;
            parentk = p.data.getVariable();
        } catch (Exception e) {
        }

        return "Node{" + "parent=" + parentk + ", childrens=" + childrens + ", data=" + datak + '}';
    }

}
