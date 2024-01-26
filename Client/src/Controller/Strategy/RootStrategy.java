package Controller.Strategy;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import Model.CustomFile;
import Model.QueueResponse;

public class RootStrategy implements IStrategy{
    private QueueResponse queueResponse;

    public RootStrategy(QueueResponse queueResponse){
        this.queueResponse=queueResponse;
    }

    @Override
    public void execute() {
        List<File> fileList = List.of(File.listRoots());
        List<CustomFile> customFiles = new ArrayList<>();
        for (File item : fileList) {
            customFiles.add(new CustomFile(item));
        }
        queueResponse.enqueueData("ROOT", customFiles);
    }

    public void execute(ObjectInputStream inputStream) {
        List<File> fileList = List.of(File.listRoots());
        List<CustomFile> customFiles = new ArrayList<>();
        for (File item : fileList) {
            customFiles.add(new CustomFile(item));
        }
        queueResponse.enqueueData("ROOT", customFiles);
    }

}
