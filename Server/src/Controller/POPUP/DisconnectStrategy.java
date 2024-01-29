package Controller.POPUP;

import java.io.IOException;

import javax.swing.JOptionPane;

import Controller.RequestClient;
import Model.Computer;
import View.ViewFrame;

public class DisconnectStrategy implements popUpStrategy {

    @Override
    public void execute(ViewFrame viewFrame) {
          Computer computer = viewFrame.getComputerSelected();
                int result = viewFrame.showDialog("Do you want to disconnect " + computer.getSocket().getInetAddress().toString());
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        viewFrame.removeComputer(computer);
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.closeConnect();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

    }
    
}
