package com.example.calculatedistance.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.File;
import java.util.List;

@XmlRootElement(name = "cities")
@XmlSeeAlso(City.class)
public class CityList {

    private List<City> cityList;

    private File document;

    @XmlElement(name = "city")
    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public File getDocument() {
        return document;
    }

    public void setDocument(File document) {
        this.document = document;
    }
}
