package Utilities.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jefemayoneso
 */
public class FileSelector {

    /**
     * Return the selected path
     *
     * @return
     */
    public String getPath() {
        JFileChooser fChooser = new JFileChooser();
        fChooser.setDialogTitle("Selecciona el archivo dentro de la carpeta");
        fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int tmp = fChooser.showOpenDialog(null);
        return tmp == JFileChooser.APPROVE_OPTION ? String.format("%1$s", fChooser.getSelectedFile().getPath()) : null;
    }

    /**
     * Get all the files into a directory and all sub directories
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
     * add files to a list
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

    public File chooseFile(String extension) {
        JFileChooser fChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter(extension, extension);
        fChooser.setDialogTitle("Selecciona el archivo " + extension);
        fChooser.setFileFilter(filter);
        return fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ? fChooser.getSelectedFile() : null;
    }

}
