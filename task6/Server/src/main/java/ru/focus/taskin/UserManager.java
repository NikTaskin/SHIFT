package ru.focus.taskin;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, ServerConnection> clients = new HashMap<>();

    public synchronized void addUser(String username, ServerConnection connection) {
        clients.put(username, connection);
    }

    public synchronized void removeUser(ServerConnection connection) {
        clients.values().remove(connection);
    }

    public synchronized boolean isUserExists(String username) {
        return clients.containsKey(username);
    }

    public synchronized Iterable<String> getAllUsers() {
        return clients.keySet();
    }

    public synchronized Iterable<ServerConnection> getAllConnections() {
        return clients.values();
    }

    public synchronized String getCurrNickname(ServerConnection connection) {
        for (Map.Entry<String, ServerConnection> entry : clients.entrySet()) {
            if (entry.getValue().equals(connection)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
