/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects;

import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class AnalysisError {

    private int line;
    private int column;
    private String lexeme;
    private String file;
    private ArrayList<String> expectedSymbols;
    private String project;

    public AnalysisError() {
        this(-1, -1, null, null, null);
    }

    public AnalysisError(int line, int column, String lexeme, String file, String project) {
        this(line, column, lexeme, file, project, null);
    }

    public AnalysisError(int line, int column, String lexeme, String file, String project, ArrayList<String> expectedSymbols) {
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
        this.file = file;
        this.expectedSymbols = expectedSymbols;
        this.project = project;
    }

    @Override
    public String toString() {
        return "AnalysisError{" + "line=" + line + ", column=" + column + ", lexeme=" + lexeme + ", file=" + file + ", expectedSymbols=" + expectedSymbols + " Projecto: " + this.project + '}';
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

}
