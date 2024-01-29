package Controller;

import Model.CustomFile;
import Model.QueueResponse;
import View.MainFrame;

import javax.crypto.CipherSpi;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ConnectServer {
    private static final String EXIT = "EXIT";
    private static final String ROOT = "ROOT";
    private static final String FOLDER = "FOLDER";
    private static final String MONITOR = "MONITOR";
    private static final String MONITORFOLDER = "MONITORFOLDER";
    private static final String INFORMATION = "INFORMATION";
    private static final String REMOVE = "REMOVE";
    private static final String DELETE = "DELETE";
    private static final String NEWFOLDER = "NEW FOLDER";
    private static final String NEWFILE = "NEW FILE";

    private List<MonitorHandler> monitorHandlers;

    private final QueueResponse queueResponse = new QueueResponse();

    private final Socket socket;
    private final MainFrame mainFrame;
    public ConnectServer(String ipAddress, MainFrame mainFrame) throws IOException {
        socket = new Socket(ipAddress, 3000);
        this.mainFrame = mainFrame;
    }

    public void addMonitorHandler(MonitorHandler monitorHandler) {
        monitorHandlers.add(monitorHandler);
    }

    private static boolean deleteDirectory(File directory) {
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

    public void ResponseServer() throws IOException, ClassNotFoundException {
        new ResponseServer(socket, queueResponse);
        monitorHandlers = new ArrayList<>();
        while (true) {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String reader = (String) objectInputStream.readObject();
            System.out.println(reader);
            switch (reader) {
                case ROOT -> {
                    List<File> fileList = List.of(File.listRoots());
                    List<CustomFile> customFiles = new ArrayList<>();
                    for (File item : fileList) {
                        customFiles.add(new CustomFile(item));
                    }
                    queueResponse.enqueueData(ROOT, customFiles);
                }

                case MONITORFOLDER -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();

                        if (obj instanceof CustomFile customFile) {
                            if (customFile.isDirectory()) {
                                File file = customFile.getFile().getCanonicalFile();
                                List<File> fileList = file.listFiles() == null ? new ArrayList<>()
                                        : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
                                List<CustomFile> customFiles = new ArrayList<>();
                                for (File item : fileList) {
                                    customFiles.add(new CustomFile(item));
                                }
                                queueResponse.enqueueData(MONITORFOLDER, customFiles);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                case FOLDER -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();

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

                                queueResponse.enqueueData(FOLDER, customFiles);
                            }

                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                }

                case MONITOR -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();
                        if (obj instanceof CustomFile file) {
                            if (file.isDirectory()) {
                                monitorHandlers.add(new MonitorHandler(this, file, file, queueResponse));
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                case INFORMATION -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();

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

                                queueResponse.enqueueData(INFORMATION, customFiles);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                }

                case REMOVE -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();

                        if (obj instanceof CustomFile customFile) {
                            File file = customFile.getFile().getCanonicalFile();
                            Iterator<MonitorHandler> iterator = monitorHandlers.iterator();
                            while (iterator.hasNext()) {
                                MonitorHandler monitorHandler = iterator.next();
                                if (monitorHandler.getRoot()
                                        .getFile()
                                        .getAbsolutePath()
                                        .contains(file.getAbsolutePath())
                                ) {
                                    iterator.remove();
                                    monitorHandler.interrupt();
                                }
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                case DELETE -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();

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

                                queueResponse.enqueueData(FOLDER, customFiles);
                            }

                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                case NEWFOLDER -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();
                        String folderName = (String) objectInputStream.readObject();

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

                                queueResponse.enqueueData(FOLDER, customFiles);
                            }

                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                case NEWFILE -> {
                    Object obj;
                    try {
                        obj = objectInputStream.readObject();
                        String folderName = (String) objectInputStream.readObject();

                        if (obj instanceof CustomFile customFile) {
                            File file = customFile.getFile().getCanonicalFile().getParentFile();
                            File newFile = new File(file.getAbsolutePath() + "/" + folderName);

                            boolean fileCreated = newFile.createNewFile();

                            if (fileCreated) {
                                System.out.println("File created successfully.");
                            } else {
                                System.out.println("File already exists.");
                            }

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

                                queueResponse.enqueueData(FOLDER, customFiles);
                            }

                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

                case EXIT -> {
                    for (MonitorHandler monitorHandler : monitorHandlers) {
                        monitorHandler.interrupt();
                    }
                    mainFrame.dispose();
                    System.exit(0);
                }
            }
        }
    }
}
