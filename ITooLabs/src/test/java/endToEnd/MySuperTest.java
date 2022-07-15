package endToEnd;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.uskov.ITooLabs.configuration.DriverSettings;
import ru.uskov.ITooLabs.configuration.PropertiesSettings;
import ru.uskov.ITooLabs.webPages.YandexMarket;
import ru.uskov.ITooLabs.webPages.YandexSearch;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MySuperTest {

    private static Logger LOGGER = Logger.getLogger(MySuperTest.class.getName());

    private static DriverSettings driverSettings;
    private static PropertiesSettings propertiesSettings;
    private static YandexSearch yandexSearch;
    private static YandexMarket yandexMarket;

    @BeforeClass
    public static void setUp() {
        try {
            propertiesSettings = new PropertiesSettings();
            driverSettings = new DriverSettings(propertiesSettings);
            yandexSearch = new YandexSearch();
            yandexMarket = new YandexMarket();
        } catch (Exception e) {
            LOGGER.info("Set up for class " + MySuperTest.class.getName() + " passed unsuccessfully");
        }
    }

    @AfterClass
    public static void cleanUp() {
        try {
            driverSettings.getWebDriver().quit();
            LOGGER.warning("Driver was closed successfully");
        } catch (Exception e) {
            LOGGER.warning("Driver was closed unsuccessfully");
        }
    }

    @Step("Результат проверки перехода по второму элементу из выпадающего списка в яндекс маркете по запросу \"белые кеды\"")
    public void checkSecondVariantInTheSearchingPopUp() throws InterruptedException {
        Actions actions = new Actions(driverSettings.getWebDriver());
        driverSettings.launchYandexSearchPage();

        WebElement marketLink = driverSettings.getWebDriver().findElement(By.xpath(yandexSearch.getMarketLinkXPath()));
        actions.click(marketLink).perform();

        Thread.sleep(5000);

        ArrayList<String> tabs2 = new ArrayList<String> (driverSettings.getWebDriver().getWindowHandles());
        driverSettings.getWebDriver().switchTo().window(tabs2.get(1));

        driverSettings.getWebDriver().findElement(By.id(yandexMarket.getMarketSearchId())).sendKeys("белые кеды");

        Thread.sleep(2000);

        WebElement secondElementInThePopUp = driverSettings.getWebDriver().findElement(By.xpath(".//*[local-name()='li' and @data-index='1']"));
        actions.click(secondElementInThePopUp).perform();

        Thread.sleep(5000);

        WebElement firstPrice = driverSettings.getWebDriver().findElement(By.xpath("(.//*[@data-autotest-value='1'])[1]"));
        LOGGER.info("This is first price on the page: " + firstPrice.getText());

        saveAllureScreenshot();
    }

    @Test
    @Owner(value = "Усков А.С.")
    @Description(value = "Тест проверяет корректный переход по ссылке на товар из выпадающего списка")
    public void MarketPopUpCheck() throws InterruptedException {
        checkSecondVariantInTheSearchingPopUp();
    }

    @Attachment(value = "Page screenshot", type = "target/allure-results")
    protected byte[] saveAllureScreenshot() {
        LOGGER.info("Taking screenshot into: " + MySuperTest.class.getName());
        return ((TakesScreenshot) driverSettings.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
