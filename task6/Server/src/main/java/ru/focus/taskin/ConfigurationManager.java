package ru.focus.taskin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final Logger LOGGER = LogManager.getLogger(ConfigurationManager.class);
    private static final int DEFAULT_PORT = 8899;

    public int getServerPort() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.error("Unable to find config.properties, set default port");
                return DEFAULT_PORT;
            }
            properties.load(input);
            return Integer.parseInt(properties.getProperty("server.port"));
        } catch (IOException | NumberFormatException e) {
            LOGGER.error("Exception while reading config file: ", e);
            return DEFAULT_PORT;
        }
    }
}
