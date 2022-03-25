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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jefemayoneso
 */
public class RequestHandler<T> extends Thread {

    private final Socket socket;
    private final ServerConsole console;

    public RequestHandler(Socket socket, ServerConsole console) {
        this.socket = socket;
        this.console = console;
    }

    @Override
    public void run() {
        try ( ObjectOutputStream output = new ObjectOutputStream(this.socket.getOutputStream());  ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream())) {
            System.out.println("Received connection");
            // get input and output streams
            // keep connection
            boolean keepConnection = true;
            // get data
            Message<T> message = (Message<T>) input.readObject();
            executeActions(message, output);
            System.out.println("close socket");
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Error at handler: " + e.getMessage());
        }
    }

    private void executeActions(Message<T> message, ObjectOutputStream output) {
        try {
            if (message.getAction() == ReqRes.ANALYZE_FILES) {
                Message<DataToAnalyze> messageSwitch = (Message<DataToAnalyze>) message;
                if (!this.console.execAnalysis(messageSwitch.getData())) {
                    // errors
                    sendErrorData(output, this.console.getController().getErrors());
                } else {
                    sendAnalyzedData(output, (T) this.console.getController().getJSON(), "Se ha generado el JSON con los resultados", ReqRes.JSON_OK);
                }
            } else {
                // TODO close socket
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendAnalyzedData(ObjectOutputStream output, T data, String message, ReqRes response) throws IOException {
        output.writeObject(new Message<>(response, message, data));
    }

    private void sendErrorData(ObjectOutputStream output, ArrayList<AnalysisError> data) throws IOException {
        output.writeObject(new Message<>(ReqRes.ERROR_AT_ANALYSIS, "Hubieron errores dentro de los archivos, se han enviado los errores encontrados.", data));
    }

}
