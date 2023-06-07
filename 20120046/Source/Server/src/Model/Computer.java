package Model;

import java.io.File;
import java.net.Socket;
import java.util.List;

public class Computer {
    private String name;
    private String address;
    private Socket socket;
    private CustomFile currentFolder;
    private CustomFile currentDrive;
    private List<CustomFile> roots;
    private List<CustomFile> fileList;
    public Computer() {
        this.name = null;
        this.address = null;
        this.socket = null;
        this.currentFolder = null;
        this.currentDrive = null;
        this.roots = null;
        this.fileList = null;
    }

    public Computer(String name, String address, Socket socket) {
        super();
        this.name = name;
        this.address = address;
        this.socket = socket;
    }

    public Computer(String name, String address, Socket socket, CustomFile currentFolder, CustomFile currentDrive,List<CustomFile> roots, List<CustomFile> fileList) {
        super();
        this.name = name;
        this.address = address;
        this.currentFolder = currentFolder;
        this.socket = socket;
        this.roots = roots;
        this.currentDrive = currentDrive;
        this.fileList = fileList;
    }

    public CustomFile getCurrentDrive() {
        return currentDrive;
    }

    public void setCurrentDrive(CustomFile currentDrive) {
        this.currentDrive = currentDrive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public List<CustomFile> getFileList() {
        return fileList;
    }

    public List<CustomFile> getRoots() {
        return roots;
    }

    public void setRoots(List<CustomFile> roots) {
        this.roots = roots;
    }

    public void setFileList(List<CustomFile> fileList) {
        this.fileList = fileList;
    }

    public CustomFile getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(CustomFile currentFolder) {
        this.currentFolder = currentFolder;
    }
}
