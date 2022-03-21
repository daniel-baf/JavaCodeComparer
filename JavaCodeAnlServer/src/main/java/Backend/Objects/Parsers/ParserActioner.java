package Backend.Objects.Parsers;

import java.util.ArrayList;

import Backend.Objects.NodeTree.*;
import java.util.Collection;
import java.util.Collections;

public class ParserActioner {

    TreeActioner tree = new TreeActioner();

    public void saveRoot(Object children) {
        try {
            Node parent = new Node("ROOT", "FILE");
            saveParent(parent, children);
            tree.setRoot(parent);
            System.out.println("\n\n---- PRINT TREE ----");
            tree.printTree(parent, "ROOT");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void saveParent(Object parent, Object children) {
        try {
            Node parentNode = (Node) parent;
            ArrayList<Node> childrenNode = children != null ? (ArrayList<Node>) children : null;
            parentNode.setChildren(childrenNode);
            tree.setParents(parentNode, childrenNode);
        } catch (Exception e) {
            System.out.println("ERROR SAVING PARENT: " + e.getMessage());
        }
    }

    public ArrayList<Node> getArray(Object array1, Object array2) {
        try {
            ArrayList<Node> array = array1 != null ? (ArrayList<Node>) array1 : new ArrayList<>();
            if (array2 != null) {
                array.addAll((Collection<? extends Node>) array2);
            }
            array.removeAll(Collections.singleton(null));
            return array;
        } catch (Exception e) {
            System.out.println("ERROR GETTING ARRAY: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Node> getItemAsArray(Object object) {
        try {
            ArrayList<Node> array = new ArrayList<>();
            if (object != null) {
                array.add((Node) object);
                return array;
            }
        } catch (Exception e) {
            System.out.println("ERROR GETTING ITEM AS ARRAY: " + e.getMessage());
        }
        return null;
    }

    public Object addAttrNode(Object object, String... attribute) {
        try {
            if (object != null) {
                Node node = (Node) object;
                node.addAttributes(attribute);
                return node;
            }
        } catch (Exception e) {
            System.out.println("ERROR ADDING ATTRIBUTE: " + e.getMessage());
        }
        return null;
    }

    public Object getNode(String type, String variable) {
        try {
            return this.tree.createNode(type, variable);
        } catch (Exception e) {
            System.out.println("ERROR CREATING NODE: " + e.getMessage());
            return null;
        }

    }
}
