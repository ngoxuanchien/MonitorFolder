package Controller.Strategy;
import java.io.IOException;

public interface IStrategy {
    public void execute(Param param) throws IOException, ClassNotFoundException;
}
