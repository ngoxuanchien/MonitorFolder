package View;

import Model.Notification;

import javax.swing.*;
import java.awt.*;

public class NotificationJList extends JList<Notification> {
    public NotificationJList() {
        setModel(new DefaultListModel<>());
        setCellRenderer(new NotificationCellRenderer());
    }

    private static class NotificationCellRenderer extends DefaultListCellRenderer {
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
                Notification notification = (Notification) value;

                label.setText(notification.toString());
                Image image = null;
                String title = notification.getTitle();
                switch (title) {
                    case "DELETE" -> image = new ImageIcon("Images/delete.png").getImage();

                    case "MODIFY" -> image = new ImageIcon("Images/update.png").getImage();

                    case "CREATE" -> image = new ImageIcon("Images/create.png").getImage();

                }
                assert image != null;
                image = image.getScaledInstance(15, -1, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(image);
                label.setIcon(icon);
            }

            return renderer;
        }
    }
}
