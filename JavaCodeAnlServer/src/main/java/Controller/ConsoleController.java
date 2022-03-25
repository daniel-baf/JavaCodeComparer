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
import Utilities.JSONCreator;
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

    public boolean analyzeJava(DataToAnalyze data) {
        this.projectAnalizer = new ProjectAnalizer(data.getProject1(), data.getProject2());
        this.scoreCalculator = new ProjectScoreCalculator(this.projectAnalizer.getProject1(), this.projectAnalizer.getProject2());
        // run analyzis
        this.projectAnalizer.execAnalyzis();
        // calc results
        //pcc.printTreeProjects();
        if (this.scoreCalculator.calcResults()) {
            // create JSON
            JSONCreator jsonC = new JSONCreator();
            this.JSON = jsonC.createJSON(this.scoreCalculator);
            return true;
        } else {
            this.errors = this.projectAnalizer.getProject1().getErrors();
            this.errors.addAll(this.projectAnalizer.getProject2().getErrors());
            return false;
        }
    }

    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<AnalysisError> errors) {
        this.errors = errors;
    }

    public File getJSON() {
        return this.JSON;
    }

}
