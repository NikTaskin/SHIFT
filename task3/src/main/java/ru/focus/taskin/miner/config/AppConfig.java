package ru.focus.taskin.miner.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static ru.focus.taskin.miner.model.logger.RecordLogger.logger;
public class AppConfig {
    private static final String CONFIG_FILE_PATH = "task3/src/main/resources/prop.properties";

    private final Properties properties;

    public AppConfig() {
        this.properties = loadProperties();
    }

    private Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream propFile = new FileInputStream(CONFIG_FILE_PATH)) {
            prop.load(propFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return prop;
    }

    public String getRecordFilePath() {
        return properties.getProperty("recordFile");
    }
}
