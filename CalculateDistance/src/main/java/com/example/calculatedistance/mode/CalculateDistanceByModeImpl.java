package com.example.calculatedistance.mode;

import com.example.calculatedistance.dao.DistanceRepository;
import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.entity.Distance;
import com.example.calculatedistance.service.CityService;
import com.example.calculatedistance.service.CityServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Service
public class CalculateDistanceByModeImpl implements CalculateService {

    Logger logger = Logger.getLogger(CalculateDistanceByModeImpl.class);

    private final EntityManager entityManager;
    private final CityServiceImpl cityService;
    private final DistanceRepository distanceRepository;

    @Autowired
    public CalculateDistanceByModeImpl(EntityManager entityManager, CityServiceImpl cityService, DistanceRepository distanceRepository) {
        this.entityManager = entityManager;
        this.cityService = cityService;
        this.distanceRepository = distanceRepository;
    }

    public List<Result> calculateDistanceByCrowFlight(List<City> from, List<City> to) { // Считаю CrowFlight и записываю результаты
        List<Result> resultList = new ArrayList<>();
        if (from.isEmpty() || to.isEmpty()) {
            logger.warn("Lists of Cities are empty!");
        }
        for (City cityFrom : from) {
            for (City cityTo : to) {
                Result result = getResult(cityFrom, cityTo);
                resultList.add(result);
                Distance distance = new Distance();
                distance.setDistance(result.getCrowFlightDistance());
                distance.setFromCity(cityFrom);
                distance.setToCity(cityTo);
                distanceRepository.save(distance);
            }
        }
        return resultList;
    }

    private Distance getMatrixDistance(City from, City to) {
        City cityFrom = cityService.getByCoordinates(from.getLatitude(), from.getLongitude());
        City cityTo = cityService.getByCoordinates(to.getLatitude(), to.getLongitude());
        return getDistanceByCities(cityFrom, cityTo);
    }

    public Distance getDistanceByCities(City from, City to) {
        return distanceRepository.getDistanceByFromCityAndToCity(from, to);
    }

    public List<Result> calculateDistanceByMatrix(List<City> from, List<City> to) { //Считаю Matrix и записываю результаты
        List<Result> resultList = new ArrayList<>();
        for (City cityFrom : from) {
            for (City cityTo : to) {
                Result result = new Result();
                Distance distance = getMatrixDistance(cityFrom, cityTo);
                if (distance.getDistance() != null) {
                    result.setTo(distance.getToCity());
                    result.setFrom(distance.getFromCity());
                    result.setMatrixDistance(distance.getDistance());
                } else {
                    logger.warn("The distance is null!");
                }
                resultList.add(result);
                logger.info("Results list with distances by Matrix is ready!");
            }
        }
        return resultList;
    }

    public List<Result> calculateDistanceAll(List<City> from, List<City> to) {
        List<Result> resultList = new ArrayList<>();
        for (City cityFrom : from) {
            for (City cityTo : to) {
                Result result = getResult(cityFrom, cityTo);
                Distance distance = getMatrixDistance(cityFrom, cityTo);
                if (distance.getDistance() != null) {
                    result.setMatrixDistance(distance.getDistance());
                    result.setFrom(cityFrom);
                    result.setTo(cityTo);
                } else {
                    logger.warn("Can't calculate distance by matrix");
                }
                resultList.add(result);
                logger.info("Results list with distances by all types of calculations is ready!");
            }
        }
        return resultList;
    }

    private Result getResult(City from, City to) {
        Result result = new Result();
        int radiusOfEarth = 6371;
        double deg2rad = Math.PI / 180;
        double dlng = deg2rad * (to.getLongitude() - from.getLongitude());
        double dlat = deg2rad * (to.getLatitude() - from.getLatitude());
        double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad * (from.getLatitude()))
                * cos(deg2rad * (to.getLatitude())) * sin(dlng / 2) * sin(dlng / 2);
        result.setCrowFlightDistance(2 * atan2(sqrt(a), sqrt(1 - a)) * radiusOfEarth);
        return result;
    }

}
