/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Backend.Objects.AnalysisError;
import Backend.Objects.Lexers.JSONLexer;
import Backend.Objects.Lexers.ReportLexer;
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
public class ProjectManagerController {

    private final String filesPath;
    private final String reportFile;
    private String jsonFile;
    private String defFile;
    private ArrayList<AnalysisError> errors;

    public ProjectManagerController(String filesPath, String reportFile) {
        this.filesPath = filesPath;
        this.reportFile = reportFile;
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
            System.out.println("Error getting data from copy file");
        }
    }

    public ArrayList<String> getFileLines(String fileName) {
        return new FileActioner().getFileLines(String.format("%1$s/%2$s", this.filesPath, fileName));
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
                JSONLexer lexer = new JSONLexer(new FileReader(String.format("%1$s/%2$s", this.filesPath, this.jsonFile)));
                JSONParser parser = new JSONParser(lexer);
                parser.parse();
                // get possible errors
                parser.getActioner().isOk();
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
                ReportLexer lexer = new ReportLexer(new FileReader(String.format("%1$s/%2$s", this.filesPath, this.defFile)));
                ReportParser parser = new ReportParser(lexer);
                parser.parse();
                // get possible errors
                addErrors(lexer.getErrors());
                return this.errors.isEmpty();
            }
        } catch (Exception e) {
            System.out.println("Error saving the def file" + e.getMessage());
        }
        return false;
    }

    // GETTERS AND UTILITIES
    public String getJsonFile() {
        return jsonFile;
    }

    public String getDefFile() {
        return defFile;
    }

    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    private boolean saveFile(String data, String file) {
        return new FileActioner().writeFile(data, this.filesPath, file);
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
