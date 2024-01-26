package Controller.Strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import Controller.ConnectServer;
import Controller.MonitorHandler;
import Model.CustomFile;
import Model.QueueResponse;

public class MonitorStrategy implements IStrategy {
    private ObjectInputStream objectInputStream;
    private List<MonitorHandler> monitorHandlers;
    private QueueResponse queueResponse;
    private ConnectServer connectServer; 


    public MonitorStrategy(ConnectServer connectServer, QueueResponse queueResponse, List<MonitorHandler> monitorHandlers){
        this.monitorHandlers=monitorHandlers;
        this.queueResponse=queueResponse;
        this.connectServer=connectServer;
        
    }

    @Override
    public void execute(){
        Object obj;
        try {
            obj = objectInputStream.readObject();
            if (obj instanceof CustomFile file) {
                if (file.isDirectory()) {
                    monitorHandlers.add(new MonitorHandler(connectServer, file, file, queueResponse));
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void execute(ObjectInputStream inputStream){
        Object obj;
        try {
            obj = inputStream.readObject();
            if (obj instanceof CustomFile file) {
                if (file.isDirectory()) {
                    monitorHandlers.add(new MonitorHandler(connectServer, file, file, queueResponse));
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
