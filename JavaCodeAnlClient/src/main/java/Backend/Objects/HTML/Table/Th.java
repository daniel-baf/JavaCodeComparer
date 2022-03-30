package Backend.Objects.HTML.Table;

import Backend.Objects.HTML.Extra.GlobalHTML;

public class Th extends GlobalHTML implements Cloneable {

    public Th(Object data) {
        super(data);
    }

    @Override
    public String getDataAsHTML() {
        return "<th>" + data + "</th>";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Th) super.clone();
    }
}
