package Controller.Strategy;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import Controller.MonitorHandler;
import Model.CustomFile;


public class RemoveStrategy implements IStrategy{
    private ObjectInputStream objectInputStream;   
    private List<MonitorHandler> monitorHandlers;

    public RemoveStrategy(ObjectInputStream objectInputStream, List<MonitorHandler> monitorHandlers){
        this.objectInputStream=objectInputStream;
        this.monitorHandlers=monitorHandlers;

    }

    public RemoveStrategy(List<MonitorHandler> monitorHandlers){
        this.monitorHandlers=monitorHandlers;

    }
    @Override
    public void execute() throws IOException, ClassNotFoundException {
        Object obj;
        try {
            obj = objectInputStream.readObject();

            if (obj instanceof CustomFile customFile) {
                File file = customFile.getFile().getCanonicalFile();
                Iterator<MonitorHandler> iterator = monitorHandlers.iterator();
                while (iterator.hasNext()) {
                    MonitorHandler monitorHandler = iterator.next();
                    if (monitorHandler.getRoot()
                            .getFile()
                            .getAbsolutePath()
                            .contains(file.getAbsolutePath())
                    ) {
                        iterator.remove();
                        monitorHandler.interrupt();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object obj;
        try {
            obj = inputStream.readObject();

            if (obj instanceof CustomFile customFile) {
                File file = customFile.getFile().getCanonicalFile();
                Iterator<MonitorHandler> iterator = monitorHandlers.iterator();
                while (iterator.hasNext()) {
                    MonitorHandler monitorHandler = iterator.next();
                    if (monitorHandler.getRoot()
                            .getFile()
                            .getAbsolutePath()
                            .contains(file.getAbsolutePath())
                    ) {
                        iterator.remove();
                        monitorHandler.interrupt();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
}
