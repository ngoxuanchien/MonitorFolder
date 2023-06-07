package View;

import javax.swing.*;
import java.awt.*;

public class ResizeIcon extends ImageIcon {
    public ResizeIcon(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image originalImage = imageIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        this.setImage(resizedImage);
    }
}
