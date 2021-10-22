package com.example.calculatedistance.service;
import com.example.calculatedistance.entity.City;
import com.example.calculatedistance.mode.CalcMode;
import com.example.calculatedistance.mode.CalculateDistanceByModeImpl;
import com.example.calculatedistance.mode.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeModeService {

    private final Logger logger = Logger.getLogger(ChangeModeService.class);

    @Autowired
    private CalculateDistanceByModeImpl calculateDistanceByModeImpl;

    public List<Result> calculateDistance(List<City> from, List<City> to, CalcMode mode) {
        if (mode == null) {
            logger.warn("Wrong calculation mode");
            return null;
        }
        switch (mode) {
            case ALL -> {
                return calculateDistanceByModeImpl.calculateDistanceAll(from, to);
            }
            case MATRIX -> {
                return calculateDistanceByModeImpl.calculateDistanceByMatrix(from, to);
            }
            case CROWFLIGHT -> {
                return calculateDistanceByModeImpl.calculateDistanceByCrowFlight(from, to);
            }
        }
        logger.warn("Does not calculated!");
        return null;
    }

}
