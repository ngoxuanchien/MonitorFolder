package Model;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class CustomFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Icon icon;
    private File file;
    private final boolean directory;
    private final File parent;

    public CustomFile(Icon icon, File file) {
        this.icon = icon;
        this.file = file;
        directory = file.isDirectory();
        parent = file.getParentFile();
    }

    public CustomFile(File file) {
        this.file = file;
        this.icon = FileSystemView.getFileSystemView().getSystemIcon(file);
        directory = file.isDirectory();
        parent = file.getParentFile();
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isDirectory() {
        return directory;
    }

    public File getParentFile() {
        return parent;
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
