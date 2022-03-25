/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Tree;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author jefemayoneso
 */
public class CommonData<T> {

    T data;
    ArrayList<Node<T>> matchedNodes;
    Node<T> parent;

    public CommonData() {
    }

    public CommonData(T data, ArrayList<Node<T>> matchedNodes) {
        this.data = data;
        this.matchedNodes = matchedNodes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ArrayList<Node<T>> getMatchedNodes() {
        return matchedNodes;
    }

    public void setMatchedNodes(ArrayList<Node<T>> matchedNodes) {
        this.matchedNodes = matchedNodes;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.data);
        return hash;
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
        final CommonData<?> other = (CommonData<?>) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

}
