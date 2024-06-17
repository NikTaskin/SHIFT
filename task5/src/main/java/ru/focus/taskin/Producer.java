package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
    private final int id;
    private final int producerTime;
    private final Storage storage;
    private static final Logger LOGGER = LogManager.getLogger(Producer.class);

    public Producer(int id, int producerTime, Storage storage) {
        this.id = id;
        this.producerTime = producerTime;
        this.storage = storage;
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try {
                Thread.sleep(producerTime);
                Resource resource = new Resource();
                storage.produce(resource);
                LOGGER.info("id {} Event: Produced, Resource ID: {}, Current Size: {}",
                        id,
                        resource.getId(),
                        storage.getStorageSize());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                run = false;
            }
        }
    }
}