package Backend;

import Backend.Objects.JavaPjcts.JavaProject;
import Backend.Objects.JavaPjcts.ProjectAnalizer;
import Backend.Objects.JavaPjcts.ProjectScoreCalculator;
import Utilities.JSONCreator;
import java.io.File;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        // project analyze
        ProjectAnalizer pa = new ProjectAnalizer(createProject("src/main/Resources/Txt/P1k"), createProject("src/main/Resources/Txt/P2k"));
        // run analyzis
        pa.execAnalyzis();
        // calc results
        ProjectScoreCalculator pcc = new ProjectScoreCalculator(pa.getProject1(), pa.getProject2());
        pcc.searchCommonNodes();
        // create JSON
        JSONCreator jsonC = new JSONCreator();
        jsonC.generateJSON(pcc);
    }

    public static JavaProject createProject(String path) {
        JavaProject tmp = new JavaProject();
        tmp.setFileNames(getFiles(path));
        tmp.setFilesCuantity(tmp.getFiles().size());
        return tmp;
    }

    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> filesOK = new ArrayList<>();
        if (path != null && new File(path).isDirectory()) {
            File[] files = new File(path).listFiles();
            addFiles(files, filesOK);
        }
        return filesOK;
    }

    public static void addFiles(File[] files, ArrayList<File> filesOK) {
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

    public static boolean endsWith(String extension, String string) {
        return string.endsWith(extension);
    }
}
