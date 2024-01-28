package Controller.Strategy;
import java.io.IOException;
import Controller.MonitorHandler;

public class ExitStrategy implements IStrategy {

    @Override
    public void execute(Param param) throws IOException, ClassNotFoundException {
        for (MonitorHandler monitorHandler : param.monitorHandlers) {
                        monitorHandler.interrupt();
                    }
                    param.mainFrame.dispose();
                    System.exit(0);
    }

}
