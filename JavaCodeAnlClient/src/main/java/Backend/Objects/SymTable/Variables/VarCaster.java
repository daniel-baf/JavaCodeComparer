package Backend.Objects.SymTable.Variables;

public class VarCaster {

    /**
     * Cast 2 elements to a new single element, used at parser to generate a final
     * value wich will be assigned to symbol table
     * 
     * @param element
     * @param newElement
     * @param action
     * @return
     */
    public VarElement castElements(VarElement element, VarElement newElement, VarAction action) {
        // cast errors
        if (!isError(element.getType(), newElement.getType(), action)) {
            return createNewElement(element, newElement, action);
        }
        return null;
    }

    /**
     * Check if element 1 type and element 2 type cann be applied to action
     * 
     * @param type1
     * @param type2
     * @param action
     * @return
     */
    private boolean isError(VarType type1, VarType type2, VarAction action) {
        // ex: INTEGER * STRING -> error
        if (type1 == VarType.ERROR || type2 == VarType.ERROR) {
            return false; // previously got error, do not check again
        } else if ((type1 == VarType.STRING || type2 == VarType.STRING) && isInvalidForString(action)) {
            return false;
        } else if (type1 == type2) {
            return true;
        } else if ((type1 == VarType.STRING && type2 == VarType.INTEGER)
                || (type1 == VarType.INTEGER && type2 == VarType.STRING)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Check the operation is valid for string, just adding is valid for strings
     * 
     * @param action
     * @return
     */
    private boolean isInvalidForString(VarAction action) {
        return action == VarAction.SUB || action == VarAction.MUL || action == VarAction.DIV;
    }

    /**
     * Return a new VarElement with the correct type, this method is only called
     * when the action and the element types are valid, valid states are STRING +
     * STRING, STRING + INT, INT +/*- INT
     * 
     * @param element
     * @param newElement
     * @param action
     * @return
     */
    private VarElement createNewElement(VarElement element, VarElement newElement, VarAction action) {
        if (element.getType() == newElement.getType() && element.getType() == VarType.INTEGER) {
            return new VarElement(getNewInteger((Integer) element.getValue(), (Integer) newElement.getValue(), action),
                    VarType.INTEGER);

        } else { // different types
            return new VarElement(getNewString((String) element.getValue(), (String) newElement.getValue()),
                    VarType.STRING);
        }
    }

    /**
     * Return a new INteger based on the elements gotten from the parser
     * 
     * @param n1
     * @param n2
     * @param action
     * @return
     */
    private Integer getNewInteger(Integer n1, Integer n2, VarAction action) {
        try {
            return switch (action) {
                case ADD -> n1 + n2;
                case SUB -> n1 - n2;
                case MUL -> n1 * n2;
                case DIV -> n1 / n2;
                default -> 0;
            };
        } catch (Exception e) {
            return 0;
            // TODO handle exception when div 0
        }
    }

    private String getNewString(String s1, String s2) {
        return s1 + s2;
    }

}
