package Controller;
import Model.CustomFile;
import Model.QueueResponse;
import View.MainFrame;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import Controller.Strategy.ExitStrategy;
import Controller.Strategy.FolderStrategy;
import Controller.Strategy.IStrategy;
import Controller.Strategy.InformationStrategy;
import Controller.Strategy.MonitorFolderStrategy;
import Controller.Strategy.MonitorStrategy;
import Controller.Strategy.RemoveStrategy;
import Controller.Strategy.RootStrategy;

public class ConnectServer {
    private static final String EXIT = "EXIT";
    private static final String ROOT = "ROOT";
    private static final String FOLDER = "FOLDER";
    private static final String MONITOR = "MONITOR";
    private static final String MONITORFOLDER = "MONITORFOLDER";
    private static final String INFORMATION = "INFORMATION";
    private static final String REMOVE = "REMOVE";
    private List<MonitorHandler> monitorHandlers = new ArrayList<MonitorHandler>();

    private final QueueResponse queueResponse = new QueueResponse();

    private final Socket socket;
    private final MainFrame mainFrame;

    private HashMap<String, IStrategy> Factory = new HashMap<String,IStrategy>();

    private void FactoryInit()
    {
        Factory.put(EXIT, new ExitStrategy(monitorHandlers, mainFrame));
        Factory.put(ROOT, new RootStrategy(queueResponse));
        Factory.put(FOLDER, new FolderStrategy(queueResponse));
        Factory.put(MONITOR, new MonitorStrategy(this, queueResponse,  monitorHandlers));
        Factory.put(MONITORFOLDER, new MonitorFolderStrategy(queueResponse));
        Factory.put(INFORMATION, new InformationStrategy(queueResponse));
        Factory.put(REMOVE, new RemoveStrategy(monitorHandlers));
    }

    public ConnectServer(String ipAddress, MainFrame mainFrame) throws IOException {
        socket = new Socket(ipAddress, 3000);
        this.mainFrame = mainFrame;
    }



    public void addMonitorHandler(MonitorHandler monitorHandler) {
        monitorHandlers.add(monitorHandler);
    }

    public void ResponseServer() throws IOException, ClassNotFoundException {
        FactoryInit();
        new ResponseServer(socket, queueResponse);
        //monitorHandlers = new ArrayList<>();
        //ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        while (true) {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String reader = (String) objectInputStream.readObject();

            //Test
            IStrategy strategy = Factory.get(reader);
            strategy.execute(objectInputStream);
            //
            // switch (reader) {
            //     case ROOT -> {
            //         List<File> fileList = List.of(File.listRoots());
            //         List<CustomFile> customFiles = new ArrayList<>();
            //         for (File item : fileList) {
            //             customFiles.add(new CustomFile(item));
            //         }
            //         queueResponse.enqueueData(ROOT, customFiles);
            //     }

            //     case MONITORFOLDER -> {
            //         Object obj;
            //         try {
            //             obj = objectInputStream.readObject();

            //             if (obj instanceof CustomFile customFile) {
            //                 if (customFile.isDirectory()) {
            //                     File file = customFile.getFile().getCanonicalFile();
            //                     List<File> fileList = file.listFiles() == null ? new ArrayList<>()
            //                             : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
            //                     List<CustomFile> customFiles = new ArrayList<>();
            //                     for (File item : fileList) {
            //                         customFiles.add(new CustomFile(item));
            //                     }
            //                     queueResponse.enqueueData(MONITORFOLDER, customFiles);
            //                 }
            //             }
            //         } catch (ClassNotFoundException e) {
            //             throw new RuntimeException(e);
            //         }
            //     }

            //     case FOLDER -> {
            //         Object obj;
            //         try {
            //             obj = objectInputStream.readObject();

            //             if (obj instanceof CustomFile customFile) {
            //                 File file = customFile.getFile().getCanonicalFile();
            //                 if (file.isDirectory()) {
            //                     List<File> fileList = file.listFiles() == null ? new ArrayList<>()
            //                             : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
            //                     if (file.getParentFile() != null) {
            //                         fileList.add(0, new File(file.getPath() + "\\.."));
            //                     }

            //                     List<CustomFile> customFiles = new ArrayList<>();
            //                     for (File item : fileList) {
            //                         customFiles.add(new CustomFile(item));
            //                     }

            //                     queueResponse.enqueueData(FOLDER, customFiles);
            //                 }

            //             }
            //         } catch (ClassNotFoundException e) {
            //             throw new RuntimeException(e);
            //         }
            //     }

            //     case MONITOR -> {
            //         Object obj;
            //         try {
            //             obj = objectInputStream.readObject();
            //             if (obj instanceof CustomFile file) {
            //                 if (file.isDirectory()) {
            //                     monitorHandlers.add(new MonitorHandler(this, file, file, queueResponse));
            //                 }
            //             }
            //         } catch (ClassNotFoundException e) {
            //             throw new RuntimeException(e);
            //         }
            //     }

            //     case INFORMATION -> {
            //         Object obj;
            //         try {
            //             obj = objectInputStream.readObject();
            //             if (obj instanceof CustomFile customFile) {
            //                 File file = customFile.getFile().getCanonicalFile();
            //                 if (file.isDirectory()) {
            //                     List<File> fileList = file.listFiles() == null ? new ArrayList<>()
            //                             : new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
            //                     if (file.getParentFile() != null) {
            //                         fileList.add(0, new File(file.getPath() + "\\.."));
            //                     }

            //                     List<CustomFile> customFiles = new ArrayList<>();
            //                     for (File item : fileList) {
            //                         customFiles.add(new CustomFile(item));
            //                     }

            //                     queueResponse.enqueueData(INFORMATION, customFiles);
            //                 }
            //             }
            //         } catch (ClassNotFoundException e) {
            //             throw new RuntimeException(e);
            //         }
            //     }

            //     case REMOVE -> {
            //         Object obj;
            //         try {
            //             obj = objectInputStream.readObject();

            //             if (obj instanceof CustomFile customFile) {
            //                 File file = customFile.getFile().getCanonicalFile();
            //                 Iterator<MonitorHandler> iterator = monitorHandlers.iterator();
            //                 while (iterator.hasNext()) {
            //                     MonitorHandler monitorHandler = iterator.next();
            //                     if (monitorHandler.getRoot()
            //                             .getFile()
            //                             .getAbsolutePath()
            //                             .contains(file.getAbsolutePath())
            //                     ) {
            //                         iterator.remove();
            //                         monitorHandler.interrupt();
            //                     }
            //                 }
            //             }
            //         } catch (ClassNotFoundException e) {
            //             throw new RuntimeException(e);
            //         }
            //     }

            //     case EXIT -> {
            //         for (MonitorHandler monitorHandler : monitorHandlers) {
            //             monitorHandler.interrupt();
            //         }
            //         mainFrame.dispose();
            //         System.exit(0);
            //     }
            // }
        }
    }
}
