/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author jefemayoneso
 */
public enum ReqRes {
    END_CONNECTION("END_CONNECTION"),
    ANALYZE_FILES("ANALIZE_FILES");

    private final String text;

    public String getText() {
        return this.text;
    }

    private ReqRes(String text) {
        this.text = text;
    }
}
