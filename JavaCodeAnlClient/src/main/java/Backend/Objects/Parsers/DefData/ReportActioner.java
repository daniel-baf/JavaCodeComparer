package Backend.Objects.Parsers.DefData;

import Backend.Objects.Parsers.JSONData.JSONDataSaver;
import Backend.Objects.SymTable.Variables.VarCaster;
import Backend.Objects.SymTable.Variables.VarElement;
import Backend.Objects.SymTable.Variables.VarType;

public class ReportActioner {

    private final VarCaster caster;
    private final JSONDataSaver JSON;

    public ReportActioner(JSONDataSaver JSON) {
        this.caster = new VarCaster();
        this.JSON = JSON;
    }

    public VarElement castElements(Object element1, Object element2, Object action) {
        // casst elements
        try {
            this.JSON.getClasses();
            // return this.caster.cast( element1, element2, action);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public JSONDataSaver getJSON() {
        return JSON;
    }

    public VarElement getFromResult(int key, String id) {
        try {
            System.out.println("get: " + key + " " + id);
            int pos = findValOnTable(id); // TODO search in symTable for ID if is ID
            VarElement data = switch (key) {
                case 1 -> new VarElement(this.JSON, VarType.OBJECT);
                case 2 -> new VarElement(this.JSON.getScore(), VarType.INTEGER);
                case 3 -> new VarElement(this.JSON.getClasses(), VarType.LIST);
                case 4 -> new VarElement(this.JSON.getClasses().get(pos), VarType.STRING);
                case 5 -> new VarElement(this.JSON.getClasses().get(pos), VarType.STRING); // same as 5, idk why is an
                                                                                           // option
                case 6 -> new VarElement(this.JSON.getVariables(), VarType.LIST);
                case 7 -> new VarElement(this.JSON.getVariables().get(pos), VarType.OBJECT);
                case 8 -> new VarElement(this.JSON.getComments(), VarType.LIST);
                case 9 -> new VarElement(this.JSON.getComments().get(pos), VarType.STRING);
                case 10 -> new VarElement(this.JSON.getComments().get(pos), VarType.STRING); // same as 10, idk why is
                                                                                             // an option
                case 11 -> new VarElement(this.JSON.getMethods(), VarType.LIST);
                case 12 -> new VarElement(this.JSON.getMethods().get(pos), VarType.OBJECT);
                default -> null;
            };
            return data;
        } catch (Exception e) {
            // TODO validate errors
            System.out.println("Error getting from result " + e.getMessage());
        }
        return null;
    }

    public VarElement getFromMethodResult(String id, String type) {
        try {
            int pos = findValOnTable(id); // TODO search in symTable fo ID if exist
            VarElement data = switch (type) {
                case "name" -> new VarElement(this.JSON.getMethods().get(pos).getName(), VarType.STRING);
                case "type" -> new VarElement(this.JSON.getMethods().get(pos).getType(), VarType.STRING);
                case "parameters" -> new VarElement(this.JSON.getMethods().get(pos).getParameters(), VarType.INTEGER);
                default -> null;
            };
            return data;
        } catch (Exception e) {
            // TODO validate errors
            System.out.println("Error getting from method result " + e.getMessage());
        }
        return null;
    }

    public VarElement getFromVariableResult(String id, String type) {
        try {
            int pos = Integer.valueOf(id); // TODO search in symTable for ID if is ID
            VarElement data = switch (type) {
                case "name" -> new VarElement(this.JSON.getVariables().get(pos).getName(), VarType.STRING);
                case "type" -> new VarElement(this.JSON.getVariables().get(pos).getType(), VarType.STRING);
                case "function" ->
                    new VarElement(this.JSON.getVariables().get(pos).getFunctions().toString(), VarType.STRING);
                default -> null;
            };
            return data;
        } catch (NumberFormatException e) {
            // TODO validate errors
            System.out.println("Error getting from variable result " + e.getMessage());
        }
        return null;
    }

    private int findValOnTable(String id) {
        if (id == null) {
            return 0;
        }
        try {
            boolean isId = false;
            // check is an id or number
            for (int i = 0; i < id.length(); i++) {
                if ((Character.isLetter(id.charAt(i)) == true)) {
                    isId = true;
                    break;
                }
            }

            if (isId) {
                // TODO search in symTable for ID if is ID
            } else {
                return Integer.valueOf(id);
            }
        } catch (NumberFormatException e) {
            // TODO validate errors
            System.out.println("Error finding value on table " + e.getMessage());
        }
        return 0;
    }

}
