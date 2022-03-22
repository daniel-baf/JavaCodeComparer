package Backend.Objects.JavaPjcts;

import java.util.ArrayList;

import Backend.Objects.NodeTree.Node;
import Backend.Objects.Parsers.ProjectDataSaver;

/**
 * This method calculate the result of projects
 *
 * @author jefemayoneso
 */
public class ProjectCopyCalcultator {

    private ProjectDataSaver project1;
    private ProjectDataSaver project2;
    private ArrayList<Node[]> commonNodes;
    private int totalCommentsDeclared;
    private int totalVariablesDeclared;
    private int totalMethodsDeclared;
    private int totalClassDeclared;
    private int repeatedComments;
    private int repeatedVariables;
    private int repeatedMethods;
    private int repeatedClasses;
    private Double score;

    public ProjectCopyCalcultator() {
        this(null, null);
    }

    public ProjectCopyCalcultator(ProjectDataSaver project1, ProjectDataSaver project2) {
        this.project1 = project1;
        this.project2 = project2;
        this.commonNodes = null;
        this.totalClassDeclared = this.project1.getClassDeclared() + this.project2.getClassDeclared();
        this.totalCommentsDeclared = this.project1.getCommentsDeclared() + this.project2.getCommentsDeclared();
        this.totalMethodsDeclared = this.project1.getMethodsDeclared() + this.project2.getMethodsDeclared();
        this.totalVariablesDeclared = this.project1.getVarsDeclared() + this.project2.getVarsDeclared();
        // repeated = 0 until analyzis executed
        this.repeatedClasses = this.repeatedComments = this.repeatedMethods = this.repeatedVariables = 0;
        this.score = 0.0000;
    }

    public void printTreeProjects() {
        System.out.println("----- PROJECT 1 TREE -----");
        this.project1.getTree().printTree(this.project1.getTree().getRoot(), "");
        System.out.println("----- PROJECT 2 TREE -----");
        this.project2.getTree().printTree(this.project2.getTree().getRoot(), "");
    }

    public void searchCommonNodes() {
        // search common methods, variables and classes
        this.commonNodes = this.project1.getTree().searchCommons(this.project2.getTree().getRoot());
        increaseVariablesCommon();
        // search common comments
        searchCommonComments();
        calcPoints();
    }

    private void increaseVariablesCommon() {
        this.commonNodes.forEach(commonNode -> {
            switch (commonNode[0].getType()) {
                case "CLASS" ->
                    this.repeatedClasses++;
                case "METHOD" ->
                    this.repeatedMethods++;
                default ->
                    this.repeatedVariables++;
            }
        });
    }

    private void searchCommonComments() {
        // move between comments
        if (this.project1.getCommentsDeclared() == 0 || this.project2.getCommentsDeclared() == 0) {
            // do nothing, no comments match
        } else {
            this.project1.getComments().forEach(comment -> {
                this.project2.getComments().stream().filter(comment2 -> (comment.equalsIgnoreCase(comment2))).forEachOrdered(_item -> {
                    // TODO add comment and show to JSON
                    this.repeatedComments++;
                });
            });
        }
    }

    public void printCommonNodes() {
        System.out.println("---- PRINT COMMON NODES ----");
        if (this.commonNodes != null) {
            this.commonNodes.forEach(nodes -> {
                System.out.println("EQ: \n\t" + nodes[0].toString() + "\n\t" + nodes[1].toString());
            });
        }

        System.out.println("DATA:");
        System.out.println("\tVARIABLES= D:" + this.totalVariablesDeclared + " R: " + this.repeatedVariables);
        System.out.println("\tMETHODS= D: " + this.totalMethodsDeclared + " R: " + this.repeatedMethods);
        System.out.println("\tCLASSES= D: " + this.totalClassDeclared + " R: " + this.repeatedClasses);
        System.out.println("\tCOMMENTS= D: " + this.totalCommentsDeclared + " R: " + this.repeatedComments);
        System.out.println("SCORE: " + this.score);

    }

    public void calcPoints() {
        // totalRepeated / ( SUM decls p1 & p2 ) * 0.25 -> comments, variables, methods, classes
        double valsDiv = this.totalVariablesDeclared == 0 ? 1.0 : this.totalVariablesDeclared;
        double commDiv = this.totalCommentsDeclared == 0 ? 1.0 : this.totalCommentsDeclared;
        double methDiv = this.totalMethodsDeclared == 0 ? 1.0 : this.totalMethodsDeclared;
        double classDiv = this.totalClassDeclared == 0 ? 1.0 : this.totalClassDeclared;
        this.score = (((this.repeatedVariables / valsDiv) + (this.repeatedComments / commDiv) + (this.repeatedMethods / methDiv) + (this.repeatedClasses / classDiv)) * 0.25);
    }

}
