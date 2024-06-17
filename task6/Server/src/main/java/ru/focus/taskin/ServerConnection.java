package ru.focus.taskin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerConnection {
    private final Server server;
    private final BufferedReader input;
    private final BufferedWriter output;
    private final ObjectMapper objectMapper;

    public ServerConnection(ObjectMapper objectMapper, Server server, Socket socket) throws IOException {
        this.objectMapper = objectMapper;
        this.server = server;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String msgJson = input.readLine();
                    if (msgJson == null) {
                        break;
                    }
                    Message msg = objectMapper.readValue(msgJson, Message.class);
                    server.getMessage(msg);
                }
            } catch (IOException e) {
                server.exceptionHandling("Problems with deserialization ", e);
            } finally {
                server.disconnect(ServerConnection.this);
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
            server.exceptionHandling("Problems with serialization ", e);
        }
    }

    public synchronized Message receiveMessage() throws IOException {
        String msgJson = input.readLine();
        if (msgJson == null) {
            throw new IOException("End of stream");
        }
        return objectMapper.readValue(msgJson, Message.class);
    }
}

