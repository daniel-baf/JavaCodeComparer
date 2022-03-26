/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Backend.Objects.AnalysisError;
import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.JavaPjcts.ProjectAnalizer;
import Backend.Objects.JavaPjcts.ProjectScoreCalculator;
import Utilities.Files.JSONCreator;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class ConsoleController {

    private ProjectAnalizer projectAnalizer;
    private ProjectScoreCalculator scoreCalculator;
    private ArrayList<AnalysisError> errors;
    private File JSON;

    public ConsoleController() {
        this.errors = new ArrayList<>();
        this.JSON = null;
    }

    /**
     * Search for lexical or syntactical errors into files given by client app,
     * if no errors found, generates a JSON file and send the JSON to client
     *
     * @param data
     * @return
     */
    public boolean analyzeJava(DataToAnalyze data) {
        // run analyzis
        this.projectAnalizer = new ProjectAnalizer(data.getProject1(), data.getProject2());
        this.projectAnalizer.execAnalyzis();
        this.errors = this.projectAnalizer.getProject1().getErrors();
        this.errors.addAll(this.projectAnalizer.getProject2().getErrors());

        if (this.errors != null && this.errors.isEmpty()) {
            // calc score
            this.scoreCalculator = new ProjectScoreCalculator(this.projectAnalizer.getProject1(), this.projectAnalizer.getProject2());
            if (this.scoreCalculator.calcResults()) {
                // create JSON
                JSONCreator jsonC = new JSONCreator();
                this.JSON = jsonC.createJSON(this.scoreCalculator);
                return true;
            }
        }
        return false;
    }

    // GETTERS AND SETTERS
    public ArrayList<AnalysisError> getErrors() {
        return this.errors;
    }

    public File getJSON() {
        return this.JSON;
    }

}
