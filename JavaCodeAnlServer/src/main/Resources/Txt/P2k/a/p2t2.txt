public class JSONCreator {

    public JSONCreator() {
    }

    public String generateJSON(ArrayListNode nodes, Double score) {
        String JSON = startJSON(); // the JSON text
        JSON = addScoreSection(score, JSON); // add score
        return closeJSON(JSON);
    }

    public String startJSON() {
        return "{\n";
    }

    public String closeJSON(String JSON) {
        return JSON + "\n}";
    }

    public String addScoreSection(Double score, String JSON) {
        return JSON + "\tScore: " + "\'" + score + "\'" + " ,\n";
    }
}