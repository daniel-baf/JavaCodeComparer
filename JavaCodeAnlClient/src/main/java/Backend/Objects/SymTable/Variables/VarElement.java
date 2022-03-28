package Backend.Objects.SymTable.Variables;

public class VarElement {
    Object value;
    VarType type;

    public VarElement(Object value, VarType type) {
        this.value = value;
        this.type = type;
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

    public void setType(VarType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VarElement [type=" + type + ", value=" + value + "]";
    }

}