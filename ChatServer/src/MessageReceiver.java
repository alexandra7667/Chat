/**
 * This class continuously listens for new messages from the client.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReceiver implements Runnable{
    private boolean alive;
    private Socket clientSocket;
    ClientConnection clientConnection;
    private BufferedReader bufferedReader;

    public MessageReceiver(Socket clientSocket, ClientConnection clientConnection) {
        alive = true;
        this.clientSocket = clientSocket;
        this.clientConnection = clientConnection;
    }

    /**
     * This method listens continuously for incoming messages from the client.
     * If the message is not blank (only white space), the message is sent to the
     * ClientConnection object.
     */
    @Override
    public void run() {
        createReader();

        while(alive) {
            String message = "";

            try {
                message = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(message == null || message.equalsIgnoreCase("exit")) {
                clientConnection.clientExit();
            }
            else if(!message.isBlank()) {
                clientConnection.readMessage(message);
            }
        }
    }

    /**
     * This method creates a BufferedReader
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
     * This method closes the BufferedReader
     */
    private void closeReader() {
        try {
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method terminates this class (from running as a thread).
     */
    public void killReceiver() {
        closeReader();
        alive = false;
    }
}
