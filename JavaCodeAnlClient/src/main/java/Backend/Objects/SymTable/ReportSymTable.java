package Backend.Objects.SymTable;

import Backend.Objects.SymTable.Variables.VarElement;
import java.util.ArrayList;

/**
 * This class is used to save symbols declared
 *
 * @author jefemayoneso
 */
public class ReportSymTable {

    private final ArrayList<VarElement> table;

    public ReportSymTable() {
        this.table = new ArrayList<>();
    }

    public VarElement findSymbol(String id) {
        for (VarElement varElement : this.table) {
            if (varElement.getId().equals(id)) {
                return varElement;
            }
        }
        return null;
    }

    public boolean saveSymbol(VarElement variable) {
        // check if type = ID
        if (findSymbol(variable.getId()) == null) {
            this.table.add(variable);
            return true;
        }
        return false;
    }

    public ArrayList<VarElement> getTable() {
        return table;
    }

}
