package ru.focus.taskin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(ServerConnectionManager.class);
    private static final String SERVER_NAME = "Server";
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final Server server;
    private final UserManager userManager;
    private final MessageBroadcaster messageBroadcaster;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServerConnectionManager(Server server, UserManager userManager, MessageBroadcaster messageBroadcaster) {
        this.server = server;
        this.userManager = userManager;
        this.messageBroadcaster = messageBroadcaster;
    }

    public void acceptConnection(ServerSocket serverSocket) {
        try {
            ServerConnection connection = new ServerConnection(objectMapper, server, serverSocket.accept());
            Message initialMessage = connection.receiveMessage();
            String clientName = initialMessage.getText();
            synchronized (this) {
                if (userManager.isUserExists(clientName)) {
                    connection.sendMessage(new Message(SERVER_NAME, "Name already taken", new SimpleDateFormat(DATA_FORMAT).format(new Date())));
                } else {
                    userManager.addUser(clientName, connection);
                    messageBroadcaster.sendMessageToAll(new Message(SERVER_NAME, clientName + " joined", new SimpleDateFormat(DATA_FORMAT).format(new Date())));
                    messageBroadcaster.updateUsersList();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Problem with connection with exception: ", e);
        }
    }
}
