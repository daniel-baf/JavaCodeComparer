/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Backend.Objects.JavaPjcts.AnalizeData;
import Backend.Objects.Message;
import Utilities.ReqRes;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author jefemayoneso
 */
public class ServerThread extends Thread {

    protected Socket socket;

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("EXEC THREAD");
        try ( ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  ObjectInputStream input = new ObjectInputStream(socket.getInputStream());) {
            Message<AnalizeData> message = (Message<AnalizeData>) input.readObject();
            // execute actions
            execAction(message, output);
        } catch (EOFException e) {
            // this is ok, expected error
        } catch (Exception ex) {
            System.out.println("ERROR at thread: " + ex);
        }
    }

    private void execAction(Message<AnalizeData> message, ObjectOutputStream output) {
        try {
            if (message.getAction() == ReqRes.ANALYZE_FILES) {
                // TODO validate
                output.writeUTF("ACCION reconocida, P1: " + message.getData().getProject1().getFilesCuantity() + " P2: " + message.getData().getProject2().getFilesCuantity());
            } else {
                output.writeUTF("ACCION " + message.getAction() + " no reconocida");
            }
        } catch (IOException e) {
        }
    }

}
