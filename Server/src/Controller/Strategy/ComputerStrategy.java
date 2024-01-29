package Controller.Strategy;

import java.net.Socket;

import Model.Computer;

public class ComputerStrategy implements IStrategy {

    @Override
    public void execute(Param param) {
        Socket socket = (Socket) param.object;
        String ipAddress = socket.getInetAddress().toString();
        ipAddress = ipAddress.replace("/", "");
        Computer computer = new Computer(ipAddress, ipAddress, socket);

        //param.viewFrame.addComputer(computer);
    }
    
}
