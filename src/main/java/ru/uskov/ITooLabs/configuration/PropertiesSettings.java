package ru.uskov.ITooLabs.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesSettings {

    private static Logger LOGGER = Logger.getLogger(PropertiesSettings.class.getName());

    private static Properties properties;
    private final String DRIVER_KEY = "chromedriver";
    private final String TEST_PAGE_KEY = "yandexSearch";

    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")){
            properties = new Properties();
            properties.load(fileInputStream);
            LOGGER.info("config.properties file was processed successfully");
        } catch (IOException e) {
            LOGGER.warning("Problem with loading config.properties file");
        }
    }
    public String getDriverPath() {
        return properties.getProperty(DRIVER_KEY);
    }

    public String getTestPage() {
        return properties.getProperty(TEST_PAGE_KEY);
    }
}
