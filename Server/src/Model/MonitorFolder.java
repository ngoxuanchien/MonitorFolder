package Model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class MonitorFolder {
    private Computer computer;
    private LocalDateTime date;
    private CustomFile folder;
    private List<Notification> history;
    public MonitorFolder(Computer computer, LocalDateTime date, CustomFile folder, List<Notification> history) {
        this.computer = computer;
        this.date = date;
        this.folder = folder;
        this.history = history;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CustomFile getFolder() {
        return folder;
    }

    public void setFolder(CustomFile folder) {
        this.folder = folder;
    }

    public List<Notification> getHistory() {
        return history;
    }

    public void setHistory(List<Notification> history) {
        this.history = history;
    }
    public void addNotification(Notification notification) {
        history.add(0, notification);
    }
}
