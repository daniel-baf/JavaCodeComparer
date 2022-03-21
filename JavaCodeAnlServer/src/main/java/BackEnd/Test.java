package BackEnd;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import Backend.Objects.Lexers.JavaCodeLexer;
import Backend.Objects.NodeTree.Node;
import Backend.Objects.Parsers.JavaCodeParser;

public class Test {
    
    public static void main(String[] args) {
        JavaCodeLexer lexer = new JavaCodeLexer(new StringReader(readFile("src/main/Resources/Txt/text.txt")));
        JavaCodeParser parser = new JavaCodeParser(lexer);

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(null);
        nodes.add(new Node("class", "Main"));

        for (Node nd : nodes) {
            System.out.println("IS NULL: " + (nd == null));
            if(nd != null) {
                System.out.println(nd.toString());
            }
        }

        System.out.println("SIZE: " + nodes.size());

        try {
            // printTOkens();
            parser.parse();
            // print comments

            System.out.println("COMENTARIOS: ");
            ArrayList<String> comments = lexer.getComments();
            for (String comment : comments) {
                System.out.println(comment);
            }
        } catch (Exception e) {
        }
    }

    private static String readFile(String path) {
        File file = new File(path);
        String line = "";
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                line += reader.nextLine() + "\n";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return line;
    }
}
