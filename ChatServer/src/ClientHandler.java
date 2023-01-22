import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class handles clients connected to the server.
 * New clients are added to the concurrent list 'clientConnections'.
 * Each new client is run as a ClientConnection thread.
 * Every message is sent from this class to its intended clients, through the ClientConnection objects.
 *
 * @author alexandraharnstrom
 * @version 1
 */

public class ClientHandler{
    CopyOnWriteArrayList<ClientConnection> clientConnections;

    public ClientHandler() {
        clientConnections = new CopyOnWriteArrayList<>();
    }

    /**
     * This method instantiates an object of ClientConnection which represents one client.
     * The ClientConnection is added to the list 'clientConnections'.
     * @param clientSocket - The new client's Socket connection
     */
    public void addClient(Socket clientSocket) {
        ClientConnection clientConnection = new ClientConnection(clientSocket, this);

        clientConnections.add(clientConnection);

        sendMessage(clientConnection.getClientHostName() + " joined the chat");
    }

    /**
     * This method removes a client from the list 'clientConnections'.
     * @param clientConnection - The client to be removed
     */
    public synchronized void removeClient(ClientConnection clientConnection) {
        String clientHostName = clientConnection.getClientHostName();

        clientConnections.remove(clientConnection);

        sendMessage(clientHostName + " left the chat");
    }

    /**
     * This method sends a message to all clients in 'clientConnections'.
     * @param message - The message to be sent to all clients
     */
    public synchronized void sendMessage(String message) {
        for (ClientConnection c : clientConnections) {
            c.sendMessage(message);
        }
    }

    /**
     * This method sends a special WWHHOO message:
     * All clients' host names are sent to the client who requested it.
     */
    public synchronized void sendWwhhooMessage(ClientConnection clientConnection) {
        StringBuilder message = new StringBuilder();
        message.append("------");

        for (ClientConnection c : clientConnections) {
            message.append("WWHHOO: " + c.getClientHostName() + "\n");
        }

        message.append("------");

        clientConnection.sendMessage(String.valueOf(message));
    }

    /**
     * This method returns the number of clients in 'clientConnections'.
     * @return - The number of clients
     */
    public int getClientConnectionsSize() {
        return clientConnections.size();
    }
}
