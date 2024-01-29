package Controller.Strategy;

import java.io.IOException;

import Controller.RequestClient;
import Model.MonitorFolder;
import Model.Notification;

public class NotificationStrategy implements IStrategy {

    @Override
    public void execute(Param param) {
        try {
            Notification notification = (Notification) param.object;
           // param.viewFrame.addNotification(notification);
            //param.viewFrame.addHistory(notification);
            //param.viewFrame.setClient();
            MonitorFolder monitorFolder = param.viewFrame.getMonitorFolderSelection();
            RequestClient requestClient = new RequestClient(monitorFolder.getComputer().getSocket());
            requestClient.requestInformation(monitorFolder.getFolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
