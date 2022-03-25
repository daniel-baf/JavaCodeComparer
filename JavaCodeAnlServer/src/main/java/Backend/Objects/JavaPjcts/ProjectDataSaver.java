package Backend.Objects.JavaPjcts;

import Backend.Objects.AnalysisError;
import Utilities.Tree.JavaData;
import java.util.ArrayList;

import Utilities.Tree.Node;
import Utilities.Tree.DerivationTree;
import java.util.Collections;
import java.util.HashMap;

public class ProjectDataSaver {

    private int commentsCounter;
    private int variablesCounter;
    private int methodsCounter;
    private int classesCounter;
    private String name;
    private DerivationTree<JavaData> tree;
    private ArrayList<String> comments;
    private HashMap<String, Integer> classesTable;
    private HashMap<String, Integer> variablesTable;
    private HashMap<String, Integer> methodTable;
    private HashMap<String, Integer> commentsTable;
    private ArrayList<AnalysisError> errors;

    // CONSTRUCTORS
    public ProjectDataSaver(String name) {
        this(0, 0, 0, new DerivationTree<JavaData>(), new ArrayList<String>(), name);
    }

    public ProjectDataSaver(int variablesCounter, int methodsCounter, int classesCounter,
            DerivationTree<JavaData> tree, ArrayList<String> comments, String name) {
        this.variablesCounter = variablesCounter;
        this.methodsCounter = methodsCounter;
        this.classesCounter = classesCounter;
        this.tree = tree;
        this.comments = comments;
        this.commentsCounter = comments == null ? 0 : comments.size();
        this.variablesTable = this.classesTable = this.methodTable = this.commentsTable = new HashMap<>();
        this.errors = new ArrayList<>();
        // check tree is not empty
        this.name = name;
        this.tree.setRoot(this.tree.createNode(new JavaData("MASTER", name)));
    }

    /**
     * Add a tree son to master tree, the added tree (new child) is a tree that
     * belongs to a java file
     *
     * @param children
     */
    public void addChild(Node<JavaData> child) {
        ArrayList<Node<JavaData>> nodes = new ArrayList<>();
        nodes.add(child);
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
    private HashMap<String, Integer> addDataRowsToHash(HashMap<String, Integer> data, HashMap<String, Integer> table) {
        // check is not null
        HashMap<String, Integer> newHash = new HashMap<>();
        if (table != null) {
            newHash.putAll(table);
        }
        if (data != null) {
            data.keySet().forEach(name -> {
                if (newHash.containsKey(name)) {
                    newHash.put(name, data.get(name) + newHash.get(name));
                } else {
                    newHash.put(name, data.get(name));
                }
            });
        }
        return newHash;

    }

    public void addTableHash(HashMap<String, Integer> hash, int table) {
        switch (table) {
            case 1 ->
                this.variablesTable = addDataRowsToHash(hash, this.variablesTable);
            case 2 ->
                this.classesTable = addDataRowsToHash(hash, this.classesTable);
            case 3 ->
                this.methodTable = addDataRowsToHash(hash, this.methodTable);
            default ->
                this.commentsTable = addDataRowsToHash(hash, this.commentsTable);
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
            this.comments.addAll(comments);
            this.commentsCounter = this.comments.size();
            this.comments.removeAll(Collections.singleton(null)); // delete nulls
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

    public DerivationTree<JavaData> getTree() {
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
        return getTimesDeclaredTable(this.variablesTable, key);
    }

    public int getTimesClassDeclared(String key) {
        return getTimesDeclaredTable(this.classesTable, key);
    }

    public int getTimesMethodDeclared(String key) {
        return getTimesDeclaredTable(this.methodTable, key);
    }

    public int getTimesCommentDeclared(String key) {
        return getTimesDeclaredTable(this.commentsTable, key);
    }

    private int getTimesDeclaredTable(HashMap<String, Integer> hash, String key) {
        try {
            return hash.get(key);
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<String> getComments() {
        return this.comments;
    }

    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<AnalysisError> errors) {
        this.errors = errors;
    }

    public String getName() {
        return name;
    }

}
