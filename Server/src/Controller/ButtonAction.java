package Controller;

import Model.Computer;
import Model.CustomFile;
import Model.DefineColor;
import Model.MonitorFolder;
import View.ViewFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ButtonAction implements ActionListener {
    private final ViewFrame viewFrame;
    private RequestClient requestClient;
    public ButtonAction(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getName().equals("Monitor")) {
            Computer computer = viewFrame.getComputerSelected();
            CustomFile file = viewFrame.getSelectedFolder();
            LocalDateTime dateTime = LocalDateTime.now();
            MonitorFolder monitorFolder = new MonitorFolder(
                    computer,
                    dateTime,
                    file,
                    new ArrayList<>());
            requestClient = new RequestClient(computer.getSocket());
            viewFrame.addMonitorFolder(monitorFolder);
            try {
                requestClient.monitorFolder(file);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
