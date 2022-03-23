/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Backend.Objects.JavaPjcts.JavaData;
import Backend.Objects.JavaPjcts.ProjectScoreCalculator;
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
        System.out.println("\n\n------ PRINT JSON -----\n\n");
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
    private void setData(ArrayList<String> lines, ArrayList<Node<JavaData>[]> nodes, ArrayList<String> comments) {
        // split data into classes, comments, and variables
        ArrayList<Node<JavaData>> classes = new ArrayList<>();
        ArrayList<Node<JavaData>> methods = new ArrayList<>();
        ArrayList<Node<JavaData>[]> variables = new ArrayList<>();
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
    private void splitData(ArrayList<Node<JavaData>[]> nodes, ArrayList<Node<JavaData>> classes, ArrayList<Node<JavaData>> methods, ArrayList<Node<JavaData>[]> variables) {
        // split data
        if (nodes != null) {
            nodes.forEach(variable -> {
                switch (variable[0].getData().getType()) {
                    case "CLASS" ->
                        classes.add(variable[0]);
                    case "METHOD" ->
                        methods.add(variable[0]);
                    default -> // variables
                        variables.add(variable);
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
    private void addLinesWithData(ArrayList<String> lines, ArrayList<Node<JavaData>> classes, ArrayList<Node<JavaData>> methods, ArrayList<Node<JavaData>[]> variables, ArrayList<String> comments) {
        // LINES
        String line = "";
        // save info of class
        lines.add("\tClases:[");
        line = String.format("\t\t%1$s", classes.stream().map(node -> String.format("{Nombre: \"%1$s\"},", node.getData().getVariable())).reduce(line, String::concat));
        lines.add(line);
        lines.add("\t],");
        // save info of variables
        lines.add("\tVariables:[");
        variables.forEach(_item -> {
            lines.add(String.format("\t\t{Nombre: \"%1$s\", Tipo:\"%2$s\", Funcion: \"%3$s, %4$s\"},", _item[0].getData().getVariable(), _item[0].getData().getType(), _item[0].getParent().getData().getVariable(), _item[1].getParent().getData().getVariable()));
        });
        lines.add("\t],");
        // save info methods
        lines.add("\tMetodos:[");
        methods.forEach(_node -> {
            lines.add(String.format("\t\t{Nombre: \"%1$s\", Tipo: \"%2$s\", Parametros: %3$x},", _node.getData().getVariable(), "METHOD", 2)); // TODO check this
        });
        lines.add("\t],");
        // save comments
        lines.add("\tComentarios:[");
        comments.forEach(_comment -> {
            lines.add(String.format("\t\t{Texto: \"%1$s\"", _comment));
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

}
