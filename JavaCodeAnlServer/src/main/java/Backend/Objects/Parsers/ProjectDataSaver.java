package Backend.Objects.Parsers;

import java.util.ArrayList;

import Backend.Objects.NodeTree.Node;
import Backend.Objects.NodeTree.TreeActioner;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ProjectDataSaver {

    private int commentsCounter;
    private int variablesCounter;
    private int methodsCounter;
    private int classesCounter;
    private TreeActioner tree;
    private ArrayList<String> comments;
    private HashMap<String, Integer> variablesTable;

    public ProjectDataSaver(String name) {
        this(0, 0, 0, new TreeActioner(), new ArrayList<String>(), name);
    }

    public ProjectDataSaver(int variablesCounter, int methodsCounter, int classesCounter,
            TreeActioner tree, ArrayList<String> comments, String name) {
        this.variablesCounter = variablesCounter;
        this.methodsCounter = methodsCounter;
        this.classesCounter = classesCounter;
        this.tree = tree;
        this.comments = comments;
        this.commentsCounter = comments == null ? 0 : comments.size();
        this.variablesTable = new HashMap<>();
        // check tree is not empty
        this.tree.setRoot(this.tree.createNode("MASTER", name));
    }

    public void addChildren(Node... children) {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(Arrays.asList(children));
        if (this.tree.getRoot().getChildren() == null) {
            this.tree.getRoot().setChildren(nodes);
            this.tree.setParents(this.tree.getRoot(), nodes);
        } else {
            nodes.addAll(this.tree.getRoot().getChildren());
            this.tree.getRoot().setChildren(nodes);
        }

    }

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

    public void addMethodsCounter(int n) {
        this.methodsCounter += n;
    }

    public void addClassesCounter(int n) {
        this.classesCounter += n;
    }

    public void addVariablesCounter(int n) {
        this.variablesCounter += n;
    }

    public void addComments(ArrayList<String> comments) {
        // check null
        if (comments != null) {
            comments.removeAll(Collections.singleton(null)); // delete nulls
            this.comments.addAll(comments);
            this.commentsCounter = this.comments.size();
        }
    }

    public TreeActioner getTree() {
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
