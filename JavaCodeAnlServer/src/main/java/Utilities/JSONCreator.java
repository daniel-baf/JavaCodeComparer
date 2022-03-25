/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Utilities.Tree.JavaData;
import Backend.Objects.JavaPjcts.ProjectScoreCalculator;
import Utilities.Tree.CommonData;
import Utilities.Tree.Node;
import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class JSONCreator {

    public JSONCreator() {
    }

    public void generateJSON(ProjectScoreCalculator pcc) {
        ArrayList<String> lines = getJSON(pcc);
        new FileActioner().writeFile("JSON", "copy.json", lines);
    }

    /**
     * Get the lines of a JSON FILE
     *
     * @param pcc
     * @return
     */
    private ArrayList<String> getJSON(ProjectScoreCalculator pcc) {
        ArrayList<String> lines = new ArrayList<>();
        // start JSON
        lines.add(startJSON());
        // set score
        lines.add(setScore(pcc.getScore()));
        // set data
        setData(lines, pcc.getCommonNodes(), pcc.getCommonComments());
        // close JSON
        lines.add(closeJSON());

        // print lines
        System.out.println("\n");
        lines.forEach(line -> {
            System.out.println(line);
        });
        return lines;
    }

    /**
     * Save the data inside the JSON file
     *
     * @param lines
     * @param nodes
     * @param comments
     */
    private void setData(ArrayList<String> lines, ArrayList<CommonData<JavaData>> nodes, ArrayList<String> comments) {
        // split data into classes, comments, and variables
        ArrayList<CommonData<JavaData>> classes = new ArrayList<>();
        ArrayList<CommonData<JavaData>> methods = new ArrayList<>();
        ArrayList<CommonData<JavaData>> variables = new ArrayList<>();
        // split data
        splitData(nodes, classes, methods, variables);
        // add lines
        addLinesWithData(lines, classes, methods, variables, comments);
    }

    /**
     * Split the data of an array into multiple arrays, an array for classes,
     * methods and other for variables
     *
     * @param nodes
     * @param classes
     * @param methods
     * @param variables
     */
    private void splitData(ArrayList<CommonData<JavaData>> nodes, ArrayList<CommonData<JavaData>> classes, ArrayList<CommonData<JavaData>> methods, ArrayList<CommonData<JavaData>> variables) {
        // split data
        if (nodes != null) {
            nodes.forEach(variable -> {
                switch (variable.getData().getType()) {
                    case "CLASS" ->
                        classes.add(variable);
                    default -> // variables
                    {
                        if (variable.getData().isIsMethod()) {
                            methods.add(variable);
                        } else {
                            variables.add(variable);
                        }
                    }
                }
            });
        }

    }

    /**
     * Add the lines of the data to the json
     *
     * @param lines the arraylist of the lines to add data
     * @param classes the list of classes repeated
     * @param methods the list of methods repeated
     * @param variables the list of variables repeated
     * @param comments the list of comments repeated
     */
    private void addLinesWithData(ArrayList<String> lines, ArrayList<CommonData<JavaData>> classes, ArrayList<CommonData<JavaData>> methods, ArrayList<CommonData<JavaData>> variables, ArrayList<String> comments) {
        // LINES
        String line = "";
        // save info of class
        lines.add("\tClases:[");
        line = String.format("\t\t%1$s", classes.stream().map(node -> String.format("{Nombre: \"%1$s\"},", node.getData().getVariable())).reduce(line, String::concat));
        line = line.substring(0, line.length() - 1); // remove last , 
        lines.add(line);
        lines.add("\t],");
        // save info of variables
        lines.add("\tVariables:[");

        variables.forEach(_item -> {
            String prev = "CLASS".equals(_item.getParent().getData().getType()) ? "Clase " + _item.getParent().getData().getVariable() : "Metodo " + _item.getParent().getData().getVariable() + ", ";
            String foundNodes = "";
            String withComma = variables.get(variables.size() - 1).getData() == _item.getData() ? "" : ", ";
            foundNodes = _item.getMatchedNodes().stream().map(matchedNode -> "CLASS".equals(matchedNode.getParent().getData().getType()) ? ", Clase: " + matchedNode.getParent().getData().getVariable() : ", Metodo" + matchedNode.getParent().getData().getVariable()).reduce(foundNodes, String::concat);
            lines.add(String.format("\t\t{Nombre: \"%1$s\", Tipo:\"%2$s\", Funcion: \"%3$s\"}%4$s", _item.getData().getVariable(), _item.getData().getType(), prev + foundNodes, withComma));
        });
        lines.add("\t],");
        // save info methods
        lines.add("\tMetodos:[");
        methods.forEach(_item -> {
            String withComma = methods.get(methods.size() - 1).getData() == _item.getData() ? "" : ", ";
            lines.add(String.format("\t\t{Nombre: \"%1$s\", Tipo: \"%2$s\", Parametros: %3$x}%4$s", _item.getData().getVariable(), _item.getData().getType(), _item.getData().getParametersSize(), withComma)); // TODO check this
        });
        lines.add("\t],");
        // save comments
        lines.add("\tComentarios:[");
        comments.forEach(_item -> {
            String withComma = comments.get(comments.size() - 1).equals(_item) ? "" : ", ";
            lines.add(String.format("\t\t{Texto: \"%1$s\"}%2$s", _item, withComma));
        });
        lines.add("\t]");
    }

    private String startJSON() {
        return "{";
    }

    private String closeJSON() {
        return "}";
    }

    public String setScore(Double score) {
        return "\tScore: \"" + score + "\",";
    }

    public int getChildrenParams(Node<JavaData> node) {
        int params = 0;
        if (node.getChildren() != null && node.getChildren().size() > 0) {
            params = node.getChildren().stream().filter(node1 -> (node1.getData().getAttributes().contains("PARAMETER"))).map(_item -> 1).reduce(params, Integer::sum);
        }
        return params;
    }

    public String getType(Node<JavaData> node) {
        try {
            return node.getData().getAttributes().get(0);
        } catch (Exception e) {
            return "PUBLIC";
        }
    }

}
