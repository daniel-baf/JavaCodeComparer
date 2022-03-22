/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects.JavaPjcts;

import Backend.Objects.NodeTree.Node;
import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class JSONCreator {

    public JSONCreator() {
    }

    public String generateJSON(ArrayList<Node> nodes, Double score) {
        String JSON = startJSON(); // the JSON text
        JSON = addScoreSection(score, JSON); // add score
        return closeJSON(JSON);
    }

    public String startJSON() {
        return "{\n";
    }

    public String closeJSON(String JSON) {
        return JSON += "\n}";
    }

    public String addScoreSection(Double score, String JSON) {
        return JSON += "\tScore: " + "\"" + score + "\" ,\n";
    }
}
