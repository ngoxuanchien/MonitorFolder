package Controller.Strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import Controller.MonitorHandler;
import View.MainFrame;

public class ExitStrategy implements IStrategy {
    private List<MonitorHandler> monitorHandlers;
    private MainFrame mainFrame;

    public ExitStrategy(List<MonitorHandler> monitorHandlers, MainFrame mainFrame)
    {
        this.monitorHandlers=monitorHandlers;
        this.mainFrame=mainFrame;
    }

    @Override
    public void execute() throws IOException, ClassNotFoundException {
        for (MonitorHandler monitorHandler : monitorHandlers) {
                        monitorHandler.interrupt();
                    }
                    mainFrame.dispose();
                    System.exit(0);
    }

    public void execute(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        for (MonitorHandler monitorHandler : monitorHandlers) {
                        monitorHandler.interrupt();
                    }
                    mainFrame.dispose();
                    System.exit(0);
    }
    
}
