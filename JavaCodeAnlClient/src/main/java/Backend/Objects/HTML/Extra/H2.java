package Backend.Objects.HTML.Extra;

public class H2 extends GlobalHTML {

    public H2(Object data) {
        super(data);
    }

    @Override
    public String getDataAsHTML() {
        return  "<h2>" + data + "</h2>";
    }

    @Override
    public String toString() {
        return "<h2>" + super.toString() + "</h2>";
    }

}
