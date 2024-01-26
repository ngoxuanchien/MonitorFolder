package Controller.Strategy;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Model.CustomFile;
import Model.QueueResponse;

public class FolderStrategy implements IStrategy{
    private ObjectInputStream objectInputStream;   
    private QueueResponse queueResponse;

    public FolderStrategy( QueueResponse queueResponse, ObjectInputStream objectInputStream){
        this.objectInputStream=objectInputStream;
        this.queueResponse=queueResponse;

    }

    public FolderStrategy( QueueResponse queueResponse){
        this.queueResponse=queueResponse;
    }

    @Override
    public void execute() throws IOException, ClassNotFoundException {
    Object obj;
    try {
        obj = this.objectInputStream.readObject();

        if (obj instanceof CustomFile customFile) {
            File file = customFile.getFile().getCanonicalFile();
            if (file.isDirectory()) {
                List<File> fileList = file.listFiles() == null ? new ArrayList<>()
                        : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
                if (file.getParentFile() != null) {
                    fileList.add(0, new File(file.getPath() + "\\.."));
                }

                List<CustomFile> customFiles = new ArrayList<>();
                for (File item : fileList) {
                    customFiles.add(new CustomFile(item));
                }

                queueResponse.enqueueData("FOLDER", customFiles);
            }
        }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
    Object obj;
    try {
        obj = inputStream.readObject();

        if (obj instanceof CustomFile customFile) {
            File file = customFile.getFile().getCanonicalFile();
            if (file.isDirectory()) {
                List<File> fileList = file.listFiles() == null ? new ArrayList<>()
                        : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
                if (file.getParentFile() != null) {
                    fileList.add(0, new File(file.getPath() + "\\.."));
                }

                List<CustomFile> customFiles = new ArrayList<>();
                for (File item : fileList) {
                    customFiles.add(new CustomFile(item));
                }

                queueResponse.enqueueData("FOLDER", customFiles);
            }
        }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    
}
