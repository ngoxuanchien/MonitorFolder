package Controller;
import Model.QueueResponse;
import View.MainFrame;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import Controller.Strategy.IStrategy;
import Controller.Strategy.Param;
import Controller.Strategy.StrategyFactory;

public class ConnectServer {
    private List<MonitorHandler> monitorHandlers = new ArrayList<MonitorHandler>();
    private final QueueResponse queueResponse = new QueueResponse();
    private final Socket socket;
    private final MainFrame mainFrame;

    public ConnectServer(String ipAddress, MainFrame mainFrame) throws IOException {
        socket = new Socket(ipAddress, 3000);
        this.mainFrame = mainFrame;
    }

    public void addMonitorHandler(MonitorHandler monitorHandler) {
        monitorHandlers.add(monitorHandler);
    }

    public void ResponseServer() throws IOException, ClassNotFoundException {
        new ResponseServer(socket, queueResponse);
        while (true) {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String reader = (String) objectInputStream.readObject();
            IStrategy strategy = StrategyFactory.get(reader);
            Param param = new Param(queueResponse, socket, mainFrame, objectInputStream, monitorHandlers, this);
            strategy.execute(param);
        }
    }
}
