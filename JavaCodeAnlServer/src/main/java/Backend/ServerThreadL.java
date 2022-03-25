/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Backend.Objects.AnalysisError;
import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.Message;
import Frontend.ServerConsole;
import Utilities.ReqRes;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author jefemayoneso
 */
public class ServerThreadL extends Thread {

    protected Socket socket;
    private final ServerConsole GUI;

    ServerThreadL(Socket socket, ServerConsole GUI) {
        this.socket = socket;
        this.GUI = GUI;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        System.out.println("THREAD RUNNING");
        try ( ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  ObjectInputStream input = new ObjectInputStream(socket.getInputStream());) {
            Message<DataToAnalyze> message = (Message<DataToAnalyze>) input.readObject();
            execAction(message, output);
        } catch (EOFException e) {
            // this is ok, expected error
        } catch (Exception ex) {
            System.out.println("ERROR at thread: " + ex);
        }
    }

    public void execAction(Message<DataToAnalyze> message, ObjectOutputStream outputStream) {
        try {
            if (message.getAction() == ReqRes.ANALYZE_FILES) {
                execAnalysis(message.getData(), outputStream);
            }
        } catch (IOException ex) {
            System.out.println("ERROR WHILE RESPONDDING FROM SERVER: " + ex.getMessage());
        }
    }

    public void execAnalysis(DataToAnalyze data, ObjectOutputStream output) throws IOException {
        // analyze data
        if (!this.GUI.execAnalysis(data)) {
            // error, return error
            Message<ArrayList<AnalysisError>> response = new Message<>(ReqRes.ERROR_AT_ANALYSIS, "Hubieron errores en los archivos de entrada", this.GUI.getController().getErrors());
            output.writeObject(response);
            System.out.println("response error sent");
        } else {
            // SEND JSON
            Message<File> response = new Message<>(ReqRes.JSON_OK, "Se ha generado el score, no existieron errores durante el analisis", this.GUI.getController().getJSON());
            output.writeObject(response);
            System.out.println("json sent");
        }
    }

}
