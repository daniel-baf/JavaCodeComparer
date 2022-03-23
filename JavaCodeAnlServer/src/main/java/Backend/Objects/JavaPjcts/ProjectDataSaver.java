package Backend.Objects.JavaPjcts;

import java.util.ArrayList;

import Utilities.Tree.Node;
import Utilities.Tree.DerivationTree;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ProjectDataSaver {

    private int commentsCounter;
    private int variablesCounter;
    private int methodsCounter;
    private int classesCounter;
    private DerivationTree<JavaData> tree;
    private ArrayList<String> comments;
    private HashMap<String, Integer> variablesTable;

    // CONSTRUCTORS
    public ProjectDataSaver(String name) {
        this(0, 0, 0, new DerivationTree(), new ArrayList<String>(), name);
    }

    public ProjectDataSaver(int variablesCounter, int methodsCounter, int classesCounter,
            DerivationTree tree, ArrayList<String> comments, String name) {
        this.variablesCounter = variablesCounter;
        this.methodsCounter = methodsCounter;
        this.classesCounter = classesCounter;
        this.tree = tree;
        this.comments = comments;
        this.commentsCounter = comments == null ? 0 : comments.size();
        this.variablesTable = new HashMap<>();
        // check tree is not empty
        this.tree.setRoot(this.tree.createNode(new JavaData("MASTER", name)));
    }

    /**
     * Add a tree son to master tree, the added tree (new child) is a tree that
     * belongs to a java file
     *
     * @param children
     */
    public void addChildren(Node<JavaData>... children) {
        ArrayList<Node<JavaData>> nodes = new ArrayList<>();
        nodes.addAll(Arrays.asList(children));
        if (this.tree.getRoot().getChildren() == null) {
            this.tree.getRoot().setChildren(nodes);
            this.tree.setParents(this.tree.getRoot(), nodes);
        } else {
            nodes.addAll(this.tree.getRoot().getChildren());
            this.tree.getRoot().setChildren(nodes);
        }

    }

    /**
     * Add the number of times a variable is declared
     *
     * @param variables
     */
    public void addVariablesDeclaredCounter(HashMap<String, Integer> variables) {
        // check is not null
        if (variables != null) {
            variables.forEach((key, value) -> {
                if (this.variablesTable.containsKey(key)) {
                    this.variablesTable.put(key, this.variablesTable.get(key) + value);
                } else {
                    this.variablesTable.put(key, value);
                }
            });
        }
    }

    /**
     * Save a comment to the list and remove possible nulls or empty comments
     *
     * @param comments
     */
    public void addComments(ArrayList<String> comments) {
        // check null
        if (comments != null) {
            comments.removeAll(Collections.singleton(null)); // delete nulls
            this.comments.addAll(comments);
            this.commentsCounter = this.comments.size();
        }
    }

    // GETTERS AND SETTERS
    public void addMethodsCounter(int n) {
        this.methodsCounter += n;
    }

    public void addClassesCounter(int n) {
        this.classesCounter += n;
    }

    public void addVariablesCounter(int n) {
        this.variablesCounter += n;
    }

    public DerivationTree getTree() {
        return tree;
    }

    public int getVarsDeclared() {
        return this.variablesCounter;
    }

    public int getClassDeclared() {
        return this.classesCounter;
    }

    public int getCommentsDeclared() {
        return this.commentsCounter;
    }

    public int getMethodsDeclared() {
        return this.methodsCounter;
    }

    public int getTimesVarDeclared(String key) {
        System.out.println("Trying to get: " + key + " contains: " + this.variablesTable.containsKey(key));
        return this.variablesTable.get(key);
    }

    public ArrayList<String> getComments() {
        return this.comments;
    }
}
