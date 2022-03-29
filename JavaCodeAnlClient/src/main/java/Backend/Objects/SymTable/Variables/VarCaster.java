package Backend.Objects.SymTable.Variables;

public class VarCaster {

    /**
     * Cast 2 elements to a new single element, used at parser to generate a
     * final value wich will be assigned to symbol table
     *
     * @param element
     * @param newElement
     * @param action
     * @return
     */
    public VarElement castElements(VarElement element, VarElement newElement, VarAction action) {
        // cast errors
        if (isValid(element.getType(), newElement.getType(), action)) {
            return createNewElement(element, newElement, action);
        }
        return new VarElement(element.getValue().toString().replaceAll("\"", "") + " " + newElement.getValue().toString().replaceAll("\"", ""), VarType.ERROR);
    }

    /**
     * Check if element 1 type and element 2 type cann be applied to action
     *
     * @param type1
     * @param type2
     * @param action
     * @return
     */
    private boolean isValid(VarType type1, VarType type2, VarAction action) {
        // ex: INTEGER * STRING -> error
        if (type1 == VarType.ERROR || type2 == VarType.ERROR) {
            return false; // previously got error, do not check again
        } else if ((type1 == VarType.STRING || type2 == VarType.STRING) && action != VarAction.ADD) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Return a new VarElement with the correct type, this method is only called
     * when the action and the element types are valid, valid states are STRING
     * + STRING, STRING + INT, INT +/*- INT
     *
     * @param element
     * @param newElement
     * @param action
     * @return
     */
    private VarElement createNewElement(VarElement element, VarElement newElement, VarAction action) {
        if (element.getType() == newElement.getType() && element.getType() == VarType.INTEGER) {
            return getArithmNum(Double.parseDouble(element.getValue().toString()), Double.parseDouble(newElement.getValue().toString()), action);
        } else { // different types
            return new VarElement(getNewString(element.getValue().toString(), newElement.getValue().toString()),
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
    private VarElement getArithmNum(Double n1, Double n2, VarAction action) {
        try {
            Double data = switch (action) {
                case ADD ->
                    n1 + n2 * 1.0;
                case SUB ->
                    n1 - n2 * 1.0;
                case MULT ->
                    n1 * n2 * 1.0;
                case DIV ->
                    n1 / n2 * 1.0;
                default ->
                    0.0;
            };
            return new VarElement(data, VarType.INTEGER);
        } catch (Exception e) {
            System.out.println("Unable to generate integer element cast " + e.getMessage());
            return new VarElement(n1, VarType.ERROR);
        }
    }

    private String getNewString(String s1, String s2) {
        return s1.replaceAll("\"", "") + s2.replaceAll("\"", "");
    }

}
