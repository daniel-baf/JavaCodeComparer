/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objects;

import Utilities.ReqRes;
import java.io.Serializable;

/**
 *
 * @author jefemayoneso
 */
public class Message<T> implements Serializable {

    private static final long serialVersionUID = -5399605122490343339L;

    private ReqRes action;
    private String message;
    private T data;

    public Message() {
    }

    public Message(ReqRes action, String message, T data) {
        this.action = action;
        this.message = message;
        this.data = data;
    }

    public ReqRes getAction() {
        return action;
    }

    public void setAction(ReqRes action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
