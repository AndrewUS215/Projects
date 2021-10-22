package com.example.calculatedistance.service;

import com.example.calculatedistance.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface CityService extends JpaRepository<City, Integer> {

    public City getByCoordinates(Double latitude, Double longitude);

}
