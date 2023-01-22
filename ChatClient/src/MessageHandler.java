/**
 * This class creates a MessageSender that continuously listens to user input
 * and a message receiver that continuously listens for messages from the server.
 * It handles thread termination of both classes.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.io.IOException;
import java.net.Socket;

public class MessageHandler {
    private Socket clientSocket;
    private MessageReceiver messageReceiver;
    private MessageSender messageSender;

    public MessageHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        messageReceiver = new MessageReceiver(clientSocket, this);
        Thread receiverThread = new Thread(messageReceiver);
        receiverThread.start();
        messageSender = new MessageSender(clientSocket, this);
        Thread senderThread = new Thread(messageSender);
        senderThread.start();
    }

    /**
     * This method terminates the threads that listens for user input
     * and for messages from the server, effectively ending the program.
     */
    public void stopAll() {
        messageReceiver.stopThread();
        messageSender.stopThread();

        try {
            clientSocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
