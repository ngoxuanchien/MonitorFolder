import Controller.Strategy.StrategyFactory;
import View.MainFrame;

public class Client {
    public static void main(String[] args) {
        new MainFrame();
        StrategyFactory.FactoryInit();
    }
}
