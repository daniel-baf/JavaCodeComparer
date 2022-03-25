package Backend.Objects.JavaPjcts;

import Utilities.Tree.CommonData;
import Utilities.Tree.JavaData;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This method calculate the result of projects
 *
 * @author jefemayoneso
 */
public class ProjectScoreCalculator {

    private ProjectDataSaver project1;
    private ProjectDataSaver project2;
    private ArrayList<CommonData<JavaData>> commonNodes;
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
    HashMap<String, Integer> timesCounted = new HashMap<>();

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
        System.out.println("Val: " + this.project1.getVarsDeclared());
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
            this.commonNodes.forEach(commonData -> {
                switch (commonData.getData().getType()) {
                    case "CLASS" -> {
                        addToHash("CLASS: " + commonData.getData().getVariable());
                        if (getFromHash("CLASS: " + commonData.getData().getVariable()) <= 1) {
                            this.repeatedClasses += this.project1
                                    .getTimesClassDeclared(commonData.getData().getVariable())
                                    + this.project2.getTimesClassDeclared(commonData.getData().getVariable());
                        }
                    }
                    default ->
                        setMethodOrVariable(commonData.getData());
                }
            });
        }
    }

    private void setMethodOrVariable(JavaData par) {
        String item = par.isIsMethod() ? "METH: " + par.getVariable() : "VAR: " + par.getVariable();
        addToHash(item);
        if (getFromHash(item) <= 1) {
            if (par.isIsMethod()) {
                this.repeatedMethods += this.project1.getTimesMethodDeclared(par.getVariable())
                        + this.project2.getTimesMethodDeclared(par.getVariable());
            } else {
                this.repeatedVariables += this.project1.getTimesVarDeclared(par.getVariable())
                        + this.project2.getTimesVarDeclared(par.getVariable());
            }
        }
    }

    /**
     * Check all comments from project 1 and search for equal comments at
     * project 2, if it is found, add it to a list CommonComments
     */
    private void searchCommonComments() {
        // move between comments
        project1.getComments().forEach(comment -> {
            // check times delcared the comment with hashmap
            if (this.project2.getTimesCommentDeclared(comment) > 0) {
                this.commonComments.add(comment);
                this.repeatedComments += this.project1.getTimesCommentDeclared(comment)
                        + this.project2.getTimesCommentDeclared(comment);
            }
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
        // totalRepeated / ( SUM decls p1 & p2 ) * 0.25 -> comments, variables, methods,
        // classes
        double valsDiv = this.totalVariablesDeclared == 0 ? 1.0 : this.totalVariablesDeclared;
        double commDiv = this.totalCommentsDeclared == 0 ? 1.0 : this.totalCommentsDeclared;
        double methDiv = this.totalMethodsDeclared == 0 ? 1.0 : this.totalMethodsDeclared;
        double classDiv = this.totalClassDeclared == 0 ? 1.0 : this.totalClassDeclared;
        this.score = (((this.repeatedVariables / valsDiv) + (this.repeatedComments / commDiv)
                + (this.repeatedMethods / methDiv) + (this.repeatedClasses / classDiv)) * 0.25);
        this.score = Math.round(this.score * 100.00) / 100.00;
    }

    public void addToHash(String key) {
        if (this.timesCounted.containsKey(key)) {
            this.timesCounted.put(key, this.timesCounted.get(key) + 1);
        } else {
            this.timesCounted.put(key, 1);
        }
    }

    public int getFromHash(String key) {
        try {
            return this.timesCounted.get(key);
        } catch (Exception e) {
            return 0;
        }
    }

    // GETTRES AND SETTERS
    public Double getScore() {
        return this.score;
    }

    public ArrayList<String> getCommonComments() {
        return this.commonComments;
    }

    public ArrayList<CommonData<JavaData>> getCommonNodes() {
        return commonNodes;
    }

    public boolean calcResults() {
        if (this.project1.getErrors().size() > 0 || this.project2.getErrors().size() > 0) {
            return false;
        } else {
            searchCommonNodes();
            return true;
        }
    }

    public int getTotalVariablesDeclared() {
        return totalVariablesDeclared;
    }

}
