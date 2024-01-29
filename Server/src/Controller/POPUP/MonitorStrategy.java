package Controller.POPUP;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Controller.RequestClient;
import Model.Computer;
import Model.CustomFile;
import Model.MonitorFolder;
import View.ViewFrame;

public class MonitorStrategy implements popUpStrategy {

    @Override
    public void execute(ViewFrame viewFrame) {
      Computer computer = viewFrame.getComputerSelected();
                CustomFile file = viewFrame.getSelectedFolder();
                LocalDateTime dateTime = LocalDateTime.now();
                MonitorFolder monitorFolder = new MonitorFolder(
                        computer,
                        dateTime,
                        file,
                        new ArrayList<>());
                viewFrame.addMonitorFolder(monitorFolder);
                try {
                    RequestClient requestClient = new RequestClient(computer.getSocket());
                    requestClient.monitorFolder(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
    }
    
}
