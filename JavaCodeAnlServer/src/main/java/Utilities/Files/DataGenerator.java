/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Files;

import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.JavaPjcts.JavaProject;

/**
 *
 * @author jefemayoneso
 */
public class DataGenerator {

    /**
     * Return an object DataToAnalyze used to calculate points and generate
     * JSON, the object contains all files given from client to server wich
     * where saved relatively into tmp folder
     *
     * @return
     */
    public DataToAnalyze getFilesToAnalize() {
        DataToAnalyze dataAnalyze = new DataToAnalyze(); // create object, get data
        FileActioner fileActioner = new FileActioner();
        JavaProject project = new JavaProject();
        // get data 1st project
        project.setFileNames(fileActioner.getFiles(fileActioner.getJAVA_DIRS() + "/P1"));
        project.setFilesCuantity(project.getFiles().size());
        dataAnalyze.setProject1(project);
        // get data 2nd project
        project = new JavaProject();
        project.setFileNames(fileActioner.getFiles(fileActioner.getJAVA_DIRS() + "/P2"));
        project.setFilesCuantity(project.getFiles().size());
        dataAnalyze.setProject2(project);
        // send data
        return dataAnalyze;
    }

}
