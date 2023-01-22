/**
 * This class represents one client.
 * It interprets messages sent from this client Socket connection
 * and sends them to other clients.
 * It also forwards messages sent from other clients.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.net.Socket;

public class ClientConnection{
    private Socket clientSocket;
    private ClientHandler clientHandler;
    private String clientHostName;
    private MessageSender messageSender;
    private MessageReceiver messageReceiver;


    public ClientConnection(Socket clientSocket, ClientHandler clientHandler) {
        this.clientSocket = clientSocket;
        this.clientHandler = clientHandler;
        clientHostName = clientSocket.getInetAddress().getHostName();
        messageSender = new MessageSender(clientSocket);
        messageReceiver = new MessageReceiver(clientSocket, this);
        Thread receiverThread = new Thread(messageReceiver);
        receiverThread.start();
    }

    /**
     * This method interprets the incoming message from the client.
     * If the message is "wwhhoo", it is a request to know all host names of clients.
     * If the message is not "wwhhoo", it is a standard message to all clients.
     */
    public void readMessage(String outgoingMessage) {
        if (outgoingMessage.equalsIgnoreCase("wwhhoo")) {
            clientHandler.sendWwhhooMessage(this);
        }
        else {
            clientHandler.sendMessage(clientHostName + ": " + outgoingMessage);
        }
    }

    /**
     * This method retrieves a message from another client via the ClientHandler
     * and sends it to the MessageSender.
     */
    public void sendMessage(String incomingMessage) {
        messageSender.sendMessage(incomingMessage);
    }

    /**
     * This method runs when the client exits.
     * It kills the MessageReceiver thread, closes all streams,
     * and alerts the ClientHandler to remove this client from the list of clients.
     */
    public void clientExit() {
        messageReceiver.killReceiver();

        messageSender.killSender();

        closeSocket();

        clientHandler.removeClient(this);
    }

    /**
     * This method closes the client's Socket connection.
     */
    private void closeSocket() {
        try {
            clientSocket.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns this client's host name
     * @return clientHostName - The host name of this client
     */
    public String getClientHostName() {
        return clientHostName;
    }

}
