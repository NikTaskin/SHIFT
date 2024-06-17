package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientUI extends JFrame implements ActionListener {
    private static final Logger LOGGER = LogManager.getLogger(ClientUI.class);
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private static final String FRONT = "Arial";
    private static final int FRONT_SIZE = 14;
    public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final JTextArea log = new JTextArea();
    private final JTextField fieldInput = new JTextField();
    private final JList<String> userList = new JList<>(new DefaultListModel<>());
    private ClientConnectionManager clientConnectionManager;
    private String nickname;
    private String ipAddress = "";
    private String port = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientUI::new);
    }

    public ClientUI() {
        super("Simple chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        log.setFont(new Font(FRONT, Font.PLAIN, FRONT_SIZE));
        JScrollPane logScrollPane = new JScrollPane(log);

        userList.setFont(new Font(FRONT, Font.PLAIN, FRONT_SIZE));
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(150, HEIGHT));
        userScrollPane.setBorder(BorderFactory.createTitledBorder("Chat participants:"));

        JPanel inputPanel = new JPanel(new BorderLayout());
        fieldInput.setFont(new Font(FRONT, Font.PLAIN, FRONT_SIZE));
        inputPanel.add(fieldInput, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font(FRONT, Font.PLAIN, FRONT_SIZE));
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(logScrollPane, BorderLayout.CENTER);
        add(userScrollPane, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        fieldInput.addActionListener(this);

        askForIpaddress();
        askForPort();
        askForNickname();
        connect();
        setVisible(true);
    }

    public void askForNickname() {
        nickname = JOptionPane.showInputDialog(this, "Enter your name:");
        if (nickname == null || nickname.trim().isEmpty()) {
            askForNickname();
        }
    }

    private void askForIpaddress() {
        if (ipAddress.equals("")) {
            ipAddress = JOptionPane.showInputDialog(this, "Enter the IP address of server:");
        }
    }

    private void askForPort() {
        if (port.equals("")) {
            port = JOptionPane.showInputDialog(this, "Enter port to connect to server:");
        }
    }


    public void connect() {
        clientConnectionManager = new ClientConnectionManager(this, ipAddress, port, nickname);
        clientConnectionManager.connect();
    }

    public void actionPerformed(ActionEvent e) {
        String msgText = fieldInput.getText();
        if (msgText.equals("")) {
            return;
        }
        fieldInput.setText(null);
        Message msg = new Message(nickname, msgText, new SimpleDateFormat(DATA_FORMAT).format(new Date()));
        clientConnectionManager.sendMessage(msg);
    }

    public void getMessage(Message msg) {
        MessageHandler.handle(this, msg);
    }

    public void disconnect() {
        printMessage("The connection has been interrupted!");
        clientConnectionManager.disconnect();
    }

    public void exceptionHandling(String message, Exception e) {
        printMessage(message);
        LOGGER.error("{} with error: {}", message, e);
    }

    public synchronized void printMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            log.append(msg + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    public void updateUsersList(String users) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = (DefaultListModel<String>) userList.getModel();
            model.clear();
            for (String user : users.split(",")) {
                model.addElement(user);
            }
        });
    }
}
