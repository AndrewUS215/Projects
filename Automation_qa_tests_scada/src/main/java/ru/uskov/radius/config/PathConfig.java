package ru.uskov.radius.config;

import ru.uskov.radius.Main;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class PathConfig {

    private static final Logger LOGGER = Logger.getLogger(PathConfig.class.getName());
    private static final String TAIL_CONFIG_TXT_PATH = "/config/config.txt";
    private static final String TAIL_LOG_PATH = "/log.log";
    private static final String TAIL_SWITCHING_ELEMENT_TXT_PATH = "/config/switching-elements.txt";
    private static final String WEB_CONFIGURATION_PATH = "/application-web/conf/";
    private static final String WEB_APP_PATH = "/application-web/app/";
    private static final String START = "/start";
    private static final String STOP = "/stop";
    private static final String CONFIG = "/config/";

    ConfigTxtProperties configTxtProperties;

    public PathConfig(ConfigTxtProperties configTxtProperties) {
        this.configTxtProperties = configTxtProperties;
    }

    public String getUrlConf() throws URISyntaxException {
        return configTxtProperties.getHostName(getConfigFile()) + WEB_CONFIGURATION_PATH + configTxtProperties.getSchemeName(getConfigFile()) + START;
    }

    public String getYamlPath() throws URISyntaxException {
        return getCurrentFolder() + CONFIG + configTxtProperties.getYamlName(getConfigFile());
    }

    public String getUrlApp() throws URISyntaxException {
        return configTxtProperties.getHostName(getConfigFile()) + WEB_APP_PATH + configTxtProperties.getSchemeName(getConfigFile());
    }

    public String getUrlStop() throws URISyntaxException {
        return configTxtProperties.getHostName(getConfigFile()) + WEB_CONFIGURATION_PATH + configTxtProperties.getSchemeName(getConfigFile()) + STOP;
    }

    public String getDriverPath() throws URISyntaxException {
        Path driverPath = Paths.get(getCurrentFolder() + "/" + configTxtProperties.getDriverName(getConfigFile()));
        return driverPath.toString();
    }

    public String getConfigPath() throws URISyntaxException {
        return getCurrentFolder() + TAIL_CONFIG_TXT_PATH;
    }

    public File getConfigFile() throws URISyntaxException {
        return new File(getConfigPath());
    }

    public String getLogPath() throws URISyntaxException {
        return getCurrentFolder() + TAIL_LOG_PATH;
    }

    public String getTailSwitchingElementTxtPath() throws URISyntaxException {
        return getCurrentFolder() + TAIL_SWITCHING_ELEMENT_TXT_PATH;
    }

    public String getCurrentFolder() throws URISyntaxException {
        String currentFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        String debugFile = new File(currentFolder).getParent();
        currentFolder = debugFile + "/stand/util";
        return currentFolder;
    }
}
