package View;

import javax.swing.*;
import java.awt.*;

public class MiniButton extends JButton {
    public MiniButton(ImageIcon icon) {
        super(null, icon);
        this.setPreferredSize(new Dimension(25, 25));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBackground(new Color(0, 0, 0, 0));
    }
}
