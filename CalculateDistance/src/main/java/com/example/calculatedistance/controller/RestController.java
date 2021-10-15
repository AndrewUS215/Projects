package com.example.calculatedistance.controller;

import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.mode.*;
import com.example.calculatedistance.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private CityService cityService;

    @GetMapping(value = "/all")
    public List<City> showAllCities() {
        return cityService.getAllCities();
    }

    @PostMapping(value = "/save",
            consumes = {MediaType.APPLICATION_XML_VALUE})
    public void saveCities() throws JAXBException, FileNotFoundException {
        cityService.saveCities();
    }

    @PostMapping(value = "/{type}")
    public List<Result> calculate(@PathVariable("type") CalcMode type) {
        List<City> cityList = cityService.getAllCities();
        return new ChangeMode().calculateDistance(cityList, cityList, type);
    }
}
