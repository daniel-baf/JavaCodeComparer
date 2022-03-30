package Backend.Objects.Parsers.JSONData;

import java.util.ArrayList;

public class JSONVariable {

    private String name;
    private String type;
    private ArrayList<String[]> functions;

    public JSONVariable(String name, String type, ArrayList<String[]> functions) {
        this.name = name;
        this.type = type;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String[]> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<String[]> functions) {
        this.functions = functions;
    }

    public String getFunctionsAsString() {
        String function = "";
        for (String[] data : this.functions) {
            function += data[0] + ": " + data[1] + ", ";
        }
        return function;
    }

    @Override
    public String toString() {
        String functPrint = "[";
        functPrint = functions.stream().map(strings -> "\n\t\t\t\t" + strings[0] + ": " + strings[1]).reduce(functPrint,
                String::concat);
        functPrint += "\n\t\t\t]";
        return "\n\t\t{\n\t\t\tfunctions=" + functPrint + ", \n\t\t\tname=" + name + ", \n\t\t\ttype=" + type
                + "\n\t\t}";
    }

}
