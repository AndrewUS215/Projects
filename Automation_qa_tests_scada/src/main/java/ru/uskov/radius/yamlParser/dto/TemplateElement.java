package ru.uskov.radius.yamlParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TemplateElement implements Serializable {

    @JsonProperty
    private String key;
    @JsonProperty
    private String type;

    public TemplateElement() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TemplateElement{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
