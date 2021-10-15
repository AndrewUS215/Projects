package com.example.calculatedistance.service;

import com.example.calculatedistance.dao.CityRepository;
import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.entity.CityList;
import com.example.calculatedistance.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Level;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public void saveCities() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(City.class, CityList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CityList cityList = (CityList) unmarshaller
                .unmarshal(new FileReader("src/main/resources/cities.xml"));
        cityRepository.saveAll(cityList.getCityList());
    }

    @Override
    public City getByCoordinates(Double latitude, Double longitude) {
        City city;
        try {
            city = entityManager.createQuery("select City from City" +
                            " where City.latitude = :latitude and City.longitude = :longitude", City.class)
                    .setParameter("latitude", latitude)
                    .setParameter("longitude", longitude)
                    .getSingleResult();
            return city;
        } catch (NoResultException | NonUniqueResultException e) {
        }
        return new City();
    }
}
