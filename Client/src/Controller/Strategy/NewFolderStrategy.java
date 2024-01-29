package Controller.Strategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Model.CustomFile;

public class NewFolderStrategy implements IStrategy{

    @Override
    public void execute(Param param) throws IOException, ClassNotFoundException {
        Object obj;
        try {
        obj = param.objectInputStream.readObject();
        String folderName = (String) param.objectInputStream.readObject();

        if (obj instanceof CustomFile customFile) {
            File file = customFile.getFile().getCanonicalFile().getParentFile();
            File newDirectory = new File(file.getAbsolutePath() + "/" + folderName);

            boolean directoryCreated = newDirectory.mkdir();
            if (directoryCreated) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Directory already exists or creation failed.");
            }

//                            File parent = file.getParentFile();
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

                param.queueResponse.enqueueData("FOLDER", customFiles);
            }

        }
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    }
    
}