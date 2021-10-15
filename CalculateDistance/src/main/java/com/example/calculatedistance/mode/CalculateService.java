package com.example.calculatedistance.mode;

import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.entity.Distance;

import java.util.List;

public interface CalculateService {

    public List<Result> calculateDistanceByCrowFlight(List<City> from, List<City> to);
    public List<Result> calculateDistanceByMatrix(List<City> from, List<City> to);
    public List<Result> calculateDistanceAll(List<City> from, List<City> to);
}
