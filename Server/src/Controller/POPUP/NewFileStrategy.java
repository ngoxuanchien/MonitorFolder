package Controller.POPUP;

import java.io.IOException;

import Controller.RequestClient;
import Model.Computer;
import Model.CustomFile;
import View.ViewFrame;

public class NewFileStrategy implements popUpStrategy {

    @Override
    public void execute(ViewFrame viewFrame) {
             Computer computer = viewFrame.getComputerSelected();
                CustomFile file = viewFrame.getSelectedFolder();
                String fileName = viewFrame.requestName("Name the file?");
                try {
                    RequestClient requestClient = new RequestClient(computer.getSocket());
                    requestClient.createNewFile(file, fileName);

//                    requestClient.requestInformation(file.getParentFile());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
    }
    
}
