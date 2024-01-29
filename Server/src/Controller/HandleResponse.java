package Controller;

import Model.*;
import View.ViewFrame;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HandleResponse extends Thread {
    private static final String ROOT = "ROOT";
    private static final String FOLDER = "FOLDER";
    private static final String MONITOR = "MONITOR";
    private static final String MONITORFOLDER = "MONITORFOLDER";
    private static final String INFORMATION = "INFORMATION";
    private static final String NOTIFICATION = "NOTIFICATION";
    private static final String COMPUTER = "COMPUTER";
    private final ViewFrame viewFrame;
    private final QueueResponse queueResponse;
    private boolean running = true;
    public HandleResponse(QueueResponse queueResponse, ViewFrame viewFrame) {
        this.queueResponse =queueResponse;
        this.viewFrame = viewFrame;
        start();
    }

    public void cancel() {
        running = false;
    }

    @Override
    public void run() {
        synchronized (viewFrame) {
            while (running) {
                try {
                    String action = queueResponse.dequeueAction();
                    Object object = queueResponse.dequeueObject();
                    Param param = new Param(object, viewFrame);
                    switch (action) {
                        case COMPUTER -> {
                            // Socket socket = (Socket) object;
                            // String ipAddress = socket.getInetAddress().toString();
                            // ipAddress = ipAddress.replace("/", "");
                            // Computer computer = new Computer(ipAddress, ipAddress, socket);

                            // viewFrame.addComputer(computer);

                            new ComputerStrategy().execute(param);
                        }

                        case ROOT -> {
                            // Computer computer = viewFrame.getComputerSelected();

                            // if (object instanceof List<?> receivedList) {
                            //     List<CustomFile> fileList = new ArrayList<>();
                            //     for (Object item: receivedList) {
                            //         if (item instanceof CustomFile) {
                            //             fileList.add((CustomFile) item);
                            //         }
                            //     }
                            //     computer.setRoots(fileList);
                            //     computer.setFileList(fileList);

                            //     viewFrame.addAllDrive(computer.getRoots());
                            //     viewFrame.addAllFolder(computer.getFileList());
                            //     viewFrame.setSelectedDrive(computer.getCurrentDrive());
                            // }

                            new RootStrategy().execute(param);
                        }

                        case FOLDER -> {
                            // Computer computer = viewFrame.getComputerSelected();

                            // if (object instanceof List<?> receivedList) {
                            //     List<CustomFile> fileList = new ArrayList<>();
                            //     for (Object item: receivedList) {
                            //         if (item instanceof CustomFile) {
                            //             fileList.add((CustomFile) item);
                            //         }
                            //     }

                            //     viewFrame.addAllFolder(fileList);
                            //     computer.setFileList(fileList);

                            // }
                            new FolderStrategy().execute(param);
                        }

                        case MONITORFOLDER -> {

                            // if (object instanceof List<?> receivedList) {
                            //     List<CustomFile> fileList = new ArrayList<>();
                            //     for (Object item: receivedList) {
                            //         if (item instanceof CustomFile) {
                            //             fileList.add((CustomFile) item);
                            //         }
                            //     }
                            //     viewFrame.addAllInformationFolder(fileList);
                            // }
                            new MonitorFolderStrategy().execute(param);
                        }

                        case INFORMATION -> {

                            // if (object instanceof List<?> receivedList) {
                            //     List<CustomFile> fileList = new ArrayList<>();
                            //     for (Object item: receivedList) {
                            //         if (item instanceof CustomFile) {
                            //             fileList.add((CustomFile) item);
                            //         }
                            //     }

                            //     viewFrame.addAllInformationFolder(fileList);
                            // }

                            new InformationStrategy().execute(param);
                        }

                        case NOTIFICATION -> {
                            // try {
                            //     Notification notification = (Notification) object;
                            //     viewFrame.addNotification(notification);
                            //     viewFrame.addHistory(notification);
                            //     viewFrame.setClient();
                            //     MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                            //     RequestClient requestClient = new RequestClient(monitorFolder.getComputer().getSocket());
                            //     requestClient.requestInformation(monitorFolder.getFolder());
                            // } catch (IOException e) {
                            //     throw new RuntimeException(e);
                            // }

                            new NotificationStrategy().execute(param);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private interface SStrategy {
        public void execute(Param param);
    }

    private class Param {

        public Object object;
        public ViewFrame viewFrame;
    
        public Param(Object object, ViewFrame viewFrame) {
            this.object = object;
            this.viewFrame = viewFrame;
        }
    }

    private class RootStrategy implements SStrategy {

        @Override
        public void execute(Param param) {
            Computer computer = viewFrame.getComputerSelected();
            if (param.object instanceof List<?> receivedList) {
                List<CustomFile> fileList = new ArrayList<>();
                for (Object item: receivedList) {
                    if (item instanceof CustomFile) {
                        fileList.add((CustomFile) item);
                    }
                }
               computer.setRoots(fileList);
               computer.setFileList(fileList);
               viewFrame.addAllDrive(computer.getRoots());
               viewFrame.addAllFolder(computer.getFileList());
               viewFrame.setSelectedDrive(computer.getCurrentDrive());
            }
        }
    }
    
    private class NotificationStrategy implements SStrategy {

        @Override
        public void execute(Param param) {
            try {
                Notification notification = (Notification) param.object;
                viewFrame.addNotification(notification);
                viewFrame.addHistory(notification);
                viewFrame.setClient();
                MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                RequestClient requestClient = new RequestClient(monitorFolder.getComputer().getSocket());
                requestClient.requestInformation(monitorFolder.getFolder());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private class InformationStrategy implements SStrategy {

        @Override
        public void execute(Param param) {
    
            if (param.object instanceof List<?> receivedList) {
                List<CustomFile> fileList = new ArrayList<>();
                for (Object item: receivedList) {
                    if (item instanceof CustomFile) {
                        fileList.add((CustomFile) item);
                    }
                }
                viewFrame.addAllInformationFolder(fileList);
            }
        } 
    }
    
    private class FolderStrategy implements SStrategy {

        @Override
        public void execute(Param param) {
            Computer computer = viewFrame.getComputerSelected();
    
            if (param.object instanceof List<?> receivedList) {
                List<CustomFile> fileList = new ArrayList<>();
                for (Object item: receivedList) {
                    if (item instanceof CustomFile) {
                        fileList.add((CustomFile) item);
                    }
                }
    
                viewFrame.addAllFolder(fileList);
                computer.setFileList(fileList);
    
            }
        } 
    }

    private class ComputerStrategy implements SStrategy {
        @Override
        public void execute(Param param) {
            Socket socket = (Socket) param.object;
            String ipAddress = socket.getInetAddress().toString();
            ipAddress = ipAddress.replace("/", "");
            Computer computer = new Computer(ipAddress, ipAddress, socket);
            viewFrame.addComputer(computer);
        }
        
    }

    private class MonitorFolderStrategy implements SStrategy {

        @Override
        public void execute(Param param) {
            if (param.object instanceof List<?> receivedList) {
                    List<CustomFile> fileList = new ArrayList<>();
                    for (Object item: receivedList) {
                        if (item instanceof CustomFile) {
                            fileList.add((CustomFile) item);
                        }
                    }
                    viewFrame.addAllInformationFolder(fileList);
                }
        }
        
    }

}
