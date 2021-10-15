package com.example.calculatedistance.entity;


import javax.persistence.*;

@Entity
@Table(name = "distance")
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "toCity")
    private City toCity;

    @ManyToOne
    @JoinColumn(name = "fromCity")
    private City fromCity;

    private Double distance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "id=" + id +
                ", toCity=" + toCity +
                ", fromCity=" + fromCity +
                ", distance=" + distance +
                '}';
    }
}
