/**
 * This class lets the user send messages to the server,
 * on the socket defined in class Main.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MessageSender implements Runnable{
    private boolean alive;
    private Socket clientSocket;
    private MessageHandler messageHandler;
    private BufferedWriter bufferedWriter;
    private Scanner scanner;

    public MessageSender(Socket clientSocket, MessageHandler messageHandler) {
        this.clientSocket = clientSocket;
        this.messageHandler = messageHandler;
        alive = true;
    }

    /**
     * This method lets the user continuously send messages to the server.
     * If the message is "exit", this program exits
     * (client disconnects from the server.)
     */
    @Override
    public void run() {

        createWriterAndScanner();

        while (alive) {
            String msgToSend = scanner.nextLine();

            try {
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            if(msgToSend.equalsIgnoreCase("exit")) {
                messageHandler.stopAll();
                System.out.println("Disconnected from server.");
            }
        }
    }

    /**
     * This method creates a BufferedWriter from the socket's input stream.
     * It also creates a Scanner for user input.
     */
    private void createWriterAndScanner() {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            scanner = new Scanner(System.in);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the BufferedWriter and Scanner.
     */
    private void closeWriterAndScanner() {
        try {
            bufferedWriter.close();
            scanner.close();
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
        closeWriterAndScanner();
        alive = false;
    }
}
