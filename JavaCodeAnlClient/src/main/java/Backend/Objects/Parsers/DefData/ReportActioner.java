package Backend.Objects.Parsers.DefData;

import Backend.Objects.AnalysisError;
import Backend.Objects.HTML.Extra.GlobalHTML;
import Backend.Objects.HTML.HTMLContent;
import Backend.Objects.HTML.Table.Table;
import Backend.Objects.HTML.Table.Td;
import Backend.Objects.HTML.Table.Th;
import Backend.Objects.HTML.Table.Tr;
import Backend.Objects.Parsers.JSONData.JSONDataSaver;
import Backend.Objects.SymTable.Variables.VarAction;
import Backend.Objects.SymTable.Variables.VarElement;
import Backend.Objects.SymTable.Variables.VarType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ReportActioner {

    private final DefCaster caster;
    private final HTMLGenerator htmlGenerator;
    private final ArrayList<AnalysisError> errors = new ArrayList<>();

    public ReportActioner(JSONDataSaver JSON) {
        this.caster = new DefCaster(JSON);
        this.htmlGenerator = new HTMLGenerator();
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
            VarElement got = this.caster.cast((VarElement) element1, (VarElement) element2, (VarAction) action);
            // search for possible id
            if (got.getType() == VarType.ERROR) {
                addError(line, column, "Variable no declarada", null, "SEMANTICO");
            }
            return got;
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
            if (!this.caster.saveToTable((VarElement) object, type, id)) {
                addError(line, column, "El id y/o el tipo de variable no coincide con el esperado", null, "SEMANTICO");
            }
        } catch (Exception e) {
            System.out.println("ERROR SAVING ON TABLE " + e.getMessage());
        }
    }

    public void removeFromTable(String id, int line, int column) {
        if (!this.caster.deleteFromTable(id)) {
            addError(line, column, "El id y/o de la variable no se ha encontrado", null, "SEMANTICO");
        }
    }

    /**
     *
     * @param dataToDuplicate
     * @param start
     * @param end
     * @param line
     * @param column
     * @return
     */
    public ArrayList<GlobalHTML> createMultipleTdTh(Object dataToDuplicate, int start, Object end, int line,
            int column) {
        ArrayList<GlobalHTML> data = new ArrayList<>();
        try {
            ArrayList<GlobalHTML> backup = (ArrayList<GlobalHTML>) dataToDuplicate;
            // get iterator and end data
            VarElement[] forData = (VarElement[]) end;
            // get data to duplicate
            int repeat = this.caster.getIfIntFrom(forData[1]);
            // update data before add to list
            for (int i = 0; i < repeat; i++) {
                // duplicate
                ArrayList<GlobalHTML> loopData = (ArrayList<GlobalHTML>) backup.clone();
                for (int j = 0; j < backup.size(); j++) {
                    this.caster.getGenerator().getReportTable().updateTable(forData[0].getId(), j, VarType.INTEGER);
                    loopData.get(i).updateDataIfValElement(this.caster.getGenerator().getReportTable());
                }
                data.addAll((Collection<? extends GlobalHTML>) loopData.clone());
            }
            this.caster.getGenerator().removeFromTable(forData[0].getId());
        } catch (Exception e) {
            System.out.println("Error saving Tr data " + e.getMessage());
        }
        return data;
    }

    public ArrayList<Tr> createMultipleTr(Object trToDuplicate, int start, Object end, int line, int column) {
        ArrayList<Tr> data = new ArrayList<>();
        try {
            Tr trToCopy = (Tr) trToDuplicate;
            // get iterator and end data
            VarElement[] forData = (VarElement[]) end;
            int repeat = this.caster.getIfIntFrom(forData[1]);
            // mulltiply Tr
            for (int i = 0; i < repeat; i++) {
                // update data of each tr
                Tr loopTr = (Tr) trToCopy.clone();
                loopTr.updateData(this.caster.getGenerator().getReportTable());
                this.caster.getGenerator().getReportTable().updateTable(forData[0].getId(), i, VarType.INTEGER);
                data.add(loopTr);
            }
            this.caster.getGenerator().getReportTable().delete(forData[0].getId());
        } catch (CloneNotSupportedException e) {
            System.out.println("Error creating multiple tr: " + e.getMessage());
        }
        return data;
    }

    /**
     * Save all data gotten into a HTML object
     *
     * @param object
     */
    public void saveHTML(Object object) {
        try {
            if (object != null) {
                this.htmlGenerator.addData((ArrayList<HTMLContent>) object);
            }
            this.htmlGenerator.printHTML();
        } catch (Exception e) {
            System.out.println("ERROR SAVING HTML " + e.getMessage());
        }
    }

    public ArrayList<HTMLContent> getHTMLContentAsArray(Object... data) {
        ArrayList<HTMLContent> dataToReturn = new ArrayList<>();
        try {
            for (Object object : data) {
                if (object instanceof HTMLContent) {
                    dataToReturn.add((HTMLContent) object);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR GETTING HTML CONTENT AS ARRAY " + e.getMessage());
        }
        return dataToReturn;
    }

    /**
     * Create a Table Row to be saved into HTML
     *
     * @param isTd
     * @param data
     * @return
     */
    public Tr createTr(Object data) {
        try {
            ArrayList<Th> ths = new ArrayList<>();
            ArrayList<Td> tds = new ArrayList<>();
            Class classT = ((ArrayList<Object>) data).get(0).getClass();
            if (classT == Th.class) {
                ths.addAll((Collection<? extends Th>) data);
            } else if (classT == Td.class) {
                tds.addAll((Collection<? extends Td>) data);
            }
            ths.removeAll(Collections.singleton(null));
            tds.removeAll(Collections.singleton(null));
            Tr tr = new Tr(tds, ths);
            tr.updateData(this.caster.getGenerator().getReportTable());
            return tr;
        } catch (Exception e) {
            System.out.println("Error generating TR: " + e.getMessage());
            return null;
        }
    }

    /**
     * MErge multiple arrasy of type HTMLContent into a single one
     *
     * @param array
     * @param element
     * @return
     */
    public ArrayList<HTMLContent> getVarElementsAsArray(Object array, Object element) {
        try {
            return this.htmlGenerator.mergeHTMLToArray((ArrayList<HTMLContent>) array, (HTMLContent) element);
        } catch (Exception e) {
            System.out.println("Error casting array wih single element: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<HTMLContent> mergeHTMLArrays(Object array1, Object array2) {
        try {
            ArrayList<HTMLContent> arrayMerged = array1 != null ? (ArrayList<HTMLContent>) array1 : new ArrayList<>();
            if (array2 != null) {
                arrayMerged.addAll((Collection<? extends HTMLContent>) array2);
            }
            return arrayMerged;
        } catch (Exception e) {
            System.out.println("Error merrging arrays: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Tr> getTrsAsArray(Object array, Object trs) {
        try {
            ArrayList<Tr> merge = array != null ? (ArrayList<Tr>) array : new ArrayList<>();
            if (trs != null) {
                merge.addAll((Collection<? extends Tr>) trs);
            }
            merge.removeAll(Collections.singleton(null));
            return merge;
        } catch (Exception e) {
            System.out.println("Error casting array of Tr: " + e.getMessage());
            return null;
        }
    }

    public Table saveTable(Object object) {
        try {
            return new Table((ArrayList<Tr>) object);
        } catch (Exception e) {
            System.out.println("Unable to save table, " + e.getMessage());
            return new Table();
        }
    }

    public ArrayList<HTMLContent> multipliHTMLContentForTimes(Object dataToMultiply, Object end, int line, int column) {
        ArrayList<HTMLContent> data = new ArrayList<>();
        try {
            VarElement[] forData = (VarElement[]) end;
            int repeat = this.caster.getIfIntFrom(forData[1]);
            for (int i = 0; i < repeat; i++) {
                // multiply data
                data.addAll((Collection<? extends HTMLContent>) dataToMultiply);
            }
            // delete iterator
            this.caster.getGenerator().removeFromTable(forData[0].getId());
            // return list
            return data;
        } catch (Exception e) {
            System.out.println("Error multipliHTMLContentForTimes: " + e.getMessage());
        }
        return data;
    }

    /**
     * Find data for an especific item int sym table
     *
     * @param elem
     * @param line
     * @param column
     * @return
     */
    public Object findDataForId(Object elem, int line, int column) {
        try {
            // try for VARS WHEN IS VARELEMENT
            Object found = elem.getClass() == VarElement.class && ((VarElement) elem).getType() == VarType.ID
                    ? this.caster.findDataOnTableById((VarElement) elem)
                    : elem;
            if (found == null) {
                addError(line, column, "Variable no declarada", null, "SEMANTICO");
            }
            return found;
        } catch (Exception e) {
            System.out.println("Error searching for Data by ID: " + e.getMessage());
            return null;
        }
    }

    public VarElement getFromResult(int key, String id) {
        return this.caster.getGenerator().getFromResult(key, id);
    }

    public VarElement getFromMethodResult(String id, String type) {
        return this.caster.getGenerator().getFromMethodResult(id, type);
    }

    public VarElement getFromVariableResult(String id, String type) {
        return this.caster.getGenerator().getFromVariableResult(id, type);
    }

    public ArrayList<AnalysisError> getErrors() {
        return errors;
    }

    public void addError(int left, int right, String value, ArrayList<String> expectedTkns, String type) {
        this.errors.add(new AnalysisError(left, left, value, "DEF", "PROYECTO COPY", type, expectedTkns));
    }

    public ReportElementGenerator getGenerator() {
        return this.caster.getGenerator();
    }

}
