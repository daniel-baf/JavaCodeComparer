package Backend.Objects.Parsers.JSONData;

import java.util.ArrayList;

/**
 * JSONDataSaver
 */
public class JSONDataSaver {

    private double score;
    private ArrayList<String> classes;
    private final ArrayList<JSONVariable> variables;
    private final ArrayList<JSONMethod> methods;
    private ArrayList<String> comments;

    public JSONDataSaver() {
        this.score = 0;
        this.classes = new ArrayList<>();
        this.variables = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public ArrayList<JSONVariable> getVariables() {
        return variables;
    }

    public ArrayList<JSONMethod> getMethods() {
        return methods;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "JSONDataSaver [\n\tclasses=" + classes + ", \n\tcomments=" + comments + ", \n\tmethods=" + methods + ", \n\tscore="
                + score + ", \n\tvariables=" + variables + "\n]";
    }
}
