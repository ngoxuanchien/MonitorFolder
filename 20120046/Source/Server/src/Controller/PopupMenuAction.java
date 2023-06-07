package Controller;

import Model.Computer;
import Model.CustomFile;
import Model.MonitorFolder;
import View.ViewFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PopupMenuAction implements ActionListener {
    private final ViewFrame viewFrame;
    private final JMenuItem jMenuItem;

    public PopupMenuAction(ViewFrame viewFrame, JMenuItem jMenuItem) {
        this.viewFrame = viewFrame;
        this.jMenuItem = jMenuItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = jMenuItem.getName();
        switch (name) {
            case "DISCONNECT" -> {
                Computer computer = viewFrame.getComputerSelected();
                int result = viewFrame.showDialog("Do you want to disconnect " + computer.getSocket().getInetAddress().toString());
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        viewFrame.removeComputer(computer);
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.closeConnect();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            case "MONITOR" -> {
                Computer computer = viewFrame.getComputerSelected();
                CustomFile file = viewFrame.getSelectedFolder();
                LocalDateTime dateTime = LocalDateTime.now();
                MonitorFolder monitorFolder = new MonitorFolder(
                        computer,
                        dateTime,
                        file,
                        new ArrayList<>());
                viewFrame.addMonitorFolder(monitorFolder);
                try {
                    RequestClient requestClient = new RequestClient(computer.getSocket());
                    requestClient.monitorFolder(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            case "REMOVE" -> {
                MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                Computer computer = monitorFolder.getComputer();
                int result = viewFrame.showDialog("Do you want to remove monitor " + monitorFolder.getFolder().getFile().getAbsolutePath());
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        viewFrame.removeMonitorFolder(monitorFolder);
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.removeMonitorFolder(monitorFolder.getFolder());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            case "INFORMATION" -> {
                MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                CustomFile file = monitorFolder.getFolder();
                Computer computer = monitorFolder.getComputer();
                try {
                    if (file != null) {
                        viewFrame.setClient();
                        viewFrame.setVisibleInformation();
                        RequestClient requestClient = new RequestClient(computer.getSocket());
                        requestClient.requestInformation(file);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
    }
}
