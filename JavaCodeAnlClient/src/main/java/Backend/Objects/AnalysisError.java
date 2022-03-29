/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is general, is used to save all data related with an Analysis
 * error
 *
 * @author jefemayoneso
 */
public class AnalysisError implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private int line;
    private int column;
    private String lexeme;
    private String file;
    private ArrayList<String> expectedSymbols;
    private String project;
    private String type;

    public AnalysisError() {
        this(-1, -1, null, null, null, null);
    }

    public AnalysisError(int line, int column, String lexeme, String file, String project, String type) {
        this(line, column, lexeme, file, project, type, null);
    }

    public AnalysisError(int line, int column, String lexeme, String file, String project, String type, ArrayList<String> expectedSymbols) {
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
        this.file = file;
        this.type = type;
        this.expectedSymbols = expectedSymbols;
        this.project = project;
    }

    @Override
    public String toString() {
        return "AnalysisError{" + "line=" + line + ", column=" + column + ", lexeme=" + lexeme + ", file=" + file + ", expectedSymbols=" + expectedSymbols + " Projecto: " + this.project + ", tipo=" + type + '}';
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return column;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getFile() {
        return file;
    }

    public ArrayList<String> getExpectedSymbols() {
        return expectedSymbols;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getType() {
        return type;
    }

}
