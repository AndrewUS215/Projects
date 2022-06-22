package serverPart.launch;

import org.apache.logging.log4j.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import ru.uskov.radius.config.ConfigTxtProperties;
import ru.uskov.radius.config.PathConfig;
import ru.uskov.radius.web.connections.Authorization;
import ru.uskov.radius.web.connections.WebConnection;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaunchScadaTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static PathConfig pathConfig;
    private static ConfigTxtProperties configTxtProperties;
    private static WebConnection webConnection;
    private static Authorization authorization;

    @BeforeAll
    static void setUpDriver() throws URISyntaxException {
        configTxtProperties = new ConfigTxtProperties();
        pathConfig = new PathConfig(configTxtProperties);
        webConnection = new WebConnection(pathConfig);
        authorization = new Authorization(configTxtProperties, pathConfig, webConnection);
        LOGGER.info("The set up process is completed");
    }

    @AfterAll
    static void cleanUp() {
        try {
            webConnection.stopConf();
            webConnection.closeDriver();
        } catch (Exception e) {
            LOGGER.error("Stopping the configuration was failed");
        }
    }

    @Test
    public void launchConfigurationUrl() throws URISyntaxException {
        webConnection.launchConf();
        String expected = "Загрузка конфигурации...";
        String actual = webConnection.getDriver().findElement(By.xpath("//body[text()]")).getText();
        assertEquals(expected, actual);
    }

    @Test
    public void launchAppUrl() throws URISyntaxException {
        webConnection.launchConf();
        webConnection.launchApp();
        String expected = "Авторизуйтесь, пожалуйста!";
        String actual = webConnection.getDriver().findElement(By.xpath("//p[text()]")).getText();
        assertEquals(expected, actual);
    }

    @Test
    public void logIn() {
        try {
            webConnection.launchConf();
            webConnection.launchApp();
            authorization.authorization();
            Thread.sleep(3000);
        } catch (Exception loginException) {
        }
        String expected = "Выход";
        String actual = webConnection.getDriver().findElement(By.xpath("//span[contains(@id, 'btnInnerEl') and text()='Выход']")).getText();
        assertEquals(expected, actual);
    }
}
