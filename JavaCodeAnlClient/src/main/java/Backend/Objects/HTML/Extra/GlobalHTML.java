package Backend.Objects.HTML.Extra;

import Backend.Objects.HTML.HTMLContent;
import Backend.Objects.SymTable.ReportSymTable;
import Backend.Objects.SymTable.Variables.VarElement;
import Backend.Objects.SymTable.Variables.VarType;

public class GlobalHTML extends HTMLContent implements Cloneable {

    protected Object data;

    public GlobalHTML() {
        this.data = "";
    }

    public GlobalHTML(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data + "";
    }

    public void updateDataIfValElement(ReportSymTable dataTable) {
        try {
            if (this.data.getClass() == VarElement.class) {
                // check if is ID
                if (((VarElement) this.data).getType() == VarType.ID) {
                    this.data = dataTable.findSymbol(((VarElement) this.data).getValue().toString()).getValue();
                } else {
                    this.data = ((VarElement) this.data).getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating data at GLOBAL HTML " + e.getMessage());
        }

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (GlobalHTML) super.clone();
    }

}
