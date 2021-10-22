package com.example.calculatedistance.controller;

import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.mode.*;
import com.example.calculatedistance.service.ChangeModeService;
import com.example.calculatedistance.service.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private CityServiceImpl cityService;

    @Autowired
    private ChangeModeService changeModeService;

    @GetMapping(value = "/all")
    public List<City> showAllCities() {
        return cityService.getAllCities();
    }

    @PostMapping(value = "/save",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveCities(@RequestPart("file") MultipartFile document) throws JAXBException, IOException {
        cityService.saveCities(document);
    }

    @GetMapping(value = "/calculate/type/{type}")
    public List<Result> calculate(@PathVariable("type") CalcMode type, @RequestParam Collection<String> from, @RequestParam Collection<String> to) {
        List<City> fromCity = cityService.getCitiesByName(from);
        List<City> toCity = cityService.getCitiesByName(to);
        return changeModeService.calculateDistance(fromCity, toCity, type);
    }
}
