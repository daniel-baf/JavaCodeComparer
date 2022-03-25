/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.Message;
import Frontend.ServerConsole;
import Utilities.ReqRes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This method run a client requests, a new Thread is created when socket client
 * is connected, once operation are done, the tread is interrupted, or, if there
 * is 5 minutes without requests, thread will automatically interrupt
 *
 * @author jefemayoneso
 */
public class RequestHandler<T> extends Thread {

    private final Socket socket;
    private ThreadActioner actioner;

    public RequestHandler(Socket socket, ServerConsole console) {

        this.socket = socket;
        try {
            this.actioner = new ThreadActioner<>(console, null, new DataInputStream(this.socket.getInputStream()),
                    new DataOutputStream(this.socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creating actioner thread");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try (
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());  ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());  DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());  DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream())) {
            System.out.println("Received connection");
            // get 1st instruction
            Message<T> message = (Message<T>) objectInputStream.readObject();
            handleRequest(message, objectOutputStream, objectInputStream);
            System.out.println("close socket");
            this.socket.close();
        } catch (Exception ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    private void handleRequest(Message<T> mainRequest, ObjectOutputStream output, ObjectInputStream input) {
        // auto close in 5 minutes
        // wait for requests
        while (mainRequest.getAction() != ReqRes.END_CONNECTION) {
            try {
                System.out.println("DO: " + mainRequest.getAction());
                ReqRes status = executeActions(mainRequest, output); // call method
                if (status != ReqRes.END_CONNECTION) {
                    mainRequest = (Message<T>) input.readObject(); // wait for re call
                } else {
                    break;
                }
            } catch (Exception e) { // error -> close socket
                break;
            }
        }
    }

    /**
     * Uses a switch to call different actions
     *
     * @param message
     * @param output
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private ReqRes executeActions(Message<T> message, ObjectOutputStream output) throws Exception {
        switch (message.getAction()) {
            case ANALYZE_FILES -> {
                this.actioner.setDataToAnalyze(((Message<DataToAnalyze>) message).getData());
                this.actioner.receiveFiles(); // save files locally
                this.actioner.runCopyCalculatorAndGiveResponse(message, output); // send response
                return ReqRes.END_CONNECTION;
            }
            default -> {
                throw new Exception("UNKNOWN ACTION");
            }
        }
    }

}
