package ru.uskov.radius;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.uskov.radius.config.ConfigTxtProperties;
import ru.uskov.radius.config.PathConfig;
import ru.uskov.radius.web.connections.Authorization;
import ru.uskov.radius.web.connections.WebConnection;
import ru.uskov.radius.yamlParser.YamlParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static PathConfig pathConfig;
    private static ConfigTxtProperties configTxtProperties;
    private static WebConnection webConnection;
    private static Authorization authorization;

    public static void main(String[] args) throws Exception {

        configTxtProperties = new ConfigTxtProperties();
        pathConfig = new PathConfig(configTxtProperties);
        webConnection = new WebConnection(pathConfig);
        authorization = new Authorization(configTxtProperties, pathConfig, webConnection);

        try {
            FileHandler fh;
            try {
                Path logPath = Paths.get(pathConfig.getLogPath());
                fh = new FileHandler(logPath.toString());
                LOGGER.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setEncoding("UTF-8");
                fh.setFormatter(formatter);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, (Supplier<String>) e.getCause());
            }

            Map<String, String> elementIds = new HashMap<>();
            if (configTxtProperties.isUseYamlTrue(pathConfig.getConfigFile())) {
                YamlParser yamlParser = new YamlParser(pathConfig.getYamlPath());
                elementIds = yamlParser.getSwitchingElements();
            } else {
                System.exit(0);
            }
            LOGGER.log(Level.INFO, "Элементы, подлежащие проверке: ");
            for (Map.Entry entry : elementIds.entrySet()) {
                LOGGER.log(Level.INFO, (String) entry.getValue());
            }


            LOGGER.log(Level.INFO, "START TESTING");
            webConnection.launchConf();

            try {
                WebElement dynamicElement = (new WebDriverWait(webConnection.getDriver(), 2))
                        .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            } catch (Exception e) {
                webConnection.launchApp();
            }

            authorization.authorization();
            LOGGER.log(Level.INFO, "authorization in Scada SUCCESS");

            Actions actions = new Actions(webConnection.getDriver());


            long start = System.currentTimeMillis();
            long end = start + 3 * 1000;
            while (System.currentTimeMillis() < end) { //waiting
            }

            List<String> report = new ArrayList<>();
            String pass = "ТЕСТ ПРОЙДЕН";
            Map<String, Integer> countSwitchResult = new HashMap<>();
            List<Integer> timeList = new ArrayList<>();


            for (Map.Entry entry : elementIds.entrySet()) {

                LOGGER.log(Level.INFO, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start testing element " + entry.getValue() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                String[] reportItem = switchStatus((String) entry.getKey(), actions, webConnection.getDriver());
                LOGGER.log(Level.INFO, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< stop testing element " + entry.getValue() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                report.add("Проверка переключения элемента " + entry.getValue() + " Результат переключения " + reportItem[0] + ", Время на переключение: " + reportItem[1] + " секунд.");

                if (countSwitchResult.containsKey(reportItem[0])) {
                    int count = countSwitchResult.get(reportItem[0]) + 1;
                    countSwitchResult.replace(reportItem[0], count);
                }
                if (!countSwitchResult.containsKey(reportItem[0])) {
                    countSwitchResult.put(reportItem[0], 1);
                }
                if (Objects.nonNull(reportItem[1])) {
                    timeList.add(Integer.valueOf(reportItem[1]));
                }


                if (reportItem[0].equals("FAIL")) {
                    pass = "ТЕСТ НЕ ПРОЙДЕН";
                }
            }


            //driver.get(urlStop);
            LOGGER.log(Level.INFO, "Stop configuration");
            webConnection.getDriver().close();
            LOGGER.log(Level.INFO, "Close web driver");
            LOGGER.log(Level.INFO, "STOP TESTING");


            LOGGER.log(Level.INFO, "============================================================== REPORT ==============================================================");
            LOGGER.log(Level.INFO, pass);
            for (String item : report) {
                LOGGER.log(Level.INFO, item);
            }
            LOGGER.log(Level.INFO, "Проверено элементов: " + elementIds.keySet().size() + ", Из них 1 элемент заведомо отсутствует на схеме");
            LOGGER.log(Level.INFO, countSwitchResult.toString());
            LOGGER.log(Level.INFO, "Минимальное время переключения: " + timeList.stream().min(Integer::compareTo).toString());
            LOGGER.log(Level.INFO, "Максимальное время переключения: " + timeList.stream().max(Integer::compareTo).toString());
            LOGGER.log(Level.INFO, "Среднее время переключения: " + timeList.stream().mapToInt(x -> x).average());

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "TEST FAILED WITH ERROR: " + e.getCause() + e.toString());
            e.printStackTrace();
        }

    }

    static void turnOn(String dataId, Actions actions, WebDriver driver) {
        LOGGER.log(Level.INFO, "---------------------------------------------------------------------Try to turn on element " + dataId);
        int i = 0;
        while (true) {//right click
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='g' and @data-id='" + dataId + "']//*[local-name()='rect']"));
                actions.contextClick(actionUser).perform();

                long start = System.currentTimeMillis();
                long end = start + 500;
                while (System.currentTimeMillis() < end) { //waiting
                }
                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {

                break;
            }
        }

        i = 0;
        while (true) {//turn on
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='span' " +
                        "and text()='Включить' or text()='ТУ захватить' or text()='Рабочее положение, выключатель включен']"));
//                WebElement actionUser = driver.findElement(By.xpath("/html/body//text()[contains(translate(.,'абвгдеёжзийклмнопрстуфхцчшщъыьэюя','АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'),'ВКЛЮЧ')]"));
                actions.click(actionUser).perform();

                long start = System.currentTimeMillis();
                long end = start + 500;
                while (System.currentTimeMillis() < end) { //waiting
                }
                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {

                break;
            }
        }

        i = 0;
        while (true) {//confirm
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='span' and text()='Да']"));
                actions.click(actionUser).perform();

                long start = System.currentTimeMillis();
                long end = start + 500;
                while (System.currentTimeMillis() < end) { //waiting
                }
                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {

                break;
            }
        }
    }

    static void turnOff(String dataId, Actions actions, WebDriver driver) {
        LOGGER.log(Level.INFO, "---------------------------------------------------------------------Try to turn off element " + dataId);
        int i = 0;
        while (true) {//right click
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='g' and @data-id='" + dataId + "']//*[local-name()='rect']"));
                actions.contextClick(actionUser).perform();

                long start = System.currentTimeMillis();
                long end = start + 500;
                while (System.currentTimeMillis() < end) { //waiting
                }
                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {

                break;
            }
        }

        i = 0;
        while (true) {//turn off
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='span' " +
                        "and text()='Отключить' or text()='ТУ освободить' or text()='Рабочее положение, выключатель отключен']"));
//                WebElement actionUser = driver.findElement(By.xpath("/html/body//text()[contains(translate(.,'абвгдеёжзийклмнопрстуфхцчшщъыьэюя','АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'),'ОТКЛЮЧ')]"));

                actions.click(actionUser).perform();
                long start = System.currentTimeMillis();
                long end = start + 500;
                while (System.currentTimeMillis() < end) { //waiting
                }
                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {

                break;
            }
        }

        i = 0;
        while (true) {//confirm
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='span' and text()='Да']"));
                actions.click(actionUser).perform();

                long start = System.currentTimeMillis();
                long end = start + 500;
                while (System.currentTimeMillis() < end) { //waiting
                }
                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {

                break;
            }
        }
    }

    static String getState(String dataId, Actions actions, WebDriver driver) {
        String status = "unknown";
        String elementClass = "unknown";

        int i = 0;
        while (true) {//get class of the element
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='g' and @data-id='" + dataId + "']"));
                elementClass = actionUser.getAttribute("class");
                break;
            } catch (Exception e) {
                elementClass = "unknown";
            }
            i++;
            if (i > 10) {
                break;
            }
        }

        if (elementClass.contains("enable") || elementClass.contains("Enable")) {
            status = "enable";
        }
        if (elementClass.contains("disable") || elementClass.contains("Disable")) {
            status = "disable";
        }
        if (elementClass.equals("unknown")) {
            status = "not find element";
        }

        LOGGER.log(Level.INFO, "********************************************element status is: " + status);
        return status;
    }

    static String[] checkSwitch(String status, String dataId, Actions actions, WebDriver driver) {
        LOGGER.log(Level.INFO, "Check switch of element " + dataId);
        String tmpStatus = "";
        int secondsToCheck = 10;
        String[] result = new String[2];
        result[0] = "FAIL";
        result[1] = null;
        for (int i = 0; i < secondsToCheck; i++) {
            long start = System.currentTimeMillis();
            long end = start + 1 * 1000;
            while (System.currentTimeMillis() < end) { //waiting
            }
            tmpStatus = getState(dataId, actions, driver);
            if (tmpStatus.equals(status)) {
                LOGGER.log(Level.INFO, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ status of element changed at " + i + " seconds.");
                result[0] = "SUCCESS";
                result[1] = Integer.toString(i);
                break;
            }


        }
        return result;
    }

    static String[] switchStatus(String dataId, Actions actions, WebDriver driver) {
        String successSwitch = "NOT FIND ELEMENT";
        LOGGER.log(Level.INFO, "---------------------------------------------------------------------Switch status element " + dataId);
        String[] result = new String[2];

        String checkSwitch = "FAIL";
        String currentState = getState(dataId, actions, driver);
        if (currentState.equals("enable")) {
            turnOff(dataId, actions, driver);
            result = checkSwitch("disable", dataId, actions, driver);
            if (result[0].equals("SUCCESS")) {
                successSwitch = "SUCCESS";
            }
            if (result[0].equals("FAIL")) {
                LOGGER.log(Level.WARNING, "----------------------------------------xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx failed to switch first time, try again xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                turnOn(dataId, actions, driver);
                checkSwitch("enable", dataId, actions, driver);
                turnOff(dataId, actions, driver);
                result = checkSwitch("disable", dataId, actions, driver);
                if (result[0].equals("SUCCESS")) {
                    successSwitch = "AFTER SECOND TRY";
                } else {
                    successSwitch = "FAIL";
                }
            }

        }
        if (currentState.equals("disable")) {
            turnOn(dataId, actions, driver);
            result = checkSwitch("enable", dataId, actions, driver);
            if (result[0].equals("SUCCESS")) {
                successSwitch = "SUCCESS";
            }
            if (result[0].equals("FAIL")) {
                LOGGER.log(Level.WARNING, "----------------------------------------xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx failed to switch first time, try again xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                turnOff(dataId, actions, driver);
                checkSwitch("disable", dataId, actions, driver);
                turnOn(dataId, actions, driver);
                result = checkSwitch("enable", dataId, actions, driver);
                if (result[0].equals("SUCCESS")) {
                    successSwitch = "AFTER SECOND TRY";
                } else {
                    successSwitch = "FAIL";
                }
            }
        }
        if (currentState.equals("not find element")) {
            successSwitch = "CAN NOT FIND ELEMENT";
        }
        if (currentState.equals("unknown")) {
            successSwitch = "CAN NOT DETECT ELEMENT STATUS";
        }
        if (checkSwitch.equals("SUCCESS")) {
            LOGGER.log(Level.INFO, "==================================================================Switching status of element " + dataId + " is " + checkSwitch);
        } else {
            LOGGER.log(Level.WARNING, "==================================================================Switching status of element " + dataId + " is " + checkSwitch);
        }
        result[0] = successSwitch;
        return result;
    }

    static void emptyClick(Actions actions, WebDriver driver) {
        int i = 0;
        while (true) {// click
            try {
                WebElement actionUser = driver.findElement(By.xpath("//*[local-name()='g' and @data-id='10']"));
                actions.click(actionUser);

                break;
            } catch (Exception e) {

            }
            i++;
            if (i > 10) {
                break;
            }
        }
    }
}