package ru.focus.taskin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientConnection {
    private final Socket socket;
    private final Thread thread;
    private final ClientUI client;
    private final BufferedReader input;
    private final BufferedWriter output;
    private final ObjectMapper objectMapper;
    private final String nickname;

    public ClientConnection(ObjectMapper objectMapper, ClientUI client, String ipAddress, int port, String nickname) throws IOException {
        this.objectMapper = objectMapper;
        this.client = client;
        this.socket = new Socket(ipAddress, port);
        this.nickname = nickname;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String msgJson = input.readLine();
                    if (msgJson == null) {
                        break;
                    }
                    Message msg = objectMapper.readValue(msgJson, Message.class);
                    client.getMessage(msg);
                }
            } catch (IOException e) {
                client.exceptionHandling("Problems with deserialization ", e);
            } finally {
                client.disconnect();
            }
        });
        thread.start();
    }

    public synchronized void sendMessage(Message msg) {
        try {
            String msgJson = objectMapper.writeValueAsString(msg);
            output.write(msgJson + "\n");
            output.flush();
        } catch (IOException e) {
            client.exceptionHandling("Problems with serialization ", e);
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            client.exceptionHandling("Problems with closing socket ", e);
        }
    }

    @Override
    public String toString() {
        return nickname;
    }
}

