/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects.JavaPjcts;

import java.io.Serializable;

/**
 *
 * @author jefemayoneso
 */
public class AnalizeData implements Serializable {

    private Project project1;
    private Project project2;

    public AnalizeData() {
    }

    public AnalizeData(Project project1, Project project2) {
        this.project1 = project1;
        this.project2 = project2;
    }

    public Project getProject1() {
        return project1;
    }

    public void setProject1(Project project1) {
        this.project1 = project1;
    }

    public Project getProject2() {
        return project2;
    }

    public void setProject2(Project project2) {
        this.project2 = project2;
    }

}
