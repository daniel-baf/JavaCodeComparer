/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Backend.Objects.AnalysisError;
import Backend.Objects.Lexers.JSONLexer;
import Backend.Objects.Lexers.ReportLexer;
import Backend.Objects.Parsers.JSONParser;
import Backend.Objects.Parsers.ReportParser;
import java.io.FileReader;
import java.util.ArrayList;

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
            ArrayList<AnalysisError> errors = new ArrayList<>();
            // analyze json
            JSONLexer jl = new JSONLexer(new FileReader("/home/jefemayoneso/Desktop/compi1proj/RESULTS/result.json"));
            JSONParser jp = new JSONParser(jl);
            jp.parse();
            errors.addAll(jl.getErrors());
            errors.addAll(jp.getActioner().getErrors());
            System.out.println("JSON OK");
            // analyze .def file
            ReportLexer rl = new ReportLexer(new FileReader("/home/jefemayoneso/Desktop/compi1proj/RESULTS/report.def"));
            ReportParser rp = new ReportParser(rl, jp.getActioner().getData());
            rp.parse();
            errors.addAll(rl.getErrors());
            errors.addAll(rp.getActioner().getErrors());
            System.out.println(" FILE OK");

            System.out.println("----- ERRORS ----");
            errors.forEach(error -> {
                System.out.println(error.toString());
            }); // UI for projects
            /*
            ProjectManagerView pm = new ProjectManagerView("/home/jefemayoneso/Desktop/compi1proj/RESULTS", "report.copy");
            pm.setVisible(true);
            pm.setLocationRelativeTo(null);
            pm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
             */
        } catch (Exception e) {
            System.out.println("Exeption in thread main: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
