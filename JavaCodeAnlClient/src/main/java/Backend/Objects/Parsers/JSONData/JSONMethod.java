package Backend.Objects.Parsers.JSONData;

public class JSONMethod {

    private String name;
    private String type;
    private int parameters;

    public JSONMethod(String name, String type, int parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
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

    public int getParameters() {
        return parameters;
    }

    public void setParameters(int parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "\n\t\t{" + "name=" + name + ", type=" + type + ", parameters=" + parameters + '}';
    }

}
