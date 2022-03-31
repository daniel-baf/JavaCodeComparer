/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Backend.Objects.AnalysisError;
import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.JavaPjcts.JavaProject;
import Backend.Objects.Message;
import Utilities.ReqRes;
import Utilities.Delivers.DataDeliver;
import Utilities.Files.FileSelector;
import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author jefemayoneso
 */
public class ProjectCopyLoaderController<T> {

    private String path1;
    private String path2;
    private String outputPath;
    private final FileSelector fChooser;
    private DataDeliver<T> dataDeliver;
    private ArrayList<AnalysisError> errors;

    public ProjectCopyLoaderController() {
        this.path1 = null;
        this.path2 = null;
        this.fChooser = new FileSelector();
        this.dataDeliver = null;
        this.outputPath = "";
    }

    /**
     * Execute an analysis from java files, send files to server and receive a
     * JSON with the score in case of success, or an error Object in case of
     * error
     */
    @SuppressWarnings("unchecked")
    public boolean analyzeFiles() {
        this.dataDeliver = new DataDeliver<>();
        // send message with data of projects
        if (path1 != null && path2 != null) {
            try {
                // send data
                Message<DataToAnalyze> dataBackup = (Message<DataToAnalyze>) getFilesToAnalize();
                this.dataDeliver.sendData((Message<T>) dataBackup);
                sendFiles(dataBackup.getData().getProject1().getFiles());
                sendFiles(dataBackup.getData().getProject2().getFiles());
                Message<T> response = this.dataDeliver.getData();
                if (response.getAction() == ReqRes.JSON_OK) {
                    // get path to save data
                    JOptionPane.showMessageDialog(null, "Selecciona la ruta \ndonde guardar los archivos generados");
                    this.outputPath = this.fChooser.getPath();
                    if (!this.dataDeliver.createNewProjectFromServer("result.json", this.outputPath)) {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar \nel proyecto en la ruta especificada", "RUTAS", JOptionPane.ERROR_MESSAGE);
                    }
                    return true;
                } else {
                    // get errors
                    Message<ArrayList<AnalysisError>> errorsRec = (Message<ArrayList<AnalysisError>>) response;
                    this.errors = errorsRec.getData();
                }
            } catch (HeadlessException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Una de las rutas no es valida", "RUTAS", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    /**
     * Send a file to server
     *
     * @param files an array with the Files, used to get the names in server
     */
    public void sendFiles(ArrayList<File> files) {
        files.forEach(file -> {
            this.dataDeliver.sendFile(file);
        });
    }

    /**
     * Generate an object type Message<T> wich contains all file names, the
     * server uses the files list to save a copy temporally at server side
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Message<T> getFilesToAnalize() {
        Message<T> filesMsg = new Message<>(ReqRes.ANALYZE_FILES, "", null);
        DataToAnalyze ftad = new DataToAnalyze(); // create object, get data
        JavaProject project = new JavaProject();
        // get data 1st project
        project.setFileNames(this.fChooser.getFiles(this.path1));
        project.setFilesCuantity(project.getFiles().size());
        ftad.setProject1(project);
        // get data 2nd project
        project = new JavaProject();
        project.setFileNames(this.fChooser.getFiles(this.path2));
        project.setFilesCuantity(project.getFiles().size());
        ftad.setProject2(project);
        // send data
        filesMsg.setData((T) ftad);
        return filesMsg;
    }

    //GETTER AND SETTER
    public String getPath1() {
        return path1;
    }

    public String getPath2() {
        return path2;
    }

    public void savePath1() {
        this.path1 = this.fChooser.getPath();
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void savePath2() {
        this.path2 = this.fChooser.getPath();
    }

    public ArrayList<AnalysisError> getErrors() {
        return this.errors;
    }

    public File getCopyFile() {
        return this.fChooser.chooseFile("copy");
    }

}
