package Backend;

import Frontend.ServerConsole;
import Utilities.Files.FileActioner;
import javax.swing.JFrame;

import Backend.Sockets.Server;

public class App {

    public static void main(String[] args) {
        ServerConsole console;
        FileActioner fileCreator = new FileActioner();
        // create relative paths
        if (fileCreator.setup()) {
            // show console
            console = new ServerConsole();
            console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            console.setLocationRelativeTo(null);
            console.setVisible(true);
            // start server
            Server server = new Server(console);
            server.startServer();
            // automatically shut down in 10 minutes
            try {
                Thread.sleep(60000 * 10);
            } catch (InterruptedException e) {
            }
            server.stopServer();
        }
    }
}
