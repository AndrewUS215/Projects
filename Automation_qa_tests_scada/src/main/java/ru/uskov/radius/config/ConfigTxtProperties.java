package ru.uskov.radius.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ConfigTxtProperties {

    public ConfigTxtProperties() {
    }

    public Map<String, String> getProperties(File configFile) throws URISyntaxException {
        Map<String, String> properties = new HashMap<>();
        try (BufferedReader fin = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = fin.readLine()) != null) {
                properties.put(line.split("=")[0], line.split("=")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getUsername(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("username");
    }

    public String getPassword(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("password");
    }

    public String getSchemeName(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("scheme");
    }

    public String getHostName(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("host");
    }

    public String getDriverName(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("driver");
    }

    public String getYamlName(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("yaml_name");
    }

    public boolean isUseYamlTrue(File configFile) throws URISyntaxException {
        return getProperties(configFile).get("use_yaml").equals("true");
    }

}
