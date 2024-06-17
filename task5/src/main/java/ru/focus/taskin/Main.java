package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.focus.taskin.properties.IncorrectProperty;
import ru.focus.taskin.properties.MissingProperty;
import ru.focus.taskin.properties.PropLoadEx;

import java.util.Properties;

import static ru.focus.taskin.properties.PropertiesLoader.loadProperties;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Properties prop = loadProperties(args);
            int producerCount = getIntProperty(prop, "producerCount");
            int consumerCount = getIntProperty(prop, "consumerCount");
            int producerTime = getIntProperty(prop, "producerTime");
            int consumerTime = getIntProperty(prop, "consumerTime");
            int storageSize = getIntProperty(prop, "storageSize");

            Storage storage = new Storage(storageSize);

            for (int i = 0; i < producerCount; i++) {
                Thread producerThread = new Thread(new Producer(i + 1, producerTime, storage));
                producerThread.start();
            }

            for (int i = 0; i < consumerCount; i++) {
                Thread consumerThread = new Thread(new Consumer(i + 1, consumerTime, storage));
                consumerThread.start();
            }
        } catch (PropLoadEx | MissingProperty | IncorrectProperty e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static int getIntProperty(Properties prop, String name) throws IncorrectProperty, MissingProperty {
        var value = prop.getProperty(name);
        if (value == null) {
            throw new MissingProperty("Missed property component " + name);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IncorrectProperty("Property " + name + " has incorrect value " + value);
        }
    }
}
