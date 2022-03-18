/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects.JavaPjcts;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class Project implements Serializable {

    private int filesCuantity;
    private ArrayList<File> files;

    public Project() {
    }

    public Project(int filesCuantity, ArrayList<File> files) {
        this.filesCuantity = filesCuantity;
        this.files = files;
    }

    public int getFilesCuantity() {
        return filesCuantity;
    }

    public void setFilesCuantity(int filesCuantity) {
        this.filesCuantity = filesCuantity;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFileNames(ArrayList<File> files) {
        this.files = files;
    }

}
