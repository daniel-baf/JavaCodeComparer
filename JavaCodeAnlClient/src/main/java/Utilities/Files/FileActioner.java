/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jefemayoneso
 */
public class FileActioner {

    /**
     * Get the text from any file
     *
     * @param file
     * @return
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

    public boolean createFile(String name, String path) {
        try {
            File file = new File(String.format("%1$s%2$s", path, name));
            file.createNewFile();
            return true;
        } catch (IOException ex) {
            System.out.println("ex: " + ex.getMessage());
            return false;
        }

    }

    public ArrayList<String> getFileLines(String path) {
        ArrayList<String> lines = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String tmp = "";
            while ((tmp = reader.readLine()) != null) {
                lines.add(tmp);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    public boolean writeFile(String data, String path, String filename) {
        File file = new File(String.format("%1$s/%2$s", path, filename));
        try ( FileWriter writter = new FileWriter(file)) {
            file.createNewFile();
            writter.write(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
