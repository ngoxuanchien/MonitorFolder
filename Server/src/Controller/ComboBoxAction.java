package Controller;

import Model.Computer;
import Model.CustomFile;
import View.ViewFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ComboBoxAction implements ActionListener {
    ViewFrame viewFrame;
    public ComboBoxAction(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JComboBox<?> comboBox) {
            if (comboBox.getSelectedItem() instanceof CustomFile file) {
                Computer computer = viewFrame.getComputerSelected();
                try {
                    RequestClient requestClient = new RequestClient(computer.getSocket());
                    requestClient.requestFolder(file);
                    computer.setCurrentDrive(file);
                    computer.setCurrentFolder(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
