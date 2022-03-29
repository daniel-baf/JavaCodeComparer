package Backend.Objects.Parsers.JSONData;

import Backend.Objects.AnalysisError;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JSONActioner
 */
public class JSONActioner {

    private final JSONDataSaver data;
    private final HashMap<String, Integer> attributesDeclared;
    private final ArrayList<AnalysisError> errors;

    // CONSTRUCTORS
    public JSONActioner() {
        this.data = new JSONDataSaver();
        this.attributesDeclared = new HashMap<>();
        this.errors = new ArrayList<>();
    }

    /**
     * Save the score to JSONDataSaver
     *
     * @param score
     */
    public void saveScore(String score) {
        try {
            this.data.setScore(Double.valueOf(score.replaceAll("\"", "")));
        } catch (NumberFormatException e) {
            addToHash("error");
            System.out.println("Error setting score");
        }
    }

    /**
     * Save class to JSONDataSaver
     *
     * @param classN
     */
    public void saveClass(String classN) {
        try {
            this.data.getClasses().add(classN.replaceAll("\"", ""));
        } catch (Exception e) {
            addToHash("error");
            System.out.println("Error setting class name");
        }
    }

    /**
     * Save a variable to JSONDataSaver
     *
     * @param name
     * @param type
     * @param functions
     */
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
                    .add(new JSONVariable(name.replaceAll("\"", ""), type.replaceAll("\"", ""), functionsSplitted));
        } catch (Exception e) {
            addToHash("error");
            System.out.println("Error saving variable " + e.getMessage());
        }
    }

    /**
     * Save a method to JSONDataSaver
     *
     * @param name
     * @param type
     * @param parameters
     */
    public void saveMethod(String name, String type, String parameters) {
        try {
            // cast double to int
            this.data.getMethods().add(new JSONMethod(name.replaceAll("\"", ""), type.replaceAll("\"", ""), Integer.valueOf(parameters)));
        } catch (NumberFormatException e) {
            addToHash("error");
            System.out.println("Error saving function " + e.getMessage());
        }
    }

    /**
     * Save a comment to JSONDataSaver
     *
     * @param comment
     */
    public void saveComment(String comment) {
        try {
            this.data.getComments().add(comment.substring(1, comment.length() - 1));
        } catch (Exception e) {
            addToHash("error");
            System.out.println("Error saving comment: " + e.getMessage());
        }
    }

    /**
     * Add a key,value, is used to count the errors, the classes declarations...
     *
     * @param key
     */
    public void addToHash(String key) {
        key = key.toLowerCase();
        if (this.attributesDeclared.containsKey(key)) {
            this.attributesDeclared.put(key, this.attributesDeclared.get(key) + 1);
        } else {
            this.attributesDeclared.put(key, 1);
        }
    }

    /**
     * Save error to be displayed later
     *
     * @param line
     * @param column
     * @param lexeme
     * @param expectedSymbols
     */
    public void addError(int line, int column, String lexeme, ArrayList<String> expectedSymbols) {
        this.errors.add(new AnalysisError(line, column, lexeme, "JSON", "PROYECTO COPY", "SINTACTICO", expectedSymbols));
    }

    /**
     * Check if the generated JSON contains all the basic data and there is no
     * more or less than 1 attribute declared
     *
     * @return
     */
    public boolean isOk() {
        // check just 1 comment is declared...
        this.attributesDeclared.keySet().stream().filter(name -> (this.attributesDeclared.get(name) != 1 && !name.equals("error"))).forEachOrdered(name -> {
            this.errors.add(new AnalysisError(-1, -1, "Debe haber 1 declaracion de " + name, "JSON", "PRYECTO COPY", "SEMANTICO", new ArrayList<>()));
        });
        return this.errors.isEmpty();
    }

    // GETTERS AND SESTTERS
    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    public JSONDataSaver getData() {
        return data;
    }

}
