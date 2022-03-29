package Utilities.Delivers;

import Backend.Objects.Message;
import Utilities.Files.FileActioner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
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
    private FileDeliver fileDeliver;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    Socket socket;

    // CONSTRUCTOR
    public DataDeliver() {
        try {
            this.socket = new Socket(IP, PORT);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            this.fileDeliver = new FileDeliver(new DataInputStream(socket.getInputStream()), new DataOutputStream(socket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println("ERROR sendig request: " + ex);
        }
    }

    /**
     * Generate a Message<T> and send the request to server
     *
     * @param message
     */
    public void sendData(Message<T> message) {
        try {
            this.output.writeObject(message);
        } catch (IOException ex) {
            System.out.println("ERROR sendig request: " + ex);
        }
    }

    /**
     * Get any data that isn't Files from server, save the data T into a object
     * Message<T>
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Message<T> getData() {
        Message<T> response = new Message<>();
        try {
            response = (Message<T>) this.input.readObject();
        } catch (EOFException ex) {
            // OK
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error receiving data " + e);
            return null;
        }
        return response;
    }

    /**
     * Send a file to server
     *
     * @param file
     */
    public void sendFile(File file) {
        this.fileDeliver.sendFile(file);
    }

    public boolean createNewProjectFromServer(String filename, String path) {
        FileActioner actioner = new FileActioner();
        // save json
        this.fileDeliver.receiveFile(filename, path);
        // create .def
        if (actioner.createFile("report.def", path)) {
            if (actioner.createFile("report.html", path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a generated file, this file comes from server
     *
     * @param filename
     * @param savePath
     */
    public void receiveFile(String filename, String savePath) {
        this.fileDeliver.receiveFile(filename, savePath);
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

}
