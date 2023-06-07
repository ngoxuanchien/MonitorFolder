package Controller;

import Model.Notification;
import Model.QueueResponse;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private static final String COMPUTER = "COMPUTER";
    private final Socket clientSocket;
    QueueResponse queueResponse;

    public ClientHandler(Socket clientSocket, QueueResponse queueResponse) {
        this.clientSocket = clientSocket;
        this.queueResponse = queueResponse;
        this.queueResponse.enqueueData(COMPUTER, clientSocket);
        start();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
        synchronized (clientSocket) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                while (clientSocket.isConnected()) {
                    String reader = (String) objectInputStream.readObject();
                    Object object = objectInputStream.readObject();
                    queueResponse.enqueueData(reader, object);
                }
            } catch (IOException | ClassNotFoundException e) {
                if (clientSocket.isClosed()) {
                    return;
                }
                throw new RuntimeException(e);
            }
        }
    }
}
