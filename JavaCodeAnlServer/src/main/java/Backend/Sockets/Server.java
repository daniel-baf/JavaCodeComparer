/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Frontend.ServerConsole;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the main thread wich calls multiple threads when a new client
 * is connected with socket, if any error is ocurred, the server will shut down
 * automatically after 5 minuts
 *
 * @author jefemayoneso
 */
public class Server<T> extends Thread {

    private final int PORT = 5000;
    private ServerSocket serverSocket;
    private boolean running = false;
    private ServerConsole console;

    public Server(ServerConsole console) {
        this.console = console;
    }

    public void startServer() {
        try {
            this.serverSocket = new ServerSocket(this.PORT);
            this.start();
        } catch (IOException ex) {
            System.out.println("Error starting server");
        }
    }

    public void stopServer() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        this.running = true;
        System.out.println("listening for connections at port: " + this.PORT);
        while (this.running) {
            try {
                // call acept, to receive next connection
                Socket socket = this.serverSocket.accept();
                // pass socket to request handler
                RequestHandler<T> handler = new RequestHandler<T>(socket, this.console);
                handler.start();
            } catch (Exception e) {
                System.out.println("Error creating request handler: " + e.getMessage());
            }
        }
    }
}
