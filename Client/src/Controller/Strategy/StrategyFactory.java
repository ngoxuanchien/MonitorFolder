package Controller.Strategy;

import java.util.HashMap;

public class StrategyFactory {
        private static HashMap<String, IStrategy> Factory = new HashMap<String,IStrategy>();

        public static void FactoryInit()
        {
            Factory.put("EXIT", new ExitStrategy());
            Factory.put("ROOT", new RootStrategy());
            Factory.put("FOLDER", new FolderStrategy());
            Factory.put("MONITOR", new MonitorStrategy());
            Factory.put("MONITORFOLDER", new MonitorFolderStrategy());
            Factory.put("INFORMATION", new InformationStrategy());
            Factory.put("REMOVE", new RemoveStrategy());
        }

        public static IStrategy get(String Type)
        {
            return Factory.get(Type);
        }
    }
