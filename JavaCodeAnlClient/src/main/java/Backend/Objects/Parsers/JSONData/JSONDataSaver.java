package Backend.Objects.Parsers.JSONData;

import java.util.ArrayList;

/**
 * JSONDataSaver
 */
public class JSONDataSaver {

    private double score;
    private ArrayList<String> classes;
    private ArrayList<VariableJSON> variables;
    private ArrayList<JSONMethod> methods;
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

    public ArrayList<VariableJSON> getVariables() {
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

class VariableJSON {

    private String name;
    private String type;
    private ArrayList<String[]> functions;

    public VariableJSON(String name, String type, ArrayList<String[]> functions) {
        this.name = name;
        this.type = type;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String[]> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<String[]> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        String functPrint = "[";
        functPrint = functions.stream().map(strings -> "\n\t\t\t\t" + strings[0] + ": " + strings[1]).reduce(functPrint, String::concat);
        functPrint += "\n\t\t\t]";
        return "\n\t\t{\n\t\t\tfunctions=" + functPrint + ", \n\t\t\tname=" + name + ", \n\t\t\ttype=" + type + "\n\t\t}";
    }

}

class JSONMethod {

    private String name;
    private String type;
    private int parameters;

    public JSONMethod(String name, String type, int parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParameters() {
        return parameters;
    }

    public void setParameters(int parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "\n\t\t{" + "name=" + name + ", type=" + type + ", parameters=" + parameters + '}';
    }

}
