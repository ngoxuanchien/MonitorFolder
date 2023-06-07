import javax.swing.*;
import java.awt.*;

public class NestedLabelExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel parentLabel = new JLabel("Parent Label");
        parentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        parentLabel.setLayout(new FlowLayout());

        JLabel childLabel = new JLabel("Child Label");
        childLabel.setForeground(Color.RED);

        // Đặt vị trí và kích thước của childLabel bên trong parentLabel
        childLabel.setBounds(50, 50, 100, 20);
        parentLabel.add(childLabel);

        frame.add(parentLabel);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}
