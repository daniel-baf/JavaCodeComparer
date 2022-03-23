package Backend.Objects.JavaPjcts;

import java.util.ArrayList;

import Utilities.Tree.Node;

/**
 * This method calculate the result of projects
 *
 * @author jefemayoneso
 */
public class ProjectScoreCalculator {

    private ProjectDataSaver project1;
    private ProjectDataSaver project2;
    private ArrayList<Node<JavaData>[]> commonNodes;
    private ArrayList<String> commonComments;
    private int totalCommentsDeclared;
    private int totalVariablesDeclared;
    private int totalMethodsDeclared;
    private int totalClassDeclared;
    private int repeatedComments;
    private int repeatedVariables;
    private int repeatedMethods;
    private int repeatedClasses;
    private Double score;

    // CONSTRUCTORS
    public ProjectScoreCalculator() {
        this(null, null);
    }

    public ProjectScoreCalculator(ProjectDataSaver project1, ProjectDataSaver project2) {
        this.project1 = project1;
        this.project2 = project2;
        this.totalClassDeclared = this.project1.getClassDeclared() + this.project2.getClassDeclared();
        this.totalCommentsDeclared = this.project1.getCommentsDeclared() + this.project2.getCommentsDeclared();
        this.totalMethodsDeclared = this.project1.getMethodsDeclared() + this.project2.getMethodsDeclared();
        this.totalVariablesDeclared = this.project1.getVarsDeclared() + this.project2.getVarsDeclared();
        // repeated = 0 until analyzis executed
        this.repeatedClasses = this.repeatedComments = this.repeatedMethods = this.repeatedVariables = 0;
        this.commonComments = new ArrayList<>();
        this.score = 0.0000;
    }

    public void printTreeProjects() {
        System.out.println("---- TREE 1 ----");
        this.project1.getTree().print();
        System.out.println("---- TREE 2 ----");
        this.project2.getTree().print();
    }

    /**
     * Search for common Tree Nodes in project 1 master tree and project 2
     * master tree
     */
    public void searchCommonNodes() {
        // search common methods, variables and classes
        this.commonNodes = this.project1.getTree().searchCommons(this.project2.getTree().getRoot());
        increaseVariablesCommon();
        // search common comments
        searchCommonComments();
        calcPoints();
    }

    /**
     * Increase the number of classes, methods, or variables repeated are
     * declared
     */
    private void increaseVariablesCommon() {
        if (this.commonNodes != null) {
            this.commonNodes.forEach(commonNode -> {
                switch (commonNode[0].getData().getType()) {
                    case "CLASS" ->
                        this.repeatedClasses += this.project1.getTimesClassDeclared(commonNode[0].getData().getVariable()) + this.project2.getTimesClassDeclared(commonNode[0].getData().getVariable());
                    case "METHOD" ->
                        this.repeatedMethods += this.project1.getTimesMethodDeclared(commonNode[0].getData().getVariable()) + this.project2.getTimesMethodDeclared(commonNode[0].getData().getVariable());
                    default ->
                        this.repeatedVariables += this.project1.getTimesVarDeclared(commonNode[0].getData().getVariable()) + this.project2.getTimesVarDeclared(commonNode[0].getData().getVariable());
                }
            });
        }
    }

    /**
     * Check all comments from project 1 and search for equal comments at
     * project 2, if it is found, add it to a list CommonComments
     */
    private void searchCommonComments() {
        // move between comments
        project1.getComments().forEach(comment -> {
            project2.getComments().stream().filter(comment1 -> (comment.equalsIgnoreCase(comment1))).map(_item -> {
                this.commonComments.add(comment);
                return _item;
            }).forEachOrdered(_item -> {
                this.repeatedComments += this.project1.getTimesCommentDeclared(_item) + this.project2.getTimesCommentDeclared(_item);
            });
        });
    }

    /**
     * Calculate the copy score with SubScore = REP_DECS / SUM_DEC * 0.25, same
     * formula for comments, variables, methods and classes
     *
     * TOTAL_SCORE = SUM(subScore) REP_DECS=total times repeated ITEM is
     * declared, SUM_DEC = total times ITEM is declared
     *
     * ITEM = class, method, comment, variable
     */
    public void calcPoints() {
        System.out.println("DATA: ");
        System.out.println("COMMENTS: T:" + this.totalCommentsDeclared + " R:" + this.repeatedComments);
        System.out.println("CLASSES: T" + this.totalClassDeclared + " R:" + this.repeatedClasses);
        System.out.println("METHODS: T:" + this.totalMethodsDeclared + " R:" + this.repeatedMethods);
        System.out.println("VARIAB: T:" + this.totalVariablesDeclared + " R:" + this.repeatedVariables);

        // totalRepeated / ( SUM decls p1 & p2 ) * 0.25 -> comments, variables, methods, classes
        double valsDiv = this.totalVariablesDeclared == 0 ? 1.0 : this.totalVariablesDeclared;
        double commDiv = this.totalCommentsDeclared == 0 ? 1.0 : this.totalCommentsDeclared;
        double methDiv = this.totalMethodsDeclared == 0 ? 1.0 : this.totalMethodsDeclared;
        double classDiv = this.totalClassDeclared == 0 ? 1.0 : this.totalClassDeclared;
        this.score = (((this.repeatedVariables / valsDiv) + (this.repeatedComments / commDiv) + (this.repeatedMethods / methDiv) + (this.repeatedClasses / classDiv)) * 0.25);
        this.score = Math.round(this.score * 100.00) / 100.00;
    }

    // GETTRES AND SETTERS
    public Double getScore() {
        return this.score;
    }

    public ArrayList<String> getCommonComments() {
        return this.commonComments;
    }

    public ArrayList<Node<JavaData>[]> getCommonNodes() {
        return this.commonNodes;
    }

}
