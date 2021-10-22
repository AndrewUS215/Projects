package com.example.calculatedistance.dao;

import com.example.calculatedistance.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    //@Query("SELECT City FROM City WHERE City.name IN (:strings)")
    List<City> findByNameIn(Collection<String> strings);

    City getCityByLatitudeAndLongitude(Double latitude, Double longitude);
}
