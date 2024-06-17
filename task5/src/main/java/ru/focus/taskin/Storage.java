package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private final int storageSize;
    private final Queue<Resource> resourcesInStorage = new LinkedList<>();
    private static final Logger LOGGER = LogManager.getLogger(Storage.class);

    public Storage(int storageSize) {
        this.storageSize = storageSize;
    }

    public synchronized void produce(Resource resource) throws InterruptedException {
        while (resourcesInStorage.size() >= storageSize) {
            LOGGER.debug("Event: Waiting, Current Size: {}",
                    getStorageSize());
            wait();
        }
        resourcesInStorage.add(resource);
        notifyAll();
    }

    public synchronized Resource consume() throws InterruptedException {
        while (resourcesInStorage.isEmpty()) {
            LOGGER.debug("Event: Waiting, Current Size: {}",
                    getStorageSize());
            wait();
        }
        Resource resource = resourcesInStorage.poll();
        notifyAll();
        return resource;
    }

    public synchronized int getStorageSize() {
        return resourcesInStorage.size();
    }
}
