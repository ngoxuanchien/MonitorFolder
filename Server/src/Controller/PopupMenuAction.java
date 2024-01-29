package Controller;

import Model.Computer;
import Model.CustomFile;
import Model.MonitorFolder;
import View.ViewFrame;

import javax.swing.*;

import Controller.POPUP.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupMenuAction implements ActionListener {
    private final ViewFrame viewFrame;
    private final JMenuItem jMenuItem;

    public PopupMenuAction(ViewFrame viewFrame, JMenuItem jMenuItem) {
        this.viewFrame = viewFrame;
        this.jMenuItem = jMenuItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = jMenuItem.getName();
        Factory.Get(name).execute(viewFrame);
        // switch (name) {
        //     case "DISCONNECT" -> {
        //         new DisconnectStrategy().execute(viewFrame);
        //     }

        //     case "DELETE" -> {

        //         new DeleteStrategy().execute(viewFrame);
        //     }

        //     case "NEW FILE" -> {
        //             new NewFileStrategy().execute(viewFrame);
        //        }

        //     case "NEW FOLDER" -> {
        //         new NewFolderStrategy().execute(viewFrame);
        //     }

        //     case "MONITOR" -> {
        //         new MonitorStrategy().execute(viewFrame);
        //     }

        //     case "REMOVE" -> {
        //         new RemoveStrategy().execute(viewFrame);
        //     }

        //     case "INFORMATION" -> {
        //         new InformationStrategy().execute(viewFrame);
        //     }

        // }
    }
}
