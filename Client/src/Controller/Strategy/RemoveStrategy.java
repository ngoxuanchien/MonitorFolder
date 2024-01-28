package Controller.Strategy;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import Controller.MonitorHandler;
import Model.CustomFile;


public class RemoveStrategy implements IStrategy{

    @Override
    public void execute(Param param) throws IOException, ClassNotFoundException {
        Object obj;
        try {
            obj = param.objectInputStream.readObject();

            if (obj instanceof CustomFile customFile) {
                File file = customFile.getFile().getCanonicalFile();
                Iterator<MonitorHandler> iterator = param.monitorHandlers.iterator();
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
