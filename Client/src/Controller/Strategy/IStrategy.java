package Controller.Strategy;
import java.io.IOException;
import java.io.ObjectInputStream;


public interface IStrategy {
    public void execute() throws IOException, ClassNotFoundException;
    public void execute(ObjectInputStream inputStream) throws IOException, ClassNotFoundException;
}
