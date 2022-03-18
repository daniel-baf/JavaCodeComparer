/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import FrontEnd.ProjectLoaderView;
import javax.swing.JFrame;

/**
 *
 * @author jefemayoneso
 */
public class App {

    public static void main(String[] args) {
        ProjectLoaderView view1 = new ProjectLoaderView();
        view1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view1.setLocationRelativeTo(null);
        view1.setResizable(false);
        view1.setVisible(true);
    }
}
