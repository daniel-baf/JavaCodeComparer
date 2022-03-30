package Backend.Objects.Parsers.DefData;

import Backend.Objects.Parsers.JSONData.JSONDataSaver;
import Backend.Objects.SymTable.Variables.VarAction;
import Backend.Objects.SymTable.Variables.VarCaster;
import Backend.Objects.SymTable.Variables.VarElement;
import Backend.Objects.SymTable.Variables.VarType;

public class DefCaster {

    private final ReportElementGenerator generator;
    private final VarCaster caster;

    public DefCaster(JSONDataSaver JSON) {
        this.generator = new ReportElementGenerator(JSON);
        this.caster = new VarCaster();
    }

    public VarElement cast(VarElement leftSide, VarElement rightSide, VarAction action) {
        leftSide = findIfId(leftSide);
        rightSide = findIfId(rightSide);

        if (leftSide == null || rightSide == null) {
            // casst elements
            return new VarElement(null, VarType.ERROR);
        } else {
            return this.caster.castElements(leftSide, rightSide, action);
        }
    }

    public boolean saveToTable(VarElement newSym, VarType expectedType, String id) {
        // create element, object is type VarType
        if (newSym != null) {
            // check for data type ID
            newSym = findIfId(newSym);
            // save element on table
            if (newSym.getType() == expectedType) {
                // save on table
                this.generator.saveToTable(new VarElement(newSym.getValue(), expectedType, id));
                return true;
            }
        }
        return false;
    }

    public Object findDataOnTableById(VarElement element) {
        try {
            // search for data
            VarElement found = findIfId(element);
            return found.getValue();
        } catch (Exception e) {
            System.out.println("Error casting array: " + e.getMessage());
            return null;
        }
    }
    
    public void updateOnTable(String id) {
        // find data
        
    }

    public VarElement findIfId(VarElement find) {
        return find.getType() == VarType.ID ? this.generator.findById(find.getValue().toString())
                : find;
    }
    
    public ReportElementGenerator getGenerator() {
        return generator;
    }

    public boolean deleteFromTable(String id) {
        return this.generator.removeFromTable(id) != null;
    }

    public int getIfIntFrom(Object end) {
        try {
            Object endk = end.getClass() == VarElement.class && ((VarElement) end).getType() == VarType.ID ? findIfId((VarElement) end) : end;
            if (endk.getClass() == VarElement.class) {
                return Integer.valueOf(((VarElement) endk).getValue().toString());
            } else {
                return Integer.valueOf(endk.toString());
            }
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

}
