package Backend.Objects.JavaPjcts;

import java.io.File;
import java.io.StringReader;

import Backend.Objects.Lexers.JavaCodeLexer;
import Backend.Objects.Parsers.JavaCodeParser;
import Utilities.FileActioner;

public class ProjectAnalizer {

    private final ProjectDataSaver project1;
    private final ProjectDataSaver project2;
    private JavaCodeLexer lexer;
    private JavaCodeParser parser;
    private final FileActioner reader;
    private final JavaProject project1Data;
    private final JavaProject project2Data;

    public ProjectAnalizer(JavaProject project1, JavaProject project2) {
        this.project1 = new ProjectDataSaver("PJCT1");
        this.project2 = new ProjectDataSaver("PJCT2");
        this.project1Data = project1;
        this.project2Data = project2;
        this.reader = new FileActioner();
    }

    /**
     * Execute a full lexical and sintactical analyzis of 2 project, each
     * project contains multiple java files, this method creates 2 Objects
     * ProjectDataSaver wich contains all comments, variables, method an classes
     * declared... all information used after to calculate a copy score
     */
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

    /**
     * Analyze a file and create a derivation tree with the project data, also
     * validate errors and get comments, the data generated is used to compare a
     * tree with other tree and calculate the copy score
     *
     * @param file
     * @param project
     */
    private void startAnalyzing(File file, ProjectDataSaver project) {
        try {
            this.lexer = new JavaCodeLexer(new StringReader(this.reader.readFile(file)));
            this.parser = new JavaCodeParser(this.lexer, file.getName());
            // sintactical analysis
            this.parser.parse();
            // create root file and add it to slice tree
            this.parser.getActioner().getTree().getRoot().getData().setVariable(file.getName().toUpperCase());
        } catch (Exception e) {
            System.out.println("Error at project analizer: " + e.getMessage());
        } finally {
            saveDataToMasterTree(project);
        }
    }

    /**
     * Add data to a new three slice, this new slice can be added to a master
     * tree then and save multiple file.java trees into a master tree with .java
     * files as children
     *
     * @param project
     */
    private void saveDataToMasterTree(ProjectDataSaver project) {
        project.addComments(this.lexer.getComments()); // add comments
        project.addChildren(this.parser.getActioner().getTree().getRoot());// add children
        project.addVariablesCounter(this.parser.getActioner().getVarsDeclared()); // add variables counter
        project.addMethodsCounter(this.parser.getActioner().getMethodsDeclared()); // add methods counter
        project.addClassesCounter(this.parser.getActioner().getClassDeclared()); // add classes counter
        project.addTableHash(this.parser.getActioner().getVarsTable(), 1); // add variables counter table       
        project.addTableHash(this.parser.getActioner().getClassTable(), 2); // add variables counter table
        project.addTableHash(this.parser.getActioner().getMethodTable(), 3); // add variables counter table
        project.addTableHash(this.lexer.getHashComments(), 4);
    }

    // GETTRES AND SETTERS
    public ProjectDataSaver getProject1() {
        return project1;
    }

    public ProjectDataSaver getProject2() {
        return project2;
    }

}
