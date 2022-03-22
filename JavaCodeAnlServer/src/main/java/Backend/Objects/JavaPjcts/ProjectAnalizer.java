package Backend.Objects.JavaPjcts;

import java.io.File;
import java.io.StringReader;

import Backend.Objects.Lexers.JavaCodeLexer;
import Backend.Objects.Parsers.JavaCodeParser;
import Backend.Objects.Parsers.ProjectDataSaver;
import Utilities.FileReader;

public class ProjectAnalizer {

    private final ProjectDataSaver project1;
    private final ProjectDataSaver project2;
    private JavaCodeLexer lexer;
    private JavaCodeParser parser;
    private final FileReader reader;
    private final Project project1Data;
    private final Project project2Data;

    public ProjectAnalizer(Project project1, Project project2) {
        this.project1 = new ProjectDataSaver("PJCT1");
        this.project2 = new ProjectDataSaver("PJCT2");
        this.project1Data = project1;
        this.project2Data = project2;
        this.reader = new FileReader();
    }

    public void execAnalyzis() {
        // analyze project1
        try {
            project1Data.getFiles().forEach(file -> {
                try {
                    startAnalyzing(file, this.project1);
                } catch (Exception e) {
                    System.out.println("Error analyzing file: " + file.getName());
                }
            });
            // analyze project 2
            project2Data.getFiles().forEach(file -> {
                try {
                    startAnalyzing(file, this.project2);
                } catch (Exception e) {
                    System.out.println("Error analyzing file: " + file.getName());
                }
            });
        } catch (Exception e) {
            System.out.println("ERROR executing analysis: " + e.getMessage());
        }
    }

    private void startAnalyzing(File file, ProjectDataSaver project) {
        try {
            this.lexer = new JavaCodeLexer(new StringReader(this.reader.readFile(file)));
            this.parser = new JavaCodeParser(this.lexer, file.getName());
            // sintactical analysis
            this.parser.parse();
            // create root file and add it to slice tree
            this.parser.getActioner().getTree().getRoot().setVariable(file.getName().toUpperCase());
            saveDataToMasterTree(project);
        } catch (Exception e) {
            System.out.println("Error at project analizer: " + e.getMessage());
        }
    }

    private void saveDataToMasterTree(ProjectDataSaver project) {
        project.addChildren(this.parser.getActioner().getTree().getRoot());// add children
        project.addVariablesCounter(this.parser.getActioner().getVarsDeclared()); // add variables counter
        project.addMethodsCounter(this.parser.getActioner().getMethodsDeclared()); // add methods counter
        project.addClassesCounter(this.parser.getActioner().getClassDeclared()); // add classes counter
        project.addComments(this.lexer.getComments()); // add comments
        project.addVariablesDeclaredCounter(this.parser.getActioner().getVarsTable()); // add variables counter table
    }

    public ProjectDataSaver getProject1() {
        return project1;
    }

    public ProjectDataSaver getProject2() {
        return project2;
    }

}
