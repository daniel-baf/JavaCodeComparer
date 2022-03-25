/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Sockets;

import Backend.Objects.AnalysisError;
import Backend.Objects.JavaPjcts.DataToAnalyze;
import Backend.Objects.Message;
import Frontend.ServerConsole;
import Utilities.Delivers.FileDeliver;
import Utilities.Files.DataGenerator;
import Utilities.Files.FileActioner;
import Utilities.ReqRes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jefemayoneso
 */
public class ThreadActioner<T> {

    private final ServerConsole console;
    private DataToAnalyze dataToAnalyze;
    private final FileDeliver fileDeliver;

    public ThreadActioner(ServerConsole console, DataToAnalyze dataToAnalyze, InputStream inputStream, OutputStream outputStream) {
        this.console = console;
        this.dataToAnalyze = dataToAnalyze;
        this.fileDeliver = new FileDeliver(inputStream, outputStream);
    }

    /**
     * Calculate points and generate a JSON if no error, otherwise, return a
     * list with errors
     *
     * @param message
     * @param output
     */
    @SuppressWarnings("unchecked")
    public void runCopyCalculatorAndGiveResponse(Message<T> message, ObjectOutputStream output) {
        try {
            DataToAnalyze data = new DataGenerator().getFilesToAnalize();
            if (!this.console.execAnalysis(data)) {
                sendErrorData(output, this.console.getController().getErrors());

            } else {
                sendAnalyzedData(output, (T) this.console.getController().getJSON(),
                        "Se ha generado el JSON con los resultados", ReqRes.JSON_OK);
                FileActioner actioner = new FileActioner();
                this.fileDeliver.sendFile(new File(String.format("%1$s/%2$s", actioner.getJSON_DIRS(), "copy.json")));
                actioner.setup();
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Send a message to client with T data, and a ReqRes action, used at client
     * side to display messages
     *
     * @param output
     * @param data
     * @param message
     * @param response
     * @throws IOException
     */
    private void sendAnalyzedData(ObjectOutputStream output, T data, String message, ReqRes response)
            throws IOException {
        output.writeObject(new Message<>(response, message, data));
    }

    /**
     * Return a list with all errors if there is any at analysis
     *
     * @param output
     * @param data
     * @throws IOException
     */
    private void sendErrorData(ObjectOutputStream output, ArrayList<AnalysisError> data) throws IOException {
        output.writeObject(new Message<>(ReqRes.ERROR_AT_ANALYSIS,
                "Hubieron errores dentro de los archivos, se han enviado los errores encontrados.", data));
    }

    /**
     * Get all files being sended by client
     */
    public void receiveFiles() {
        this.dataToAnalyze.getProject1().getFiles().forEach(file -> {
            this.fileDeliver.receiveFile(file.getName(), "P1");
        });
        // get project 2
        this.dataToAnalyze.getProject2().getFiles().forEach(file -> {
            this.fileDeliver.receiveFile(file.getName(), "P2");
        });
    }

    public void setDataToAnalyze(DataToAnalyze dataToAnalyze) {
        this.dataToAnalyze = dataToAnalyze;
    }

    public DataToAnalyze getDataToAnalyze() {
        return dataToAnalyze;
    }

    public FileDeliver getFileDeliver() {
        return fileDeliver;
    }

    public ServerConsole getConsole() {
        return console;
    }

}
