/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author jefemayoneso
 */
public class FileReader {

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
}
