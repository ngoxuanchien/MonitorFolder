import javax.swing.*;

public class CustomLabelExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Label Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JLabel label = new JLabel("<html>Đoạn văn bản thứ nhất<br>Đoạn văn bản thứ hai</html>");
        frame.add(label);

        frame.setVisible(true);
    }
}
