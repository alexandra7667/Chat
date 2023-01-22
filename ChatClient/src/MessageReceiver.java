/**
 * This class listens to incoming messages from the server,
 * on the socket defined in class Main.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.io.*;
import java.net.Socket;

public class MessageReceiver implements Runnable{
    private boolean alive;
    private Socket clientSocket;
    private MessageHandler messageHandler;
    private BufferedReader bufferedReader;

    public MessageReceiver(Socket clientSocket, MessageHandler messageHandler) {
        this.clientSocket = clientSocket;
        this.messageHandler = messageHandler;
        alive = true;
    }

    /**
     * This method continuously listens for messages from the server.
     * If the message is null, the server has shut down and this program exits.
     * If the message is not null, it is displayed to the console.
     */
    @Override
    public void run() {

        createReader();

        while(alive) {
            try {
                String msgFromServer = bufferedReader.readLine();

                if(msgFromServer == null) {
                    messageHandler.stopAll();
                }
                else {
                    System.out.println("From server: " + msgFromServer);
                }

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method creates a BufferedReader from the socket's input stream.
     */
    private void createReader() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the BufferedReader.
     */
    private void closeReader() {
        try {
            bufferedReader.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method exits the run method
     * (terminates this class running as a thread).
     */
    public void stopThread() {
        closeReader();
        alive = false;
    }
}
