package ru.focus.taskin.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.focus.taskin.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private PropertiesLoader() {
    }

    private static final Logger LOGGER = LogManager.getLogger(PropertiesLoader.class);

    public static Properties loadProperties(String[] args) throws PropLoadEx {
        Properties prop = new Properties();
        if (args.length > 0) {
            try (InputStream input = new FileInputStream(args[0])) {
                prop.load(input);
            } catch (IOException ex) {
                LOGGER.error("Unable to load properties from {}", args[0], ex);
                throw new PropLoadEx("Unable to load properties from args");
            }
        } else {
            try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
                prop.load(input);
            } catch (IOException ex) {
                //По умолчанию настройки грузятся из файла config.properties в resource
                LOGGER.error("Unable to load properties from resource/config.properties", ex);
                throw new PropLoadEx("Unable to load properties from resource");
            }
        }
        return prop;
    }

}
