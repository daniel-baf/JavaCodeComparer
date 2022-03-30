package Backend.Objects.HTML.Table;

import Backend.Objects.SymTable.ReportSymTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tr implements Cloneable {

    private ArrayList<Td> tableData;
    private ArrayList<Th> tableHeaders;

    public Tr() {
        this.tableData = new ArrayList<>();
        this.tableHeaders = new ArrayList<>();
    }

    public Tr(ArrayList<Td> tableData, ArrayList<Th> tableHeaders) {
        this.tableData = tableData;
        this.tableHeaders = tableHeaders;
    }

    public void addTd(Td... td) {
        this.tableData.addAll(Arrays.asList(td));
    }

    public void add(ArrayList<Td> tds, ArrayList<Th> ths) {
        this.tableData.addAll(tds);
        this.tableData.removeAll(Collections.singleton(null));
        this.tableHeaders.addAll(ths);
        this.tableHeaders.removeAll(Collections.singleton(null));
    }

    public void addTh(Th... th) {
        this.tableHeaders.addAll(Arrays.asList(th));
    }

    public String getDataAsHTML() {
        String data = "<tr>\n";
        // set data lines inside the table row
        data = this.tableData.stream().map(tabData -> String.format("%1$s\n", tabData.getDataAsHTML())).reduce(data, String::concat);
        data = this.tableHeaders.stream().map(headData -> String.format("%1$s\n", headData.getDataAsHTML())).reduce(data, String::concat);
        data += "</tr>";
        return data;
    }

    public void updateData(ReportSymTable dataTable) {
        try {
            tableData.forEach(td -> {
                td.updateDataIfValElement(dataTable);
            });
            this.tableHeaders.forEach(th -> {
                th.updateDataIfValElement(dataTable);
            });
        } catch (Exception e) {
            System.out.println("Error updating data in TR " + e.getMessage());
        }
    }

    public ArrayList<Td> getTableDataCopy() {
        ArrayList<Td> copy = new ArrayList<>();
        this.tableData.forEach(td -> {
            try {
                copy.add((Td) td.clone());
            } catch (CloneNotSupportedException ex) {
                System.out.println("cannot clone td");
            }
        });
        return tableData;
    }

    public ArrayList<Th> getTableHeadersCopy() {
        ArrayList<Th> copy = new ArrayList<>();
        this.tableHeaders.forEach(th -> {
            try {
                copy.add((Th) th.clone());
            } catch (CloneNotSupportedException ex) {
                System.out.println("canot clone th");
            }
        });
        return tableHeaders;
    }

    @Override
    public String toString() {
        return "Tr [tableData=" + tableData + ", tableHeaders=" + tableHeaders + "]";
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        return (Tr) super.clone();
    }

    public ArrayList<Td> getTableData() {
        return tableData;
    }

    public ArrayList<Th> getTableHeaders() {
        return tableHeaders;
    }

    public void setTableData(ArrayList<Td> tableData) {
        this.tableData = tableData;
    }

    public void setTableHeaders(ArrayList<Th> tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

}
