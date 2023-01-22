/**
 * This class sends messages to the client.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.io.*;
import java.net.Socket;

public class MessageSender{
    private Socket clientSocket;
    private BufferedWriter bufferedWriter;

    public MessageSender(Socket clientSocket) {
        this.clientSocket = clientSocket;
        createWriter();
        System.out.println("I MessageSender constructor");

    }

    /**
     * This method takes a String message and sends it to the client.
     * @param message - The message to the client
     */
    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a BufferedWriter
     */
    private void createWriter() {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the BufferedWriter
     */
    public void killSender() {
        try {
            bufferedWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
