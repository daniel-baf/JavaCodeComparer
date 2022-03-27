package Backend.Objects.Parsers.JSONData;

import java.util.ArrayList;

/**
 * JSONActioner
 */
public class JSONActioner {

    private final JSONDataSaver data;
    private int errorsCount;

    public JSONActioner() {
        this.data = new JSONDataSaver();
        this.errorsCount = 0;
    }

    public void saveScore(String score) {
        try {
            this.data.setScore(Double.valueOf(score.replaceAll("\"", "")));
        } catch (NumberFormatException e) {
            this.errorsCount++;
            System.out.println("Error setting score");
        }
    }

    public void saveClass(String classN) {
        try {
            this.data.getClasses().add(classN.replaceAll("\"", ""));
        } catch (Exception e) {
            this.errorsCount++;
            System.out.println("Error setting class name");
        }
    }

    public void printData() {
        try {
            System.out.println(this.data.toString());
        } catch (Exception e) {
            this.errorsCount++;
            System.out.println("Error printing data " + e.getMessage());
        }
    }

    public void saveVariable(String name, String type, String functions) {
        try {
            // get functions as array
            String[] funcs = functions.replaceAll("\"", "").split(","); // split {... data... }, {... data... }
            ArrayList<String[]> functionsSplitted = new ArrayList<>();
            for (String func : funcs) {
                String[] funcSplitted = func.trim().split(" "); // split "type name"
                functionsSplitted.add(funcSplitted);
            }
            // save data
            this.data.getVariables()
                    .add(new VariableJSON(name.replaceAll("\"", ""), type.replaceAll("\"", ""), functionsSplitted));
        } catch (Exception e) {
            this.errorsCount++;
            System.out.println("Error saving variable " + e.getMessage());
        }
    }

    public void saveMethod(String name, String type, String parameters) {
        try {
            // cast double to int
            this.data.getMethods().add(new JSONMethod(name.replaceAll("\"", ""), type.replaceAll("\"", ""), Integer.valueOf(parameters)));
        } catch (NumberFormatException e) {
            this.errorsCount++;
            System.out.println("Error saving function " + e.getMessage());
        }
    }

    public void saveComment(String comment) {
        try {
            this.data.getComments().add(comment.substring(1, comment.length() - 1));
        } catch (Exception e) {
            this.errorsCount++;
            System.out.println("Error saving comment: " + e.getMessage());
        }
    }

}
