import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the main class of the TCP chat Server.
 * It opens a ServerSocket and listens for incoming client Socket connections.
 * When a client connects, its Socket object is sent to the ClientHandler.
 *
 * @author alexandraharnstrom
 * @version 1
 */

public class Main {
    public static void main(String[] args) {

        int port = 2000;

        if(args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            }
            catch(Exception e) {
                System.out.println("Wrong format of arg 'port'. Must be (int) port. Arg is optional.");
            }
        }

        ServerSocket serverSocket = null;
        String hostname = null;

        try {
            serverSocket = new ServerSocket(port);
            hostname = serverSocket.getInetAddress().getLocalHost().getHostName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientHandler clientHandler = new ClientHandler();

        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();

                clientHandler.addClient(clientSocket);

                System.out.println("Host: " + hostname);
                System.out.println("Port: " + port);
                System.out.println("Number of clients connected: " + clientHandler.getClientConnectionsSize());
            }
            catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocket.close();
                    System.out.println("Server shutting down...");
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}