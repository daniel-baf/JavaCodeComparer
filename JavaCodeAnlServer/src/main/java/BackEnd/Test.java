package BackEnd;

import Backend.Objects.JavaPjcts.Project;
import Backend.Objects.JavaPjcts.ProjectAnalizer;
import Backend.Objects.JavaPjcts.ProjectCopyCalcultator;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Test {

    public static void main(String[] args) {
        // project analyze
        ProjectAnalizer pa = new ProjectAnalizer(createProject("src/main/Resources/Txt/P1"), createProject("src/main/Resources/Txt/P2"));
        // run analyzis
        pa.execAnalyzis();
        // calc results
        ProjectCopyCalcultator pcc = new ProjectCopyCalcultator(pa.getProject1(), pa.getProject2());
        pcc.printTreeProjects();
        pcc.searchCommonNodes();
        pcc.printCommonNodes();
    }

    public static Project createProject(String path) {
        Project tmp = new Project();
        tmp.setFileNames(getFiles(path));
        tmp.setFilesCuantity(tmp.getFiles().size());
        return tmp;
    }

    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> filesOK = new ArrayList<>();
        if (path != null && new File(path).isDirectory()) {
            File[] files = new File(path).listFiles();
            addFiles(files, filesOK);
            Collections.sort(filesOK);
        }
        return filesOK;
    }

    public static void addFiles(File[] files, ArrayList<File> filesOK) {
        for (File file : files) {
            if (!file.isDirectory()) {
                if (file.isFile()) {
                    filesOK.add(file);
                }
            } else {
                File[] filesTmp = new File(file.getAbsolutePath()).listFiles();
                addFiles(filesTmp, filesOK);
            }
        }
    }
}
