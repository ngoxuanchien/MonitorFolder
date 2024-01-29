package Controller;

import Model.CustomFile;

import java.io.*;
import java.net.Socket;

public class RequestClient {
    private static final String EXIT = "EXIT";
    private static final String NOTIFICATION = "NOTIFICATION";
    private static final String MONITOR = "MONITOR";
    private static final String MONITORFOLDER = "MONITORFOLDER";
    private static final String INFORMATION = "INFORMATION";

    private static final String ROOT = "ROOT";
    private static final String FOLDER = "FOLDER";
    private static final String REMOVE = "REMOVE";
    private static final String DELETE = "DELETE";
    private static final String NEWFOLDER = "NEW FOLDER";
    private static final String NEWFILE = "NEW FILE";
    private Socket socket;
    private final ObjectOutputStream objectOutputStream;

    public RequestClient(Socket socket) {
        this.socket = socket;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void requestRoots() throws IOException {
        objectOutputStream.writeObject(ROOT);
        objectOutputStream.flush();

    }

    public void requestFolder(CustomFile file) throws IOException {
        objectOutputStream.writeObject(FOLDER);
        objectOutputStream.writeObject(file);
        objectOutputStream.flush();


    }

    public void monitorFolder(CustomFile file) throws IOException {
        objectOutputStream.writeObject(MONITOR);
        objectOutputStream.writeObject(file);
        objectOutputStream.flush();
    }

    public void requestMonitorFolder(CustomFile file) throws IOException {
        objectOutputStream.writeObject(MONITORFOLDER);
        objectOutputStream.writeObject(file);
        objectOutputStream.flush();
    }

    public void requestInformation(CustomFile file) throws IOException {
        objectOutputStream.writeObject(INFORMATION);
        objectOutputStream.writeObject(file);
        objectOutputStream.flush();

    }

    public void closeConnect() throws IOException {
        objectOutputStream.writeObject(EXIT);
        objectOutputStream.flush();

        socket.close();
    }

    public void removeMonitorFolder(CustomFile folder) throws IOException {
        objectOutputStream.writeObject(REMOVE);
        objectOutputStream.writeObject(folder);
        objectOutputStream.flush();

    }

    public void deleteFile(CustomFile file) throws IOException {
        objectOutputStream.writeObject(DELETE);
        objectOutputStream.writeObject(file);
        objectOutputStream.flush();
    }

    public void createNewFolder(CustomFile file, String fileName) throws IOException {
        objectOutputStream.writeObject(NEWFOLDER);
        objectOutputStream.writeObject(file);
        objectOutputStream.writeObject(fileName);
        objectOutputStream.flush();
    }

    public void createNewFile(CustomFile file, String fileName) throws IOException {
        objectOutputStream.writeObject(NEWFILE);
        objectOutputStream.writeObject(file);
        objectOutputStream.writeObject(fileName);
        objectOutputStream.flush();
    }
}
