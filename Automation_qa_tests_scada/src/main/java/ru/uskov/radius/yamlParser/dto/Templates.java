package ru.uskov.radius.yamlParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Templates implements Serializable {

    @JsonProperty
    private Object element;
    @JsonProperty
    private String id;
    @JsonProperty
    private Object tags;

    public Templates() {
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Templates{" +
                "element=" + element +
                ", id='" + id + '\'' +
                ", tags=" + tags +
                '}';
    }
}