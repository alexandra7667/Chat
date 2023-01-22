/**
 * This class initiates a connection to a TCP server.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        String host = "localhost";
        int port = 2000;

        if(args.length > 0) {
            try {
                host = args[0];
                if (args.length > 1) {
                    port = Integer.parseInt(args[1]);
                }
            }
            catch (Exception e) {
                System.out.println("Wrong format of args. Must be: (String) host name (int) port number. Args are optional.");
                System.exit(-1);
            }
        }

        try {
            Socket clientSocket = new Socket(host, port);

            System.out.println("Connected to host " + host + " and port " + port);

            new MessageHandler(clientSocket);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}