package com.example.calculatedistance.dao;

import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.entity.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DistanceRepository extends JpaRepository<Distance, Integer> {
    Distance getDistanceByFromCityAndToCity(City form, City to);
}
