package Controller.Strategy;

import java.util.ArrayList;
import java.util.List;

import Model.CustomFile;

public class InformationStrategy implements IStrategy {

    @Override
    public void execute(Param param) {

        if (param.object instanceof List<?> receivedList) {
            List<CustomFile> fileList = new ArrayList<>();
            for (Object item: receivedList) {
                if (item instanceof CustomFile) {
                    fileList.add((CustomFile) item);
                }
            }

            //param.viewFrame.addAllInformationFolder(fileList);
        }
    }
    
    
}
