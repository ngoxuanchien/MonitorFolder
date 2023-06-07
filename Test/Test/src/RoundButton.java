import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class RoundButton extends JButton {

    private final int size = 25; // Kích thước của RoundIconButton

    public RoundButton(Icon icon) {
        super(icon);

        // Loại bỏ viền và đường viền của JButton
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        // Đặt kích thước cho JButton
        setPreferredSize(new Dimension(size, size));
    }

    // Vẽ hình tròn bo góc cho JButton
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
//            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, size - 1, size - 1, 10, 10);

        super.paintComponent(g2);
        g2.dispose();
    }

    // Vẽ đường viền cho JButton
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(0, 0, size - 1, size - 1, 10, 10);

        g2.dispose();
    }

    // Tạo hình tròn cho JButton khi có kích thước thay đổi
    Shape shape;

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        }
        return shape.contains(x, y);
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
        JButton roundIconButton = new RoundButton(resizedIcon);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(roundIconButton);
        frame.add(panel);

        frame.setVisible(true);
    }
}
