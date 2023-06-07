import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleOpaqueButton extends JButton {

    private Color backgroundColor; // Màu nền của JButton
    private boolean isOpaqueEnabled; // Trạng thái bật/tắt setOpaque

    public ToggleOpaqueButton(String label, Color backgroundColor) {
        super(label);
        this.backgroundColor = backgroundColor;
        this.isOpaqueEnabled = true;

        // Đặt kích thước cho JButton
        setPreferredSize(new Dimension(20, 20));

        // Đăng ký ActionListener để bắt sự kiện click
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleOpaque(); // Bật/tắt setOpaque khi JButton được click
            }
        });
    }

    // Bật/tắt setOpaque và thay đổi màu nền của JButton
    private void toggleOpaque() {
        isOpaqueEnabled = !isOpaqueEnabled;
        setOpaque(isOpaqueEnabled);
        repaint(); // Vẽ lại JButton để cập nhật hiển thị
    }

    // Vẽ lại JButton khi có sự thay đổi trạng thái bật/tắt setOpaque
    protected void paintComponent(Graphics g) {
        if (isOpaqueEnabled) {
            super.setBackground(backgroundColor);
        } else {
            super.setBackground(null);
        }
        super.paintComponent(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("Images/desktop.png");

        // Thay đổi kích thước biểu tượng
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        Color backgroundColor = Color.GRAY;
        ToggleOpaqueButton toggleButton = new ToggleOpaqueButton("Toggle", backgroundColor);
        toggleButton.setIcon(resizedIcon);
        frame.add(toggleButton);

        frame.setVisible(true);
    }
}
