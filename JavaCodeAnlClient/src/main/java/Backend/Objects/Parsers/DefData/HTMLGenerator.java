package Backend.Objects.Parsers.DefData;

import Backend.Objects.HTML.Extra.GlobalHTML;
import java.util.ArrayList;
import java.util.Arrays;

import Backend.Objects.HTML.HTML;
import Backend.Objects.HTML.HTMLContent;
import Backend.Objects.HTML.Table.*;
import java.util.Collections;

/**
 * HTMLGenerator
 */
public class HTMLGenerator {

    private final HTML html;

    public HTMLGenerator() {
        this.html = new HTML();
    }

    public ArrayList<HTMLContent> mergeHTMLToArray(ArrayList<HTMLContent> array, HTMLContent... data) {
        // try to search for id
        ArrayList<HTMLContent> result = array != null ? array : new ArrayList<>();
        if (data != null) {
            result.addAll(Arrays.asList(data));
            result.removeAll(Collections.singleton(null));
        }
        return result;
    }

    public Tr createTr(boolean isTd, ArrayList<GlobalHTML> data) {
        Tr tempTr = new Tr();
        data.forEach(globalHTML -> {
            try {
                if (isTd) {
                    tempTr.addTd((Td) globalHTML);
                } else {
                    tempTr.addTh((Th) globalHTML);
                }
            } catch (Exception e) {
            }
        });
        return tempTr;
    }

    public HTML getHtml() {
        return this.html;
    }

    public void addData(ArrayList<HTMLContent> data) {
        data.removeAll(Collections.singleton(null));
        this.html.getData().addAll(data);
    }

    public void addData(HTMLContent... data) {
        this.html.addData(data);
    }

    public void printHTML() {
        this.html.getDataAsHTML().forEach(line -> {
            try {
                System.out.println(line);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
