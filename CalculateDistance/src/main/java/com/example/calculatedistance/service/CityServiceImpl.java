package com.example.calculatedistance.service;

import com.example.calculatedistance.dao.CityRepository;
import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.entity.CityList;
import com.example.calculatedistance.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

@Service
public class CityServiceImpl {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EntityManager entityManager;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public void saveCities(@RequestPart MultipartFile multipartFile) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(City.class, CityList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CityList cityList = (CityList) unmarshaller
                .unmarshal(multipartFile.getInputStream());
        cityRepository.saveAll(cityList.getCityList());
    }

    public List<City> getCitiesByName(Collection<String> strings) {
        return cityRepository.findByNameIn(strings);
    }

    public City getByCoordinates(Double latitude, Double longitude) {
        return cityRepository.getCityByLatitudeAndLongitude(latitude, longitude);
    }
}
