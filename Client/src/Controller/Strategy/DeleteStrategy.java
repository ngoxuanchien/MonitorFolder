package Controller.Strategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Model.CustomFile;

public class DeleteStrategy implements IStrategy {

    @Override
    public void execute(Param param) throws IOException, ClassNotFoundException {
         Object obj;
        try {
            obj = param.objectInputStream.readObject();

            if (obj instanceof CustomFile customFile) {
                File file = customFile.getFile().getCanonicalFile();
                if (!file.isDirectory()) {
                    boolean fileDeleted = file.delete();

                    if (fileDeleted) {
                        System.out.println("File deleted successfully.");
                    } else {
                        System.out.println("Unable to delete file.");
                    }
                } else {
                    deleteDirectory(file);
                }

                File parent = file.getParentFile();
                if (parent.isDirectory()) {
                    List<File> fileList = parent.listFiles() == null ? new ArrayList<>()
                            : new ArrayList<>(List.of(Objects.requireNonNull(parent.listFiles())));
                    if (parent.getParentFile() != null) {
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
    
    private boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }

}
