/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class to run server, when petition required, run a thread
 *
 * @author jefemayoneso
 */
public class JavaCodeServer {

    static final int PORT = 5000;

    public void main(String[] args) {

        System.out.println("Seting-up server");
        try ( ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket socket = null;
            System.out.println("listening to port 5000");
            while (true) {
                try {
                    socket = serverSocket.accept();
                    // new thread for a client
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            System.out.println("ERROR on server socket: " + e);

        }

    }

}
