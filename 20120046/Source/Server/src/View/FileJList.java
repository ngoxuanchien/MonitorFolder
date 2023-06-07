package View;

import Model.CustomFile;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class FileJList extends JList<CustomFile> {
    public FileJList() {
        setModel(new DefaultListModel<>());
        setCellRenderer(new FileCellRenderer());
    }

    private static class FileCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (renderer instanceof JLabel label) {
                CustomFile customFile = (CustomFile) value;
                File file = customFile.getFile();

                label.setText(file.getName());
                Icon icon = customFile.getIcon();
                label.setIcon(icon);

                if (file.getParent() == null) {
                    label.setText(file.getPath());
                }

            }

            return renderer;
        }
    }
}
