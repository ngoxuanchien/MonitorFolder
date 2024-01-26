
import Controller.ClientHandler;
import Controller.HandleResponse;
import Model.QueueResponse;
import View.ViewFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    public static void main(String[] args) {
        QueueResponse queueResponse = new QueueResponse();
        List<ClientHandler> clientHandlerList = new ArrayList<>();

        AtomicBoolean isRunning = new AtomicBoolean(true);
        ViewFrame viewFrame = new ViewFrame();
        viewFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (ClientHandler clientHandler : clientHandlerList) {
                    clientHandler.interrupt();
                }
                isRunning.set(false);
            }
        });

        HandleResponse handleResponse = new HandleResponse(queueResponse, viewFrame);
        ServerSocket serverSocket = null;
        try {
            int PORT = 3000;
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server Listening on port " + PORT);

            while (isRunning.get()) {
                Socket clientSocket = serverSocket.accept();
                clientHandlerList.add(new ClientHandler(clientSocket, queueResponse));
            }

            serverSocket.close();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException("Error starting the server: " + e.getMessage(), e);
        }
    }
}