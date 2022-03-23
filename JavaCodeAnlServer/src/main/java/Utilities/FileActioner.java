/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Read all data from a file
 *
 * @author jefemayoneso
 */
public class FileActioner {

    /**
     * Get the data of a file
     *
     * @param file the file received
     * @return the text of the file
     */
    public String readFile(File file) {
        String text = "";
        try ( Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                text += reader.nextLine() + "\n";
            }
        } catch (Exception e) {
            System.out.println("Error while read file: " + e);
        }
        return text;
    }

    /**
     * Write a file into a temporal directory
     *
     * @param subDir
     * @param filename
     * @param lines
     * @return
     */
    public boolean writeFile(String subDir, String filename, ArrayList<String> lines) {
        try {
            File dir = new File(String.format("tmp/%1$s", subDir));
            dir.mkdirs();
            File file = new File(dir, filename);
            file.createNewFile();
            try ( PrintWriter pw = new PrintWriter(file, "UTF-8")) {

                lines.forEach(_line -> {
                    pw.println(_line);
                });
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (IOException ex) {
            return false;
        }
    }
}
