/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Backend.Objects.JavaPjcts.AnalizeData;
import Backend.Objects.JavaPjcts.Project;
import Backend.Objects.Message;
import Utilities.ReqRes;
import Backend.Utilities.DataDeliver;
import Backend.Utilities.FileChooser;
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
        this.path1 = null;
        this.path2 = null;
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
        if (checkServer()) {
            if (path1 != null && path2 != null) {
                try {
                    Message<T> filesMsg = new Message<>(ReqRes.ANALYZE_FILES, "", null);
                    AnalizeData ftad = new AnalizeData(); // create object, get data
                    Project project = new Project();
                    // get data 1st project
                    project.setFileNames(this.fChooser.getFiles(this.path1));
                    project.setFilesCuantity(project.getFiles().size());
                    ftad.setProject1(project);
                    // get data 2nd project
                    project = new Project();
                    project.setFileNames(this.fChooser.getFiles(this.path2));
                    project.setFilesCuantity(project.getFiles().size());
                    ftad.setProject2(project);
                    // send data
                    filesMsg.setData((T) ftad);
                    this.dd.sendData(filesMsg);
                    // receive response
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Una de las rutas no es valida", "RUTAS", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean checkServer() {
        // check if server is connected
        if (this.dd.isServerConnected()) {
            return true;
        } else {
            this.dd = new DataDeliver<>();
            if (!this.dd.isServerConnected()) {
                JOptionPane.showMessageDialog(null, "No se ha podido conectar al servidor\nSe intentò conectar 2 veces al servidor\ny no respondió", "CONEXIÓN", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        }
    }
}
