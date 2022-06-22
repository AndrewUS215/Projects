package ru.uskov.radius.yamlParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.uskov.radius.yamlParser.dto.Infos;
import ru.uskov.radius.yamlParser.dto.TemplateElement;
import ru.uskov.radius.yamlParser.dto.Templates;
import ru.uskov.radius.yamlParser.dto.YamlList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YamlParser {

    private final String yamlPath;
    private YamlList yamlList;
    private List<TemplateElement> templatesItems;
    //private List<Infos> switchingElementsList;

    public YamlParser(String yamlPath) {
        this.yamlPath = yamlPath;
    }

    public void getYaml() {
        try (InputStream ip = new FileInputStream(yamlPath)) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();
            yamlList = mapper.readValue(ip, YamlList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getSwitchingElements() {
        getYaml();
        Map<String, String> elementsMap = new HashMap<>();
        for (Infos infosItem : getSwitchingElementsFromInfos()) {
            String infosItemTag = infosItem.getTag();
            for (Templates templatesItem : getTemplates()) {
                Map<String, String> element = (Map<String, String>) templatesItem.getElement();
                String key = String.valueOf(element.get("key"));
                String templatesItemId = templatesItem.getId();
                if (infosItemTag.equals(templatesItemId)) {
                    if (infosItem.getName() != null) {
                        elementsMap.put(key, templatesItemId + " " + infosItem.getName());
                    } else {
                        elementsMap.put(key, templatesItemId);
                    }
                }
            }
        }
        return elementsMap;
    }

    public List<TemplateElement> getElementsFromTemplates() {
        for (Templates entry : getTemplates()) {
            Map<String, String> element = (Map<String, String>) entry.getElement();
            String key = String.valueOf(element.get("key"));
            String type = String.valueOf(element.get("type"));
            TemplateElement templatesItem = new TemplateElement();
            templatesItem.setType(type);
            templatesItem.setKey(key);
            templatesItems.add(templatesItem);
        }
        return templatesItems;
    }

    public List<Infos> getSwitchingElementsFromInfos() {
        List<Infos> switchingElementsList = new ArrayList<>();
        for (Infos infos : getInfos()) {
            if (infos.getType().equals("CONTROL_BUTTON") ||
                    infos.getType().equals("CIRCUIT_CELL") ||
                    infos.getType().equals("SWITCH") ||
                    infos.getType().equals("DISCONNECTOR") ||
                    infos.getType().equals("EARTH_KNIFE") ||
                    infos.getType().equals("SEPARATOR") ||
                    infos.getType().equals("SHORT_CIRCUIT")) {
                switchingElementsList.add(infos);
            }
        } return switchingElementsList;
    }

    public List<String> getStyles() {
        return yamlList.getStyles();
    }

    public List<Infos> getInfos() {
        return yamlList.getInfos();
    }

    public List<Templates> getTemplates() {
        return yamlList.getTemplates();
    }

    public List<Object> templatesElementList() {
        return getTemplates().stream().map(Templates::getElement).collect(Collectors.toList());
    }
}
