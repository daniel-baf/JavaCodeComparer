package Backend.Objects.HTML.Extra;

public class H1 extends GlobalHTML {

    public H1(Object data) {
        super(data);
    }

    @Override
    public String getDataAsHTML() {
        return "<h1>" + data + "</h1>";
    }

    @Override
    public String toString() {
        return "<h1>" + super.toString() + "</h1>";
    }

}
