package Controller;
import Model.QueueResponse;
import java.io.*;
import java.net.Socket;

public class ResponseServer extends Thread {
    private final Socket socket;
    private final QueueResponse queueResponse;
    private boolean running = true;
    public ResponseServer(Socket socket, QueueResponse queueResponse) {
        this.socket = socket;
        this.queueResponse = queueResponse;
        start();
    }

    public void cancel() {
        running = false;
    }

    @Override
    public void run() {
        synchronized (socket) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                while (running) {
                    String action = queueResponse.dequeueAction();
                    Object object = queueResponse.dequeueObject();

                    objectOutputStream.writeObject(action);
                    objectOutputStream.writeObject(object);
                    objectOutputStream.flush();

                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
