package Controller.POPUP;

import java.io.IOException;

import javax.swing.JOptionPane;

import Controller.RequestClient;
import Model.Computer;
import Model.MonitorFolder;
import View.ViewFrame;

public class RemoveStrategy implements popUpStrategy{

    @Override
    public void execute(ViewFrame viewFrame) {
            MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                Computer computer = monitorFolder.getComputer();
                int result = viewFrame.showDialog("Do you want to remove monitor " + monitorFolder.getFolder().getFile().getAbsolutePath());
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        viewFrame.removeMonitorFolder(monitorFolder);
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.removeMonitorFolder(monitorFolder.getFolder());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
    }
    
}
