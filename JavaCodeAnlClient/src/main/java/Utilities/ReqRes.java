package Utilities;

/**
 * ENUM used to send data to server and receive data instructions
 *
 * @author jefemayoneso
 */
public enum ReqRes {
    END_CONNECTION("END_CONNECTION"),
    ANALYZE_FILES("ANALIZE_FILES"),
    ERROR_AT_ANALYSIS("ERROR_AT_ANALIZE"),
    JSON_OK("JSON_OK");

    private final String text;

    public String getText() {
        return this.text;
    }

    private ReqRes(String text) {
        this.text = text;
    }

}
