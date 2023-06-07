package Model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private CustomFile file;
    private CustomFile parent;
    private LocalDateTime date;
    private String description;

    public Notification(String title, CustomFile file, CustomFile parent, LocalDateTime date, String description) {
        this.title = title;
        this.file = file;
        this.parent = parent;
        this.date = date;
        this.description = description;
    }

    public CustomFile getParent() {
        return parent;
    }

    public void setParent(CustomFile parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CustomFile getFile() {
        return file;
    }

    public void setFile(CustomFile file) {
        this.file = file;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedDate = date.format(formatter);
        return "<html>" + formattedDate + " " + title + "<br>" + file.getFile().getPath() + "</html>";
    }
}
