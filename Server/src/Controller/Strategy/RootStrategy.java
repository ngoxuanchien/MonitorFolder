package Controller.Strategy;

import java.util.ArrayList;
import java.util.List;

import Model.Computer;
import Model.CustomFile;

public class RootStrategy implements IStrategy {

    @Override
    public void execute(Param param) {
        //Computer computer = param.viewFrame.getComputerSelected();
        if (param.object instanceof List<?> receivedList) {
            List<CustomFile> fileList = new ArrayList<>();
            for (Object item: receivedList) {
                if (item instanceof CustomFile) {
                    fileList.add((CustomFile) item);
                }
            }
           // computer.setRoots(fileList);
           // computer.setFileList(fileList);
           // param.viewFrame.addAllDrive(computer.getRoots());
           // param.viewFrame.addAllFolder(computer.getFileList());
           // param.viewFrame.setSelectedDrive(computer.getCurrentDrive());
        }
    }
    
}