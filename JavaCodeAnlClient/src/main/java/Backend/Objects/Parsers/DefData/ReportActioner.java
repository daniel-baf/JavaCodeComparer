package Backend.Objects.Parsers.DefData;

import Backend.Objects.AnalysisError;
import Backend.Objects.Parsers.JSONData.JSONDataSaver;
import Backend.Objects.SymTable.Variables.VarAction;
import Backend.Objects.SymTable.Variables.VarCaster;
import Backend.Objects.SymTable.Variables.VarElement;
import Backend.Objects.SymTable.Variables.VarType;
import java.util.ArrayList;

public class ReportActioner {

    private final VarCaster caster;
    private final ReportElementGenerator generator;
    private ArrayList<AnalysisError> errors = new ArrayList<>();

    public ReportActioner(JSONDataSaver JSON) {
        this.caster = new VarCaster();
        this.generator = new ReportElementGenerator(JSON);
    }

    /**
     * Cast an element to be saved at table
     *
     * @param element1
     * @param element2
     * @param action
     * @return
     */
    public VarElement castElements(Object element1, Object element2, Object action, int line, int column) {
        try {
            // search for possible id
            VarElement leftSide = (VarElement) element1;
            VarElement rightSide = (VarElement) element2;
            leftSide = leftSide.getType() == VarType.ID ? this.generator.findById(leftSide.getValue().toString())
                    : leftSide;
            rightSide = rightSide.getType() == VarType.ID ? this.generator.findById(rightSide.getValue().toString())
                    : rightSide;
            if (leftSide == null || rightSide == null) {
                // casst elements
                addError(line, column, "Variable no declarada", null, "SEMANTICO");
                return new VarElement(null, VarType.ERROR);
            } else {
                return this.caster.castElements(leftSide, rightSide, (VarAction) action);
            }
        } catch (Exception e) {
            addError(line, column, "Declaracion de variable", null, "SINTACTICO");
            return new VarElement(null, VarType.ERROR);
        }

    }

    /**
     * Save an element to table
     *
     * @param object
     * @param id
     * @param type
     */
    public void saveOnTable(Object object, String id, VarType type, int line, int column) {
        try {
            // create element, object is type VarType
            VarElement element = (VarElement) object;
            if (element != null) {
                // check for data type ID
                element = element.getType() == VarType.ID ? this.generator.findById(element.getValue().toString())
                        : element;
                // save element on table
                if (element.getType() == type) {
                    // save on table
                    this.generator.saveToTable(new VarElement(element.getValue(), type, id));
                } else {
                    addError(line, column, "El tipo de variable no coincide", null, "SEMANTICO");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR SAVING ON TABLE " + e.getMessage());
        }
    }

    public VarElement getFromResult(int key, String id) {
        return this.generator.getFromResult(key, id);
    }

    public VarElement getFromMethodResult(String id, String type) {
        return this.generator.getFromMethodResult(id, type);
    }

    public VarElement getFromVariableResult(String id, String type) {
        return this.generator.getFromVariableResult(id, type);
    }

    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    public void addError(int left, int right, String value, ArrayList<String> expectedTkns, String type) {
        this.errors.add(new AnalysisError(left, left, value, "DEF", "PROYECTO COPY", type, expectedTkns));
    }

}
