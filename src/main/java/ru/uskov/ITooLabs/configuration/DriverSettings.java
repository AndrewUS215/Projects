package ru.uskov.ITooLabs.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import ru.uskov.ITooLabs.webPages.YandexMarket;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DriverSettings {

    private static Logger LOGGER = Logger.getLogger(DriverSettings.class.getName());

    private final PropertiesSettings propertiesSettings;
    private final WebDriver webDriver;

    public DriverSettings(PropertiesSettings propertiesSettings) {
        this.propertiesSettings = propertiesSettings;
        System.setProperty("webdriver.chrome.driver", propertiesSettings.getDriverPath());
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void launchYandexSearchPage() {
        try {
            webDriver.get(propertiesSettings.getTestPage());
            LOGGER.info("The page " + propertiesSettings.getTestPage() + " was opened successfully");
        } catch (Exception e) {
            LOGGER.warning("Something wrong with opening page: " + propertiesSettings.getTestPage());
        }
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

}
