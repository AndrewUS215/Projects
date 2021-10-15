package com.example.calculatedistance.service;

import com.example.calculatedistance.entity.City;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface CityService {
    public List<City> getAllCities();

    public void saveCities() throws JAXBException, FileNotFoundException;

    public City getByCoordinates(Double latitude, Double longitude);

}
