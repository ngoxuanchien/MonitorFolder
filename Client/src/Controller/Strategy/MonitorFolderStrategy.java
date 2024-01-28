package Controller.Strategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Model.CustomFile;

public class MonitorFolderStrategy implements IStrategy {
    @Override
    public void execute(Param param) throws IOException, ClassNotFoundException {
        Object obj;
        try {
        obj = param.objectInputStream.readObject();

        if (obj instanceof CustomFile customFile) {
            if (customFile.isDirectory()) {
                File file = customFile.getFile().getCanonicalFile();
                List<File> fileList = file.listFiles() == null ? new ArrayList<>()
                        : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
                List<CustomFile> customFiles = new ArrayList<>();
                for (File item : fileList) {
                    customFiles.add(new CustomFile(item));
                }
                param.queueResponse.enqueueData("MONITORFOLDER", customFiles);
            }
        }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
}
