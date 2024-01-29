package Controller;

import Model.CustomFile;
import Model.DefineString;
import Model.Computer;
import Model.MonitorFolder;
import View.ViewFrame;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class JListAction implements MouseListener {
    private final ViewFrame viewFrame;
    JList<?> jList;
    RequestClient requestClient;

    public JListAction(ViewFrame viewFrame, JList<?> jList) {
        this.viewFrame = viewFrame;
        this.jList = jList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (jList.getSelectedIndex() >=0 ) {
            String name = jList.getName();
            switch (name) {
                case DefineString.COMPUTERS -> {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        viewFrame.computerMenu.show(jList, e.getX(), e.getY());
                    } else {
                        Computer computer = (Computer) jList.getSelectedValue();
                        if (computer.getRoots() == null || e.getClickCount() == 2) {
                            try {
                                requestClient = new RequestClient(computer.getSocket());
                                requestClient.requestRoots();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            viewFrame.addAllDrive(computer.getRoots());
                            viewFrame.addAllFolder(computer.getFileList());
                            viewFrame.setSelectedDrive(computer.getCurrentDrive());
                        }
                    }
                }

                case DefineString.FOLDERS -> {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        viewFrame.folderMenu.show(jList, e.getX(), e.getY());
                    } else {
                        if (e.getClickCount() >= 2) {
                            Computer computer = viewFrame.getComputerSelected();
                            requestClient = new RequestClient(computer.getSocket());
                            CustomFile file = (CustomFile) jList.getSelectedValue();
                            try {
                                requestClient.requestFolder(file);

                                if (file.getParentFile() == null) {
                                    computer.setCurrentDrive(file);
                                    viewFrame.setSelectedDrive(file);
                                }
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            CustomFile file = (CustomFile) jList.getSelectedValue();
                            if (file != null) {
                                viewFrame.setSelectedFolder(file);
                            }

                        }
                    }

                }

                case DefineString.MONITORFOLDER -> {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        viewFrame.monitorMenu.show(jList, e.getX(), e.getY());
                    } else {
                        if (e.getClickCount() == 2) {
                            MonitorFolder monitorFolder = (MonitorFolder) jList.getSelectedValue();
                            CustomFile file = monitorFolder.getFolder();
                            Computer computer = monitorFolder.getComputer();
                            requestClient = new RequestClient(computer.getSocket());
                            viewFrame.updateHistory(monitorFolder.getHistory());
                            try {
                                requestClient.requestMonitorFolder(file);
                                viewFrame.setClient();
                                viewFrame.setVisibleInformation();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }

                }

                case DefineString.INFORMATION -> {
                    if (e.getClickCount() == 2) {
                        MonitorFolder monitorFolder = viewFrame.getMonitorFolderSelection();
                        CustomFile file = (CustomFile) jList.getSelectedValue();
                        Computer computer = monitorFolder.getComputer();
                        requestClient = new RequestClient(computer.getSocket());
                        try {
                            if (file != null) {
                                if (file.isDirectory()) {
                                    requestClient.requestInformation(file);
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
