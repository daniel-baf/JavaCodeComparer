package Backend.Objects.Parsers;

import java.util.ArrayList;

import Backend.Objects.NodeTree.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class execute actions to parser, is used to have all code external to
 * CUP into a java file
 *
 * @author jefemayoneso
 */
public class ParserActioner {

    private TreeActioner tree;
    private int varsDeclared;
    private int classDeclared;
    private int methodsDeclared;
    private HashMap<String, Integer> varsTable;

    public ParserActioner() {
        this.tree = new TreeActioner();
        this.varsTable = new HashMap<>();
        this.varsTable.put("classVal1", 1);
    }

    /**
     * Set the root of a tree
     *
     * @param children
     */
    public void saveRoot(Object children) {
        try {
            Node parent = new Node("ROOT", "FILE");
            saveParent(parent, children);
            tree.setRoot(parent);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Save a node with his children, set children as null if doesn't exist
     *
     * @param parent
     * @param children
     */
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

    /**
     * Cast an Object to Array<Node>
     *
     * @param object the object to cast
     * @return
     */
    public ArrayList<Node> getItemAsArray(Object object) {
        try {
            if (object != null) {
                ArrayList<Node> array = new ArrayList<>();
                array.add((Node) object);
                return array;
            }
        } catch (Exception e) {
            System.out.println("ERROR GETTING ITEM AS ARRAY: " + e.getMessage());
        }
        return null;
    }

    /**
     * This method merge 2 arrays and return a single array with all info from
     * both arrays and remove possible nulls inserted
     *
     * @param array1
     * @param array2
     * @return
     */
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

    /**
     * Return an object Object with a Node element, but first adds an attribute
     * to the Object
     *
     * @param object    The Object to save
     * @param attribute The attribute to add to object
     * @return the same object with new attributes
     */
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

    /**
     * Create a new Node and cast to Object, send this new object to parser
     *
     * @param type     node type
     * @param variable node variable
     * @return new node
     */
    public Object getNode(String type, String variable) {
        try {
            return this.tree.createNode(type, variable);
        } catch (Exception e) {
            System.out.println("ERROR CREATING NODE: " + e.getMessage());
            return null;
        }

    }

    /**
     * Save a list with number of declarations of variables
     * 
     * @param variable
     */
    public void addVarToVarsTable(String variable) {
        try {
            // check if exists
            if (!this.varsTable.containsKey(variable)) {
                this.varsTable.put(variable, 1);
            } else {
                this.varsTable.put(variable, this.varsTable.get(variable) + 1);
            }
        } catch (Exception e) {
            System.out.println("ERROR ADDING VARIABLE TO VARS TABLE: " + e.getMessage());
        }
    }

    // GETTERS
    public TreeActioner getTree() {
        return this.tree;
    }

    public void increaseVarsDeclared(String variable) {
        this.varsDeclared++;
        addVarToVarsTable(variable);
    }

    public void increaseClassDeclared() {
        this.classDeclared++;
    }

    public void increaseMethodCounter() {
        this.methodsDeclared++;
    }

    public int getVarsDeclared() {
        return this.varsDeclared;
    }

    public int getClassDeclared() {
        return this.classDeclared;
    }

    public int getMethodsDeclared() {
        return this.methodsDeclared;
    }

    public HashMap<String, Integer> getVarsTable() {
        return this.varsTable;
    }

}
