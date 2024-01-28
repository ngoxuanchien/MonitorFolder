package Controller.Strategy;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import Controller.ConnectServer;
import Controller.MonitorHandler;
import Model.QueueResponse;
import View.MainFrame;

public class Param {
    public QueueResponse queueResponse;
    public Socket socket;
    public MainFrame mainFrame;
    public ObjectInputStream objectInputStream;
    public List<MonitorHandler> monitorHandlers;
    public ConnectServer connectServer;
    
    public Param(QueueResponse queueResponse, Socket socket, MainFrame mainFrame, ObjectInputStream objectInputStream,
            List<MonitorHandler> monitorHandlers, ConnectServer connectServer) {
        this.queueResponse = queueResponse;
        this.socket = socket;
        this.mainFrame = mainFrame;
        this.objectInputStream = objectInputStream;
        this.monitorHandlers = monitorHandlers;
        this.connectServer = connectServer;
    }
}
