package ru.focus.taskin;

public class Resource {
    private static int idCounter = 0;
    private final int id;

    public Resource() {
        this.id = generateId();
    }

    private static synchronized int generateId() {
        return ++idCounter;
    }

    public int getId() {
        return id;
    }
}
