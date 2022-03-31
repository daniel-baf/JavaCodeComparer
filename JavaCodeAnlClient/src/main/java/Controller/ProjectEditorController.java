/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Backend.Objects.AnalysisError;
import Backend.Objects.Lexers.JSONLexer;
import Backend.Objects.Lexers.ReportLexer;
import Backend.Objects.Parsers.JSONData.JSONDataSaver;
import Backend.Objects.Parsers.JSONParser;
import Backend.Objects.Parsers.ReportParser;
import Utilities.Files.FileActioner;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class ProjectEditorController {

    private final String filesPath;
    private final String reportFile;
    private final String htmlFile;
    private JSONDataSaver JSONData;
    private String jsonFile;
    private String defFile;
    private ArrayList<AnalysisError> errors;

    public ProjectEditorController(File copyFilePath) {
        this.filesPath = copyFilePath.getParent();
        this.reportFile = copyFilePath.getName();
        this.htmlFile = String.format("%1$s/%2$s", this.filesPath, "report.html");
        this.JSONData = new JSONDataSaver();
        this.errors = new ArrayList<>();
        getFilesProject();
    }

    private void getFilesProject() {
        try {
            String[] files = new String[2];
            String[] data = new FileActioner().readFile(new File(this.filesPath + "/" + this.reportFile)).split("\n"); // file contains JSON: ... DEF: ...
            for (int i = 0; i < 2; i++) {
                String[] file = data[i].split(":"); // TYPE: filename
                if (file[0].trim().equalsIgnoreCase("json")) {
                    files[0] = file[1].trim();
                } else {
                    files[1] = file[1].trim();
                }
            }
            // save data
            this.jsonFile = files[0];
            this.defFile = files[1];
        } catch (Exception e) {
            System.out.println("Error getting data from copy file " + e.getMessage());
        }
    }

    public ArrayList<String> getFileLines(String path) {
        return new FileActioner().getFileLines(path);
    }

    /**
     * Save the editing JSON file
     *
     * @param data
     * @return
     */
    public boolean saveJSON(String data) {
        // save files
        try {
            if (saveFile(data, this.jsonFile)) {
                JSONLexer lexer = new JSONLexer(new FileReader(this.jsonFile));
                JSONParser parser = new JSONParser(lexer);
                parser.parse();
                // get possible errors
                parser.getActioner().isOk();
                // save JSON data
                this.JSONData = parser.getActioner().getData();
                addErrors(lexer.getErrors(), parser.getActioner().getErrors());
                // check more than 1 attribute declared multiple times
                return this.errors.isEmpty();
            }
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        return false;
    }

    /**
     * Save the editing def file
     *
     * @param data
     * @return
     */
    public boolean saveDef(String data) {
        try {
            if (saveFile(data, this.defFile)) {
                ReportLexer lexer = new ReportLexer(new FileReader(this.defFile));
                ReportParser parser = new ReportParser(lexer, this.JSONData);
                parser.parse();
                // get possible errors
                addErrors(lexer.getErrors());
                addErrors(parser.getActioner().getErrors());
                // export HTML
                exportHTML(parser.getActioner().getHTMLGen().getHtml().getDataAsHTML());
                return this.errors.isEmpty();
            }
        } catch (Exception e) {
            System.out.println("Error saving the def file" + e.getMessage());
        }
        return false;
    }

    public void exportHTML(ArrayList<String> lines) {
        try {
            FileActioner fa = new FileActioner();
            fa.writeFile(lines, this.htmlFile);
        } catch (Exception e) {
            System.out.println("Unable to save HTML: " + e.getMessage());
        }
    }

    // GETTERS AND UTILITIES
    public String getJsonFile() {
        return jsonFile;
    }

    public String getDefFile() {
        return defFile;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    private boolean saveFile(String data, String file) {
        return new FileActioner().writeFile(data, file);
    }

    private void addErrors(ArrayList<AnalysisError>... errors) {
        for (ArrayList<AnalysisError> error : errors) {
            this.errors.addAll(error);
        }
    }

    public void setErrors(ArrayList<AnalysisError> errors) {
        this.errors = errors;
    }
}
