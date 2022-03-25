/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects.JavaPjcts;

import java.io.Serializable;

/**
 * This class contains data of 2 Projects into one single file, to send this
 * into a Message to Client-server
 *
 * @author jefemayoneso
 */
public class DataToAnalyze implements Serializable {

    // generate serial version
    private static final long serialVersionUID = -1303459122394012939L;
    private JavaProject project1;
    private JavaProject project2;

    public DataToAnalyze() {
    }

    public DataToAnalyze(JavaProject project1, JavaProject project2) {
        this.project1 = project1;
        this.project2 = project2;
    }

    public JavaProject getProject1() {
        return project1;
    }

    public void setProject1(JavaProject project1) {
        this.project1 = project1;
    }

    public JavaProject getProject2() {
        return project2;
    }

    public void setProject2(JavaProject project2) {
        this.project2 = project2;
    }

}
