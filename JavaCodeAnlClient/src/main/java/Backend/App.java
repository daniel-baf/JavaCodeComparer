/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import Backend.Objects.Lexers.JSONLexer;
import Backend.Objects.Lexers.ReportLexer;
import Backend.Objects.Parsers.JSONParser;
import Backend.Objects.Parsers.ReportParser;
import FrontEnd.ProjectManagerView;
import Utilities.Files.FileActioner;

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
            /*
             * JSONLexer lexer = new JSONLexer(
             * new FileReader("/home/jefemayoneso/Desktop/compi1proj/RESULTS/result.json"));
             * JSONParser parser = new JSONParser(lexer);
             * parser.parse();
             * // parser.getActioner().printData();
             */
            // ProjectManagerView projectMannager = new ProjectManagerView();
            // analyze .def file
            ReportLexer rl = new ReportLexer(new FileReader("/home/jefemayoneso/Desktop/compi1proj/RESULTS/report.def"));
            ReportParser rp = new ReportParser(rl);
            rp.parse();
        } catch (Exception e) {
            System.out.println("Exeption: " + e.getMessage());
        }

    }

}
