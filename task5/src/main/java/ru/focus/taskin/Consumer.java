package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {
    private final int id;
    private final int consumerTime;
    private final Storage storage;
    private static final Logger LOGGER = LogManager.getLogger(Consumer.class);
    public Consumer(int id, int consumerTime, Storage storage) {
        this.id = id;
        this.consumerTime = consumerTime;
        this.storage = storage;
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try {
                Resource currResource = storage.consume();
                LOGGER.info("id {} Event: Consumed, Resource ID: {}, Current Size: {}",
                        id,
                        currResource.getId(),
                        storage.getStorageSize());
                Thread.sleep(consumerTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                run = false;
            }
        }
    }
}
