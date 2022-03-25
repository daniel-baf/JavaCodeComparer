/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Frontend.ServerConsole;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class to run server, when petition required, run a thread
 *
 * @author jefemayoneso
 */
public class ServerL {

    static final int PORT = 5000;

    public void open(ServerConsole console) {
        System.out.println("Setting-up server");
        // show console

        try ( ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket socket = null;
            System.out.println("now listening port " + PORT);
            while (true) {
                try {
                    socket = serverSocket.accept();
                    // new thread for a client
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                System.out.println("CREATE AND RUN THREAD");
                new ServerThreadL(socket, console).start();
            }
        } catch (IOException e) {
            System.out.println("ERROR on server socket: " + e);
        }
    }
}
