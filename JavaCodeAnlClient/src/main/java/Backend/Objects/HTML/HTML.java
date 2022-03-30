package Backend.Objects.HTML;

import java.util.ArrayList;
import java.util.Arrays;

public class HTML {

    public ArrayList<HTMLContent> data;

    public HTML() {
        this.data = new ArrayList<>();
    }

    public ArrayList<HTMLContent> getData() {
        return data;
    }

    public void addData(HTMLContent... data) {
        this.data.addAll(Arrays.asList(data));
    }

    public ArrayList<String> getDataAsHTML() {
        ArrayList<String> htmlData = new ArrayList<>();
        htmlData.add("<html>");
        this.data.forEach(dataLine -> {
            htmlData.add(dataLine.getDataAsHTML());
        });
        htmlData.add("</html>");
        return htmlData;
    }
}
