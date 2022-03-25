package Backend;

import Frontend.ServerConsole;
import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {
        ServerConsole console;
        console = new ServerConsole();
        console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        console.setLocationRelativeTo(null);
        console.setVisible(true);

        System.out.println("setting up server");
        Server server = new Server(console);
        server.startServer();
        // automatically shut down in 5 minutes
        try {
            Thread.sleep(60000 * 5);
        } catch (Exception e) {
        }
        server.stopServer();

    }
}
