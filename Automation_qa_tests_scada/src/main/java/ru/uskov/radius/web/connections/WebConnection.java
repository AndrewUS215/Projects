package ru.uskov.radius.web.connections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.uskov.radius.config.PathConfig;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebConnection {

    private static final Logger LOGGER = Logger.getLogger(WebConnection.class.getName());
    private static WebDriver driver;
    private final PathConfig pathConfig;

    public WebConnection(PathConfig pathConfig) throws URISyntaxException {
        this.pathConfig = pathConfig;
        System.setProperty("webdriver.gecko.driver", pathConfig.getDriverPath());
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        driver = new FirefoxDriver(capabilities);
    }


    public void launchConf() throws URISyntaxException {
        LOGGER.log(Level.INFO, "Open web driver and launch configuration of scada");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
        driver.get(pathConfig.getUrlConf());
    }

    public void launchApp() throws URISyntaxException {
        LOGGER.log(Level.INFO, "Launch scada's URL application");
        driver.get(pathConfig.getUrlApp());
    }

    public void stopConf() throws URISyntaxException {
        driver.get(pathConfig.getUrlStop());
        LOGGER.log(Level.INFO, "Scada's configuration was stopped");
    }

    public void closeDriver() {
        driver.quit();
        LOGGER.info("The driver was closed");
    }

    public WebDriver getDriver() {
        return driver;
    }


}
