/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.JavaPjcts.JavaProject;
import Backend.Objects.Message;
import Utilities.ReqRes;
import Backend.Utilities.DataDeliver;
import Backend.Utilities.FileChooser;
import Utilities.FileActioner;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author jefemayoneso
 */
public class ProjectLoaderViewController<T> {

    private String path1;
    private String path2;
    private final FileChooser fChooser;
    private DataDeliver<T> dd;

    public ProjectLoaderViewController() {
        this.path1 = "/home/jefemayoneso/Desktop/JavaCodeTestProjects/PROJECTSOK/easy/P1";
        this.path2 = "/home/jefemayoneso/Desktop/JavaCodeTestProjects/PROJECTSOK/easy/P2";
        this.fChooser = new FileChooser();
        this.dd = new DataDeliver<>();
    }

    public void savePath1() {
        this.path1 = this.fChooser.getPath();
    }

    public void savePath2() {
        this.path2 = this.fChooser.getPath();
    }

    public void analyzeFiles() {
        // send message with data of projects
        if (path1 != null && path2 != null) {
            try {
                // send data
                this.dd.sendData(getData());
                // receive response
                Message<T> response = this.dd.getData();
                System.out.println("Response: " + response.getAction());
                if (response.getAction() == ReqRes.JSON_OK) {
                    Message<File> responseFile = (Message<File>) response;
                    System.out.println(new FileActioner().readFile(responseFile.getData()));
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Una de las rutas no es valida", "RUTAS", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Message<T> getData() {
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

    public String getPath1() {
        return path1;
    }

    public String getPath2() {
        return path2;
    }

}
