package Controller.POPUP;

import java.io.IOException;

import Controller.RequestClient;
import Model.Computer;
import Model.CustomFile;
import Model.MonitorFolder;
import View.ViewFrame;

public class InformationStrategy implements popUpStrategy {

    @Override
    public void execute(ViewFrame viewFrame) {
            MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                CustomFile file = monitorFolder.getFolder();
                Computer computer = monitorFolder.getComputer();
                try {
                    if (file != null) {
                        viewFrame.setClient();
                        viewFrame.setVisibleInformation();
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.requestInformation(file);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
    }
    
}
