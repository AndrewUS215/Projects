package ru.uskov.radius.web.connections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.uskov.radius.config.ConfigTxtProperties;
import ru.uskov.radius.config.PathConfig;

import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Authorization {

    private static final Logger LOGGER = Logger.getLogger(Authorization.class.getName());
    private String username;
    private String password;
    private WebConnection webConnection;
    private ConfigTxtProperties configTxtProperties;
    private PathConfig pathConfig;

    public Authorization(ConfigTxtProperties configTxtProperties, PathConfig pathConfig, WebConnection webConnection) throws URISyntaxException {
        this.pathConfig = pathConfig;
        this.configTxtProperties = configTxtProperties;
        this.webConnection = webConnection;
        username = configTxtProperties.getUsername(pathConfig.getConfigFile());
        password = configTxtProperties.getPassword(pathConfig.getConfigFile());
    }

    public void authorization() {
        try {
            WebElement username = webConnection.getDriver().findElement(By.id("username"));
            WebElement password = webConnection.getDriver().findElement(By.id("password"));

            username.sendKeys(configTxtProperties.getUsername(pathConfig.getConfigFile()));
            password.sendKeys(configTxtProperties.getPassword(pathConfig.getConfigFile()));
        } catch (Exception e) {
            LOGGER.warning("Exception with find element");
        }
        WebElement submit = webConnection.getDriver().findElement(By.id("enter"));
        try {
            submit.click();
        } catch (Exception e) {
            LOGGER.warning("Error with enter with: " + username + " and " + password);
        }
        LOGGER.info("Authorization is completed successfully with: " + username + " and " + password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
