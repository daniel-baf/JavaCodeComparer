package Backend.Objects.HTML.Table;

import java.util.ArrayList;
import java.util.Arrays;

import Backend.Objects.HTML.HTMLContent;

public class Table extends HTMLContent {

    private final ArrayList<Tr> tableRows;

    public Table() {
        this.tableRows = new ArrayList<>();
    }

    public Table(ArrayList<Tr> tableRows) {
        this.tableRows = tableRows;
    }

    public ArrayList<Tr> getTableRows() {
        return tableRows;
    }

    public void addTr(Tr... tr) {
        this.tableRows.addAll(Arrays.asList(tr));
    }

    @Override
    public String getDataAsHTML() {
        String data = "<table>\n";
        // set data lines inside the table row
        data = this.tableRows.stream().map(tabData -> String.format("%1$s\n", tabData.getDataAsHTML())).reduce(data, String::concat);
        data += "</table>";
        return data;
    }

}
