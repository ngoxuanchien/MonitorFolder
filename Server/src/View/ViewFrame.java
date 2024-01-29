package View;

import Controller.*;
import Model.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ViewFrame extends JFrame {
    JLabel serverLbl;
    DefaultListModel<Computer> computerListModel;
    DefaultListModel<Notification> notificationListModel;
    DefaultComboBoxModel<CustomFile> fileComboBoxModel;
    DefaultComboBoxModel<CustomFile> driveComboBoxModel;
    DefaultListModel<CustomFile> fileListModel;
    List<CustomFile> originalList;

    DefaultListModel<CustomFile> informationListModel;
    DefaultListModel<MonitorFolder> monitorListMode;
    DefaultListModel<Notification> historyList;
    JPanel informationPanel;
    ComputerJList computerJList;

    JTextField filterText;
    MonitorJList monitorJList;
    public JPopupMenu computerMenu;
    public JPopupMenu folderMenu;
    public JPopupMenu monitorMenu;
    public ViewFrame() {
        this.init();
    }

    private void ComputerMenu() {
        computerMenu = new JPopupMenu();
        JMenuItem disconnect = new JMenuItem("Disconnect");
        disconnect.setName("DISCONNECT");
        disconnect.addActionListener(new PopupMenuAction(this, disconnect));
        computerMenu.add(disconnect);
    }

    private void MonitorMenu() {
        monitorMenu = new JPopupMenu();
        JMenuItem information = new JMenuItem("Information");
        information.setName("INFORMATION");
        information.addActionListener(new PopupMenuAction(this, information));
        JMenuItem remove = new JMenuItem("Remove");
        remove.setName("REMOVE");
        remove.addActionListener(new PopupMenuAction(this, remove));
        monitorMenu.add(information);
        monitorMenu.add(remove);
    }

    private void FolderMenu() {
        folderMenu = new JPopupMenu();
        JMenuItem monitor = new JMenuItem("Monitor");
        monitor.setName("MONITOR");
        monitor.addActionListener(new PopupMenuAction(this, monitor));

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.setName("DELETE");
        deleteItem.addActionListener(new PopupMenuAction(this, deleteItem));

        JMenuItem newFileItem = new JMenuItem("New file");
        newFileItem.setName("NEW FILE");
        newFileItem.addActionListener(new PopupMenuAction(this, newFileItem));

        JMenuItem newFolderItem = new JMenuItem("New folder");
        newFolderItem.setName("NEW FOLDER");
        newFolderItem.addActionListener(new PopupMenuAction(this, newFolderItem));



        folderMenu.add(monitor);
        folderMenu.add(deleteItem);
        folderMenu.add(newFileItem);
        folderMenu.add(newFolderItem);
    }

    private JPanel WestPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.decode(DefineColor.LIGHT_BLUE), 1);
        panel.setBorder(border);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout());
        westPanel.setPreferredSize(new Dimension(30, -1));
        westPanel.setBackground(Color.decode(DefineColor.DARK_BLUE));
//        westPanel.setBorder(border);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.decode(DefineColor.DARK_BLUE));

        JLabel label = new JLabel("Connected");
        label.setFont(new Font(label.getFont().toString(), Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(6, 10, 6, 0));

        centerPanel.setPreferredSize(new Dimension(200, -1));
        computerJList = new ComputerJList();
        computerJList.setName(DefineString.COMPUTERS);
        computerListModel = (DefaultListModel<Computer>) computerJList.getModel();
        JListAction jListAction = new JListAction(this, computerJList);
        computerJList.addMouseListener(jListAction);
        ComputerMenu();
        JScrollPane scrollPane = new JScrollPane(computerJList);

        centerPanel.add(label, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        ResizeIcon icon = new ResizeIcon("Images/desktop.png");
        MiniButton button = new MiniButton(icon);
        button.setOpaque(true);
        button.setBackground(Color.decode(DefineColor.BLUE));
        button.addActionListener(e -> {
            if (!button.isOpaque()) {
                button.setOpaque(true);
                button.setBackground(Color.decode(DefineColor.BLUE));
                centerPanel.setVisible(true);
            } else {
                button.setOpaque(false);
                button.setBackground(new Color(0, 0, 0, 0));
                centerPanel.setVisible(false);
            }
        });
        westPanel.add(button);

        panel.add(westPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel EastPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.decode(DefineColor.LIGHT_BLUE), 1);
        panel.setBorder(border);

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new FlowLayout());
        eastPanel.setPreferredSize(new Dimension(30, -1));
        eastPanel.setBackground(Color.decode(DefineColor.DARK_BLUE));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(300, -1));
        centerPanel.setBackground(Color.decode(DefineColor.DARK_BLUE));

        JLabel label = new JLabel("Notifications");
        label.setBorder(new EmptyBorder(6, 10, 6, 0));
        label.setFont(new Font(label.getFont().toString(), Font.BOLD, 16));
        label.setForeground(Color.WHITE);

        NotificationJList notificationJList = new NotificationJList();
        notificationListModel = (DefaultListModel<Notification>) notificationJList.getModel();
        JScrollPane scrollPane = new JScrollPane(notificationJList);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(label, BorderLayout.NORTH);

        ResizeIcon icon = new ResizeIcon("Images/notification.png");
        MiniButton button = new MiniButton(icon);
        button.setOpaque(true);
        button.setBackground(Color.decode(DefineColor.BLUE));
        button.addActionListener(e -> {

            if (!button.isOpaque()) {
                button.setOpaque(true);
                button.setBackground(Color.decode(DefineColor.BLUE));
                centerPanel.setVisible(true);
            } else {
                button.setOpaque(false);
                button.setBackground(new Color(0, 0, 0, 0));
                centerPanel.setVisible(false);
            }
        });
        eastPanel.add(button);

        panel.add(eastPanel, BorderLayout.EAST);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel LeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode(DefineColor.DARK_BLUE));

        Border border = BorderFactory.createLineBorder(Color.decode(DefineColor.LIGHT_BLUE), 1);
        panel.setBorder(border);

        JLabel label = new JLabel("Folders");
        label.setFont(new Font(label.getFont().toString(), Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(6, 10, 6, 0));

        JLabel filterPanel = new JLabel();
        filterPanel.setLayout(new GridLayout(1, 2));

        driveComboBoxModel = new DefaultComboBoxModel<>();
        JComboBox<CustomFile> comboBox = new JComboBox<>(driveComboBoxModel);
        ComboBoxAction comboBoxAction = new ComboBoxAction(this);
        comboBox.addActionListener(comboBoxAction);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        JLabel filterLabel = new JLabel("Filter");
        filterLabel.setFont(new Font(filterLabel.getFont().toString(), Font.BOLD, 16));
        filterLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        filterText = new JTextField();
        filterText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
        });
        tempPanel.add(filterLabel, BorderLayout.WEST);
        tempPanel.add(filterText, BorderLayout.CENTER);

        filterPanel.add(comboBox);
        filterPanel.add(tempPanel);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        northPanel.setBackground(Color.decode(DefineColor.DARK_BLUE));
        northPanel.add(label);
        northPanel.add(filterPanel);

        FileJList fileJList = new FileJList();
        fileJList.setName(DefineString.FOLDERS);
        JListAction ac = new JListAction(this, fileJList);
        fileJList.addMouseListener(ac);
        fileListModel = (DefaultListModel<CustomFile>) fileJList.getModel();
        originalList = new ArrayList<>();
        FolderMenu();
        JScrollPane scrollPane = new JScrollPane(fileJList);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void filterList() {
        String text = filterText.getText().toLowerCase();
        List<CustomFile> filterList = new ArrayList<>();
        if (text.equals("")) {
            fileListModel.clear();
            fileListModel.addAll(originalList);
        } else {
            for (CustomFile file : originalList) {
                if (file.getFile().getName().toLowerCase().contains(text)) {
                    filterList.add(file);
                }
            }

            fileListModel.removeAllElements();
            fileListModel.addAll(filterList);
        }

    }

    JLabel clientLabel;
    private JPanel InformationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(-1, 450));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        clientLabel = new JLabel("Client");
        ImageIcon icon = new ResizeIcon("Images/minimize.png");
        MiniButton miniButton = new MiniButton(icon);
        miniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
            }
        });
        northPanel.add(clientLabel, BorderLayout.CENTER);
        northPanel.add(miniButton, BorderLayout.EAST);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        JLabel leftLabel = new JLabel("Folder: ");
        FileJList fileJList = new FileJList();
        fileJList.setName("INFORMATION");
        informationListModel = (DefaultListModel<CustomFile>) fileJList.getModel();
        JListAction jListAction = new JListAction(this, fileJList);
        fileJList.addMouseListener(jListAction);
        JScrollPane leftScrollPanel = new JScrollPane(fileJList);
        leftPanel.add(leftLabel, BorderLayout.NORTH);
        leftPanel.add(leftScrollPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        NotificationJList historyJlist = new NotificationJList();
        historyList = (DefaultListModel<Notification>) historyJlist.getModel();
        JLabel rightLabel = new JLabel("History");
        JScrollPane rightScrollPanel = new JScrollPane(historyJlist);
        rightPanel.add(rightLabel, BorderLayout.NORTH);
        rightPanel.add(rightScrollPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        panel.setVisible(false);
        return panel;
    }

    private JPanel RightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode(DefineColor.DARK_BLUE));

        Border border = BorderFactory.createLineBorder(Color.decode(DefineColor.LIGHT_BLUE), 1);
        panel.setBorder(border);

        JLabel label = new JLabel("Monitoring");
        label.setFont(new Font(label.getFont().toString(), Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(6, 10, 6, 0));

        monitorJList = new MonitorJList();
        monitorJList.setName("MONITORFOLDER");
        monitorListMode = (DefaultListModel<MonitorFolder>) monitorJList.getModel();
        JListAction jListAction = new JListAction(this, monitorJList);
        monitorJList.addMouseListener(jListAction);
        MonitorMenu();
        JScrollPane scrollPane = new JScrollPane(monitorJList);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        informationPanel = InformationPanel();
//        informationPanel.setVisible(false);
        centerPanel.add(informationPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel CenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode(DefineColor.LIGHT_BLUE));

        Border border = BorderFactory.createLineBorder(Color.decode(DefineColor.LIGHT_BLUE), 1);
        panel.setBorder(border);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        fileComboBoxModel = new DefaultComboBoxModel<>();
        JComboBox<CustomFile> comboBox = new JComboBox<>(fileComboBoxModel);
        JButton button = new JButton("Monitor");
        button.setName("Monitor");
        ButtonAction buttonAction = new ButtonAction(this);
        button.addActionListener(buttonAction);
        northPanel.add(comboBox, BorderLayout.CENTER);
        northPanel.add(button, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        centerPanel.add(LeftPanel());
        centerPanel.add(RightPanel());

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    private void init() {
        serverLbl = new JLabel();
        serverLbl.setText("Server: " + IPAddress.getIPAddress());
        serverLbl.setBorder(new EmptyBorder(0, 30, 0, 0));
        serverLbl.setForeground(Color.WHITE);

        Border border = BorderFactory.createLineBorder(Color.decode(DefineColor.LIGHT_BLUE), 1);

        JPanel northPnl = new JPanel();
        northPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPnl.add(serverLbl);
        northPnl.setPreferredSize(new Dimension(-1, 30));
        northPnl.setBackground(Color.decode(DefineColor.DARK_BLUE));
        northPnl.setBorder(border);

        JPanel southPnl = new JPanel();
        southPnl.setBorder(border);
        southPnl.setPreferredSize(new Dimension(-1, 30));
        southPnl.setBackground(Color.decode(DefineColor.DARK_BLUE));

        this.setLayout(new BorderLayout());
        this.add(northPnl, BorderLayout.NORTH);
        this.add(WestPanel(), BorderLayout.WEST);
        this.add(EastPanel(), BorderLayout.EAST);
        this.add(southPnl, BorderLayout.SOUTH);
        this.add(CenterPanel(), BorderLayout.CENTER);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("Server");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addComputer(Computer computer) {
        computerListModel.addElement(computer);
    }

    public void addAllFolder(List<CustomFile> list) {
        fileListModel.clear();
        fileListModel.addAll(list);

        originalList.clear();
        originalList.addAll(list);

        fileComboBoxModel.removeAllElements();
        for (CustomFile file : list) {
            if (file.isDirectory()) {
                fileComboBoxModel.addElement(file);
            }
        }
    }

    public void addAllDrive(List<CustomFile> list) {
        driveComboBoxModel.removeAllElements();
        driveComboBoxModel.addAll(list);
    }

    public Computer getComputerSelected() {
        return computerJList.getSelectedValue();
    }
    public void setSelectedDrive(CustomFile drive) {
        driveComboBoxModel.setSelectedItem(drive);
        fileComboBoxModel.setSelectedItem(drive);

    }

    public void setSelectedFolder(CustomFile file) {
        fileComboBoxModel.setSelectedItem(file);

    }

    public CustomFile getSelectedFolder() {
        return (CustomFile) fileComboBoxModel.getSelectedItem();

    }
    public void addMonitorFolder(MonitorFolder monitorFolder) {
        monitorListMode.addElement(monitorFolder);

    }

    public MonitorFolder getMonitorFolderSelection() {
        return monitorJList.getSelectedValue();
    }

    public void addAllInformationFolder(List<CustomFile> fileList) {
        informationListModel.removeAllElements();
        informationListModel.addAll(fileList);
        this.repaint();

        for (CustomFile file : fileList) {
            System.out.println(file.toString());
        }
        System.out.println(1);

    }

    public void addNotification(Notification notification) throws InterruptedException {
        notificationListModel.add(0, notification);
        for (int i = 0; i < monitorListMode.size(); i++) {
            MonitorFolder monitorFolder = monitorListMode.get(i);
            if (monitorFolder.getFolder().getFile().getAbsolutePath().equals(notification.getParent().getFile().getAbsolutePath())) {
                monitorFolder.addNotification(notification);
            }
        }
        int index = monitorJList.getSelectedIndex();
        monitorJList.setSelectedIndex(index);

        MouseEvent event = new MouseEvent(
                monitorJList, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
                0, monitorJList.getSelectedIndex() * monitorJList.getCellBounds(0, 0).height,
                0, 1, false, MouseEvent.BUTTON1);
        monitorJList.dispatchEvent(event);

    }

    public void updateHistory(List<Notification> history) {
        historyList.removeAllElements();
        historyList.addAll(history);
    }

    public int showDialog(String message) {
        this.repaint();

        return JOptionPane.showConfirmDialog(null, message, "Confirm?", JOptionPane.YES_NO_OPTION);
    }

    public String requestName(String message) {
        this.repaint();
        return JOptionPane.showInputDialog(message);
    }

    public void removeMonitorFolder(MonitorFolder monitorFolder) {
        if (monitorJList.getSelectedValue() == monitorFolder) {
            informationPanel.setVisible(false);
        }
        monitorListMode.removeElement(monitorFolder);


    }

    public void removeComputer(Computer computer) {
        if (computerJList.getSelectedValue() == computer) {
            fileComboBoxModel.removeAllElements();
            fileListModel.clear();
            originalList.clear();
            driveComboBoxModel.removeAllElements();

            int i = 0;
            while (i < monitorListMode.size()) {
                if (monitorListMode.get(i).getComputer() == computer) {
                    removeMonitorFolder(monitorListMode.get(i));
                } else {
                    i++;
                }
            }
        }

        computerListModel.removeElement(computer);

        this.repaint();
    }

    public void setVisibleInformation() {
        informationPanel.setVisible(true);
    }

    public void addHistory(Notification notification) {
        MonitorFolder monitorFolder = monitorJList.getSelectedValue();
        if (monitorFolder != null && monitorFolder.getFolder().getFile().equals(notification.getParent().getFile())) {
            historyList.add(0, notification);
        }

    }

    public void setClient() {
        MonitorFolder monitorFolder = monitorJList.getSelectedValue();
        clientLabel.setText("Client: " + monitorFolder.getComputer().getAddress());

    }
}
