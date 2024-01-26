package Controller;
import View.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectAction implements ActionListener {
    MainFrame mainFrame;
    ConnectServer connectServer;
    public ConnectAction(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            mainFrame.setVisible(false);
            connectServer = new ConnectServer(mainFrame.getTextField(), mainFrame);
            connectServer.ResponseServer();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
