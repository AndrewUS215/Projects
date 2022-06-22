package ru.uskov.radius.yamlParser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Infos {

    @JsonProperty
    private boolean manual;
    @JsonProperty
    private String type;
    @JsonProperty
    private String tag;
    @JsonProperty
    private String name;
    @JsonProperty
    private String ctrType;

    public Infos() {
    }

    public String getCtrType() {
        return ctrType;
    }

    public void setCtrType(String ctrType) {
        this.ctrType = ctrType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Infos{" +
                "manual=" + manual +
                ", type='" + type + '\'' +
                ", tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", ctrType='" + ctrType + '\'' +
                '}';
    }
}
