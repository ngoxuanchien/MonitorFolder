package Controller.POPUP;

import java.util.HashMap;

public class Factory {

    private static HashMap<String, popUpStrategy> Factory = new HashMap<String, popUpStrategy>();

    public static void FactoryInit()
    {
        Factory.put("DISCONNECT", new DisconnectStrategy());
        Factory.put("DELETE", new DeleteStrategy());
        Factory.put("NEW FILE", new NewFileStrategy());
        Factory.put("NEW FOLDER", new NewFolderStrategy());
        Factory.put("MONITOR", new MonitorStrategy());
        Factory.put("REMOVE", new RemoveStrategy());
        Factory.put("INFORMATION", new InformationStrategy());
    }

    public static popUpStrategy Get(String type)
    {
        return Factory.get(type);
    }
    
}
