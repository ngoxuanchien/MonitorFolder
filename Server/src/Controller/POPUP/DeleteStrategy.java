package Controller.POPUP;

import java.io.IOException;

import javax.swing.JOptionPane;

import Controller.RequestClient;
import Model.Computer;
import Model.CustomFile;
import View.ViewFrame;

public class DeleteStrategy implements popUpStrategy {

    @Override
    public void execute(ViewFrame viewFrame) {
     Computer computer = viewFrame.getComputerSelected();
                CustomFile file = viewFrame.getSelectedFolder();
                int result = viewFrame.showDialog("Do you want to delete " + file.getFile().getName());
                if (result == JOptionPane.YES_OPTION) {
                    System.out.println("DELETE " + file.getFile().getName());
                    try {
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.deleteFile(file);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
    }
    
}
