/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Utilities;

import Backend.Objects.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author jefemayoneso
 */
public class DataDeliver<T> {

    private final int PORT = 5000;
    private final String IP = "localhost";
    private ObjectOutputStream output;
    private ObjectInputStream input;
    Socket socket;

    public DataDeliver() {
        try {
            this.socket = new Socket(IP, PORT);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("ERROR sendig request: " + ex);
        }
    }

    public void sendData(Message<T> message) {
        try {
            this.output.writeObject(message);
            System.out.println("Se han enviado los datos al servidor");
        } catch (IOException ex) {
            System.out.println("ERROR sendig request: " + ex);
        }
    }

    public Message<T> getData() {
        try {
            Message<T> response = (Message<T>) this.input.readObject();
            return response;
        } catch (Exception e) {
            System.out.println("Error receiving data " + e);
            return null;
        }
    }
}
