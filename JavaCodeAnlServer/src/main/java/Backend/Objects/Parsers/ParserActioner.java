package Backend.Objects.Parsers;

import Utilities.Tree.JavaData;
import Utilities.Tree.Node;
import Utilities.Tree.DerivationTree;
import java.util.ArrayList;

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

    private final DerivationTree<JavaData> tree;
    private int varsDeclared;
    private int classDeclared;
    private int methodsDeclared;
    private final HashMap<String, Integer> varsTable;
    private final HashMap<String, Integer> classTable;
    private final HashMap<String, Integer> methodTable;

    public ParserActioner() {
        this.tree = new DerivationTree();
        this.varsTable = new HashMap<>();
        this.classTable = new HashMap<>();
        this.methodTable = new HashMap<>();
    }

    /**
     * Set the root of a tree
     *
     * @param children
     */
    public void saveRoot(Object children) {
        try {
            Node<JavaData> parent = new Node<JavaData>(new JavaData("ROOT", "FILE"));
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
            Node<JavaData> parentNode = (Node<JavaData>) parent;
            ArrayList<Node<JavaData>> childrenNode = children != null ? (ArrayList<Node<JavaData>>) children : null;
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
    public ArrayList<Node<JavaData>> getItemAsArray(Object object) {
        try {
            if (object != null) {
                ArrayList<Node<JavaData>> array = new ArrayList<>();
                array.add((Node<JavaData>) object);
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
    public ArrayList<Node<JavaData>> getArray(Object array1, Object array2) {
        try {
            ArrayList<Node<JavaData>> array = array1 != null ? (ArrayList<Node<JavaData>>) array1 : new ArrayList<>();
            if (array2 != null) {
                array.addAll((Collection<? extends Node<JavaData>>) array2);
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
     * @param object The Object to save
     * @param attribute The attribute to add to object
     * @return the same object with new attributes
     */
    public Object addAttrNode(Object object, String... attribute) {
        try {
            if (object != null) {
                Node<JavaData> node = (Node<JavaData>) object;
                node.getData().addAttributes(attribute);
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
     * @param type node type
     * @param variable node variable
     * @return new node
     */
    public Object getNode(String type, String variable) {
        try {
            return new Node<>(new JavaData(variable, type));
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
    public void addVarToVarsTable(String variable, HashMap<String, Integer> table) {
        try {
            // check if exists
            if (!table.containsKey(variable)) {
                table.put(variable, 1);
            } else {
                table.put(variable, table.get(variable) + 1);
            }
        } catch (Exception e) {
            System.out.println("ERROR ADDING VARIABLE TO VARS TABLE: " + e.getMessage());
        }
    }

    // GETTERS
    public void increaseVarsDeclared(String variable) {
        this.varsDeclared++;
        addVarToVarsTable(variable, this.varsTable);
    }

    public void increaseClassDeclared(String classDecl) {
        this.classDeclared++;
        addVarToVarsTable(classDecl, this.classTable);

    }

    public void increaseMethodDeclared(String methodDecl) {
        this.methodsDeclared++;
        addVarToVarsTable(methodDecl, methodTable);
    }

    public DerivationTree<JavaData> getTree() {
        return tree;
    }

    public int getVarsDeclared() {
        return varsDeclared;
    }

    public int getClassDeclared() {
        return classDeclared;
    }

    public int getMethodsDeclared() {
        return methodsDeclared;
    }

    public HashMap<String, Integer> getVarsTable() {
        return this.varsTable;
    }

    public HashMap<String, Integer> getClassTable() {
        return classTable;
    }

    public HashMap<String, Integer> getMethodTable() {
        return methodTable;
    }

}
