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

    /**
     * Write/overwrite a file into a specific path
     *
     * @param name
     * @param path
     * @return
     */
    public boolean createFile(String name, String path) {
        try {
            File file = new File(String.format("%1$s/%2$s", path, name));
            file.createNewFile();
            return true;
        } catch (IOException ex) {
            System.out.println("ex: " + ex.getMessage());
            return false;
        }

    }

    /**
     * Return an ArrayList<String> with each line of a file
     *
     * @param path
     * @return
     */
    public ArrayList<String> getFileLines(String path) {
        ArrayList<String> lines = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                lines.add(tmp);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    /**
     * Update the text into a file
     *
     * @param data
     * @param path
     * @param filename
     * @return
     */
    public boolean writeFile(String data, String path) {
        File file = new File(path);
        try ( FileWriter writter = new FileWriter(file)) {
            file.createNewFile();
            writter.write(data);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR SAVING FILE: " + e.getMessage());
            return false;
        }
    }

    public boolean writeFile(ArrayList<String> data, String path) {
        File file = new File(path);
        try ( FileWriter writter = new FileWriter(file)) {
            file.createNewFile();
            for (String string : data) {
                writter.write(string + "\n");
            }
            return true;
        } catch (Exception e) {
            System.out.println("ERROR SAVING FILE: " + e.getMessage());
            return false;
        }
    }

    public boolean createCopyFile(String defFile, String jsonFile, String path) {
        String data = String.format("JSON:%1$s\nREPORT:%2$s", String.format("%1$s/%2$s", path, jsonFile), String.format("%1$s/%2$s", path, defFile));
        // create report.copy
        if (createFile("report.copy", path)) {
            if (writeFile(data, path + "/report.copy")) {
                return true;
            }
        }
        return false;
    }
}
