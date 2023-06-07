package Controller;

import Model.CustomFile;
import Model.Notification;
import Model.QueueResponse;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MonitorHandler extends Thread{
    private final CustomFile folder;
    private boolean isRunning = true;
    private final static String NOTIFICATION = "NOTIFICATION";
    private final QueueResponse queueResponse;
    private final ConnectServer connectServer;
    private final CustomFile root;

    public MonitorHandler(ConnectServer connectServer, CustomFile root, CustomFile file, QueueResponse queueResponse) {
        this.folder = file;
        this.queueResponse = queueResponse;
        this.root = root;
        this.connectServer = connectServer;

        start();
    }

    public CustomFile getRoot() {
        return root;
    }

    public CustomFile getFolder() {
        return folder;
    }

    @Override
    public void run() {
        if (folder.getFile().listFiles() != null) {
            List<File> fileList = List.of(Objects.requireNonNull(folder.getFile().listFiles()));
            for (File file : fileList) {
                if (file.isDirectory()) {
                    connectServer.addMonitorHandler(new MonitorHandler(
                            connectServer,
                            root,
                            new CustomFile(file),
                            queueResponse)
                    );
                }
            }
        }
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            String directoryPath = folder.getFile().getAbsolutePath();
            Path directory = Paths.get(directoryPath);

            directory.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.OVERFLOW);

            Set<WatchEvent<?>> watchEventSet = new HashSet<>();
            while (isRunning) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    isRunning = false;
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (!watchEventSet.contains(event)) {
                        watchEventSet.add(event);
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            File file = new File(folder.getFile().getAbsolutePath() + "\\" + event.context().toString());
                            LocalDateTime dateTime = LocalDateTime.now();
                            Notification notification = new Notification("CREATE", new CustomFile(file), root, dateTime, "");
                            if (file.isDirectory()) {
                                connectServer.addMonitorHandler(new MonitorHandler(connectServer, root, new CustomFile(file), queueResponse));
                            }
                            queueResponse.enqueueData(NOTIFICATION, notification);
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            File file = new File(folder.getFile().getAbsolutePath() + "\\" + event.context().toString());
                            LocalDateTime dateTime = LocalDateTime.now();
                            Notification notification = new Notification("DELETE", new CustomFile(file), root, dateTime, "");
                            queueResponse.enqueueData(NOTIFICATION, notification);
                        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                            File file = new File(folder.getFile().getAbsolutePath() + "\\" + event.context().toString());
                            LocalDateTime dateTime = LocalDateTime.now();
                            Notification notification = new Notification("MODIFY", new CustomFile(file), root, dateTime, "");
                            queueResponse.enqueueData(NOTIFICATION, notification);
                        }
                    }
                }

                // Reset và tiếp tục theo dõi sự kiện
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cancel() {
        isRunning = false;
    }
}
