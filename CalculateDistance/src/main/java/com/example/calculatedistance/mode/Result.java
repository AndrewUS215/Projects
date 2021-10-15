package com.example.calculatedistance.mode;

import com.example.calculatedistance.entity.City;

public class Result {
    private Double crowFlightDistance;
    private Double matrixDistance;
    private City from;
    private City to;

    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public City getTo() {
        return to;
    }

    public void setTo(City to) {
        this.to = to;
    }

    public Double getCrowFlightDistance() {
        return crowFlightDistance;
    }

    public void setCrowFlightDistance(Double crowFlightDistance) {
        this.crowFlightDistance = crowFlightDistance;
    }

    public Double getMatrixDistance() {
        return matrixDistance;
    }

    public void setMatrixDistance(Double matrixDistance) {
        this.matrixDistance = matrixDistance;
    }

}
