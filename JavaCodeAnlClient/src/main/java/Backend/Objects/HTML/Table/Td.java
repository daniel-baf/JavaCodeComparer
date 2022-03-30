package Backend.Objects.HTML.Table;

import Backend.Objects.HTML.Extra.GlobalHTML;

public class Td extends GlobalHTML implements Cloneable {

    public Td(Object data) {
        super(data);
    }

    @Override
    public String getDataAsHTML() {
        return "<td>" + data + "</td>";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Td) super.clone();
    }
}
