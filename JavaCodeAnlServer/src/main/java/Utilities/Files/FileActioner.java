/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Files;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Read all data from a file
 *
 * @author jefemayoneso
 */
public class FileActioner {

    private final String JAVA_DIRS = "tmp/JAVA";
    private final String JSON_DIRS = "tmp/JSON";

    public FileActioner() {
    }

    /**
     * Create all folders used int the program, this folders are relative to JAR
     * file
     *
     * @return
     */
    public boolean setup() {
        try {
            // delete all content if exist
            File dir = new File("tmp/");
            if (dir.exists()) {
                clearFolder(dir.toPath());
            }
            // custom dirs
            dir = new File(String.format("%1$s/%2$s", JAVA_DIRS, "P1")); // create P1 folder
            dir.mkdirs();
            dir = new File(JAVA_DIRS, "P2"); // create P2 folder
            dir.mkdirs();
            dir = new File(JSON_DIRS); // create JSON folder
            dir.mkdirs();
            return true;
        } catch (IOException e) {
            System.out.println("ex: " + e.getMessage());
            return false;
        }
    }

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
    public boolean writeFile(String subDir, String filename, ArrayList<String> lines, boolean useJSONDir) {
        String tmpPath = useJSONDir ? JSON_DIRS : JAVA_DIRS;
        try {
            File dir = new File(String.format("%1$s/%2$s", tmpPath, subDir)); // use path JAVA_DIRS/? or JSON_DIRS/? ?
            // can be null
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

    private void clearFolder(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    /**
     * Return all files into a path and all sub directories of main path
     *
     * @param path
     * @return
     */
    public ArrayList<File> getFiles(String path) {
        ArrayList<File> filesOK = new ArrayList<>();
        if (path != null && new File(path).isDirectory()) {
            File[] files = new File(path).listFiles();
            addFiles(files, filesOK);
            Collections.sort(filesOK);
        }
        return filesOK;
    }

    /**
     * Save all files that matches with the expected extensions
     *
     * @param files
     * @param filesOK
     */
    public void addFiles(File[] files, ArrayList<File> filesOK) {
        for (File file : files) {
            if (!file.isDirectory()) {
                if (file.isFile() && endsWith(".java", file.getName())) {
                    filesOK.add(file);
                }
            } else {
                File[] filesTmp = new File(file.getAbsolutePath()).listFiles();
                addFiles(filesTmp, filesOK);
            }
        }
    }

    /**
     * calculate if a string ends with a extension
     *
     * @param extension
     * @param string
     * @return
     */
    public boolean endsWith(String extension, String string) {
        return string.endsWith(extension);
    }

    // GETTERS AND SETTERS
    public File getWrittenFile(String path) {
        try {
            return new File(path);
        } catch (Exception e) {
            return null;
        }
    }

    public String getJAVA_DIRS() {
        return JAVA_DIRS;
    }

    public String getJSON_DIRS() {
        return JSON_DIRS;
    }

}
