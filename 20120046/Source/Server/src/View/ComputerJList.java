package View;

import Model.Computer;

import javax.swing.*;
import java.awt.*;

public class ComputerJList extends JList<Computer> {
    public ComputerJList() {
        setModel(new DefaultListModel<>());
        setCellRenderer(new ComputerCellRenderer());
    }

    private static class ComputerCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (renderer instanceof JLabel label) {
                Computer computer = (Computer) value;

                String lbName = computer.getName();
                if (lbName == null) {
                    label.setText(computer.getAddress());
                } else {
                    label.setText(lbName);
                }

                Image image = new ImageIcon("Images/desktop.png").getImage();
                image = image.getScaledInstance(15, -1, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(image);
                label.setIcon(icon);
            }

            return renderer;
        }
    }

}
