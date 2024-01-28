package Controller.Strategy;
import java.io.IOException;
import Controller.MonitorHandler;
import Model.CustomFile;

public class MonitorStrategy implements IStrategy {
    @Override
    public void execute(Param param){
        Object obj;
        try {
            obj = param.objectInputStream.readObject();
            if (obj instanceof CustomFile file) {
                if (file.isDirectory()) {
                    param.monitorHandlers.add(new MonitorHandler(param.connectServer, file, file, param.queueResponse));
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

}
