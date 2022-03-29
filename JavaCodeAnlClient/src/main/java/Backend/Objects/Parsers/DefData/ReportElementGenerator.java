/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects.Parsers.DefData;

import Backend.Objects.Parsers.JSONData.JSONDataSaver;
import Backend.Objects.SymTable.ReportSymTable;
import Backend.Objects.SymTable.Variables.VarElement;
import Backend.Objects.SymTable.Variables.VarType;

/**
 *
 * @author jefemayoneso
 */
public class ReportElementGenerator {

    private final JSONDataSaver JSON;
    private final ReportSymTable table;

    public ReportElementGenerator(JSONDataSaver JSON) {
        this.table = new ReportSymTable();
        this.JSON = JSON;
    }

    /**
     * Return data from RESULT object
     *
     * @param key
     * @param id
     * @return
     */
    public VarElement getFromResult(int key, String id) {
        try {
            int pos = Integer.valueOf(findValOnTable(id, VarType.INTEGER).toString()); // we expect the value pof pos is INTEGER
            return switch (key) {
                case 1 ->
                    new VarElement(this.JSON, VarType.OBJECT);
                case 2 ->
                    new VarElement(this.JSON.getScore(), VarType.INTEGER);
                case 3 ->
                    new VarElement(this.JSON.getClasses(), VarType.LIST);
                case 4 ->
                    new VarElement(this.JSON.getClasses().get(pos), VarType.STRING);
                case 5 ->
                    new VarElement(this.JSON.getClasses().get(pos), VarType.STRING); // same as 5, idk why is an
                // option
                case 6 ->
                    new VarElement(this.JSON.getVariables(), VarType.LIST);
                case 7 ->
                    new VarElement(this.JSON.getVariables().get(pos), VarType.OBJECT);
                case 8 ->
                    new VarElement(this.JSON.getComments(), VarType.LIST);
                case 9 ->
                    new VarElement(this.JSON.getComments().get(pos), VarType.STRING);
                case 10 ->
                    new VarElement(this.JSON.getComments().get(pos), VarType.STRING); // same as 10, idk why is
                // an option
                case 11 ->
                    new VarElement(this.JSON.getMethods(), VarType.LIST);
                case 12 ->
                    new VarElement(this.JSON.getMethods().get(pos), VarType.OBJECT);
                default ->
                    new VarElement(null, VarType.ERROR);
            };
        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            // TODO validate errors
            System.out.println("Unable to get data from RESULT.ATTR" + e.getMessage());
            return new VarElement(null, VarType.ERROR);
        }
    }

    /**
     * Get data from object RESULT.ATTR.SUB_ATTR
     *
     * @param id
     * @param type
     * @return
     */
    public VarElement getFromMethodResult(String id, String type) {
        try {
            int pos = Integer.valueOf(findValOnTable(id, VarType.INTEGER).toString()); // we expect the value pof pos is INTEGER
            return switch (type) {
                case "name" ->
                    new VarElement(this.JSON.getMethods().get(pos).getName(), VarType.STRING);
                case "type" ->
                    new VarElement(this.JSON.getMethods().get(pos).getType(), VarType.STRING);
                case "parameters" ->
                    new VarElement(this.JSON.getMethods().get(pos).getParameters(), VarType.INTEGER);
                default ->
                    new VarElement(null, VarType.ERROR);
            };
        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            // TODO validate errors
            System.out.println("Unable to get data for RESULT.METHODS.ATTR " + e.getMessage());
            return new VarElement(null, VarType.ERROR);
        }
    }

    /**
     * Get an element from RESULT.VARIABLES.ATTR
     *
     * @param id
     * @param type
     * @return
     */
    public VarElement getFromVariableResult(String id, String type) {
        try {
            int pos = Integer.valueOf(findValOnTable(id, VarType.INTEGER).toString()); // we expect the value pof pos is INTEGER
            return switch (type) {
                case "name" ->
                    new VarElement(this.JSON.getVariables().get(pos).getName(), VarType.STRING);
                case "type" ->
                    new VarElement(this.JSON.getVariables().get(pos).getType(), VarType.STRING);
                case "function" ->
                    new VarElement(this.JSON.getVariables().get(pos).getFunctions().toString(), VarType.STRING);
                default ->
                    new VarElement(null, VarType.ERROR);
            };
        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            // TODO validate errors
            System.out.println("Unable to get data for RESULT.VARIABLES.ATTR " + e.getMessage());
            return new VarElement(null, VarType.ERROR);
        }
    }

    /**
     * Save a new symbol to symbol table
     *
     * @param element
     */
    public void saveToTable(VarElement element) {
        // search for ID data
        if (this.table.saveSymbol(element)) {
            System.out.println("||| saved: " + this.table.getTable().get(this.table.getTable().size() - 1));
        } else {
            System.out.println("not saved");
        }
    }

    /**
     * Searches for any symbol in table sym wich contains the id and matches the
     * type
     *
     * @param id
     * @param type
     * @return
     */
    private Object findValOnTable(String id, VarType type) {
        try {
            // check is an id or number
            if (isId(id)) {
                return this.table.findSymbol(id).getValue();
            } else {
                return Integer.valueOf(id);
            }
        } catch (NumberFormatException e) {
            // TODO validate errors
            System.out.println("Error finding value on table " + e.getMessage());
            return null;
        }
    }

    /**
     * Search for a declared item
     *
     * @param type
     * @param id
     * @return
     */
    public VarElement findById(String id) {
        return this.table.findSymbol(id);
    }

    /**
     * Check if an id is an element or a data type
     *
     * @param id
     * @return
     */
    private boolean isId(String id) {
        for (int i = 0; i < id.length(); i++) {
            if ((Character.isLetter(id.charAt(i)) == true)) {
                return true;
            }
        }
        return false;
    }
}
