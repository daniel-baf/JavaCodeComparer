/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import FrontEnd.ProjectManagerView;
import javax.swing.WindowConstants;

/**
 * Main class, show a GUI
 *
 * @author jefemayoneso
 */
public class App {

    public static void main(String[] args) {
        /*
         * ProjectLoaderView view1 = new ProjectLoaderView<>();
         * view1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         * view1.setLocationRelativeTo(null);
         * view1.setResizable(false);
         * view1.setVisible(true);
         */

        // test JSON analyze
        try {
            // analyze .def file
            /*
            ReportLexer rl = new ReportLexer(new FileReader("/home/jefemayoneso/Desktop/compi1proj/RESULTS/report.def"));
            ReportParser rp = new ReportParser(rl);
            rp.parse();
            System.out.println(" FILE OK");
             */
            // UI for projects
            ProjectManagerView pm = new ProjectManagerView("/home/jefemayoneso/Desktop/compi1proj/RESULTS", "report.copy");
            pm.setVisible(true);
            pm.setLocationRelativeTo(null);
            pm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } catch (Exception e) {
            System.out.println("Exeption: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
