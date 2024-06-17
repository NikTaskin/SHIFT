package ru.focus.taskin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientConnectionManager {
    private ClientConnection clientConnection;
    private final ClientUI clientUI;
    private final String ipAddress;
    private final String port;
    private final String nickname;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientConnectionManager(ClientUI clientUI, String ipAddress, String port, String nickname) {
        this.clientUI = clientUI;
        this.ipAddress = ipAddress;
        this.port = port;
        this.nickname = nickname;
    }

    public void connect() {
        try {
            clientConnection = new ClientConnection(objectMapper, clientUI, ipAddress, Integer.parseInt(port), nickname);
            clientConnection.sendMessage(new Message("Client", nickname, new SimpleDateFormat(ClientUI.DATA_FORMAT).format(new Date())));
        } catch (IOException e) {
            clientUI.exceptionHandling("Problem with connection, set correct IP address ", e);
        } catch (NumberFormatException e) {
            clientUI.exceptionHandling("Incorrect input of connection port number ", e);
        }
    }

    public void sendMessage(Message msg) {
        clientConnection.sendMessage(msg);
    }

    public void disconnect() {
        if (clientConnection != null) {
            clientConnection.disconnect();
        }
    }
}
