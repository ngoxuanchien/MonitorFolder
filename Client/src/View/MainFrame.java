package View;
import Controller.ConnectAction;
import Controller.Strategy.StrategyFactory;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JTextField textField;
    ConnectAction connectAction;
    public MainFrame() {
        this.init();
    }

    private void init() {
        JLabel label = new JLabel("Server: ");
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
//        textField.setText("192.168.1.5");
        JButton button = new JButton("Connect");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(button, BorderLayout.EAST);
        panel.add(textField, BorderLayout.CENTER);
        connectAction = new ConnectAction(this);
        button.addActionListener(connectAction);

        this.add(panel);
        this.setTitle("Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public String getTextField() {
        return textField.getText();
    }
}
