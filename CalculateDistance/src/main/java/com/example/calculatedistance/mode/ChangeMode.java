package com.example.calculatedistance.mode;
import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.entity.Distance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.List;

@Service
public class ChangeMode {

    private final Logger logger = Logger.getLogger(ChangeMode.class);

    @Autowired
    private CalculateDistanceByModeImpl calculateDistanceByModeImpl;

    public List<Result> calculateByCrowFlight(List<City> from, List<City> to) {
        return calculateDistanceByModeImpl.calculateDistanceByCrowFlight(from, to);
    }

    public List<Result> calculateByMatrix(List<City> from, List<City> to) {
        return calculateDistanceByModeImpl.calculateDistanceByMatrix(from, to);
    }

    public List<Result> calculateAll(List<City> from, List<City> to) {
        return calculateDistanceByModeImpl.calculateDistanceAll(from, to);
    }

    public List<Result> calculateDistance(List<City> from, List<City> to, CalcMode mode) {
        if (mode == null) {
            logger.warn("Wrong calculation mode");
            return null;
        }
        switch (mode) {
            case ALL -> {
                return calculateAll(from, to);
            }
            case MATRIX -> {
                return calculateByMatrix(from, to);
            }
            case CROWFLIGHT -> {
                return calculateByCrowFlight(from, to);
            }
        }
        logger.warn("Does not calculated!");
        return null;
    }

}
