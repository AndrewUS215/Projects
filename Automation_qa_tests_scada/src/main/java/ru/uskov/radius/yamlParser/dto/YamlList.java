package ru.uskov.radius.yamlParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({ "infos", "styles", "templates" })
public class YamlList implements Serializable {

    @JsonProperty
    private List<Templates> templates;
    @JsonProperty
    private List<Infos> infos;
    @JsonProperty
    private List<String> styles;

    public YamlList() {
    }

    public List<Templates> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Templates> templates) {
        this.templates = templates;
    }

    public List<Infos> getInfos() {
        return infos;
    }

    public void setInfos(List<Infos> infos) {
        this.infos = infos;
    }

    public List<String> getStyles() {
        return styles;
    }

    public void setStyles(List<String> styles) {
        this.styles = styles;
    }

    @Override
    public String toString() {
        return "YamlList{" +
                "templates=" + templates +
                ", infos=" + infos +
                ", styles=" + styles +
                '}';
    }
}