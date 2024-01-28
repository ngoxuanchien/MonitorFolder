package Controller.Strategy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import Model.CustomFile;

public class RootStrategy implements IStrategy{

    @Override
    public void execute(Param param) {
        List<File> fileList = List.of(File.listRoots());
        List<CustomFile> customFiles = new ArrayList<>();
        for (File item : fileList) {
            customFiles.add(new CustomFile(item));
        }
        param.queueResponse.enqueueData("ROOT", customFiles);
    }
}
