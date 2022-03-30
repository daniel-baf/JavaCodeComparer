package Backend.Objects.SymTable.Variables;

public class VarElement {

    Object value;
    VarType type;
    String id;

    public VarElement(Object value, VarType type) {
        this(value, type, null);
    }

    public VarElement(Object value, VarType type, String id) {
        this.value = value;
        this.type = type;
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public VarType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "VarElement [type=" + type + ", value=" + value + " id=" + id + "]";
    }

}
