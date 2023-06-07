package View;

import Model.CustomFile;
import Model.MonitorFolder;

import javax.management.monitor.Monitor;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class MonitorJList extends JList<MonitorFolder> {
    public MonitorJList() {
        setModel(new DefaultListModel<>());
        setCellRenderer(new MonitorCellRenderer());
    }

    private static class MonitorCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (renderer instanceof JLabel label) {
                MonitorFolder monitorFolder = (MonitorFolder) value;
                CustomFile customFile = monitorFolder.getFolder();

                label.setText(customFile.getFile().getName());
                Icon icon = customFile.getIcon();
                label.setIcon(icon);

                if (customFile.getParentFile() == null) {
                    label.setText(customFile.getFile().getPath());
                }
            }

            return renderer;
        }
    }
}
