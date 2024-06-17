package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private final ServerConnectionManager serverConnectionManager;
    private final ConfigurationManager configurationManager;
    private final UserManager userManager;
    private final MessageBroadcaster messageBroadcaster;
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {
        new Server().start();
    }

    public Server() {
        this.configurationManager = new ConfigurationManager();
        this.userManager = new UserManager();
        this.messageBroadcaster = new MessageBroadcaster(userManager);
        this.serverConnectionManager = new ServerConnectionManager(this, userManager, messageBroadcaster);
    }

    public void start() {
        LOGGER.info("Start running server");
        try (ServerSocket serverSocket = new ServerSocket(configurationManager.getServerPort())) {
            while (true) {
                serverConnectionManager.acceptConnection(serverSocket);
            }
        } catch (IOException e) {
            LOGGER.error("Problem with server socket with exception: ", e);
        }
    }

    public void getMessage(Message msg) {
        messageBroadcaster.sendMessageToAll(msg);
    }

    public void disconnect(ServerConnection connection) {
        if (userManager.getCurrNickname(connection) != null) {
            messageBroadcaster.sendMessageToAll(new Message("Server", userManager.getCurrNickname(connection) + " disconnected", new SimpleDateFormat(DATA_FORMAT).format(new Date())));
            userManager.removeUser(connection);
            messageBroadcaster.updateUsersList();
        }
    }

    public void exceptionHandling(String exMessage, Exception e) {
        LOGGER.error("{} with error {}", exMessage, e);
    }
}
