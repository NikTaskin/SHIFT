package ru.focus.taskin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageBroadcaster {
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final UserManager userManager;

    public MessageBroadcaster(UserManager userManager) {
        this.userManager = userManager;
    }

    public void sendMessageToAll(Message msg) {
        System.out.println(msg.getTimestamp() + " " + msg.getNickname() + ": " + msg.getText());
        for (ServerConnection connection : userManager.getAllConnections()) {
            connection.sendMessage(msg);
        }
    }

    public void updateUsersList() {
        StringBuilder users = new StringBuilder();
        for (String user : userManager.getAllUsers()) {
            users.append(user).append(",");
        }
        if (users.length() > 0) {
            users.setLength(users.length() - 1);
        }
        sendMessageToAll(new Message("Active users list", users.toString(), new SimpleDateFormat(DATA_FORMAT).format(new Date())));
    }
}
