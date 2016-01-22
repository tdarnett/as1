package com.example.taylorarnett.as1;


import java.util.Date;

/**
 * Created by taylorarnett on 2016-01-21.
 */
public class Entry {
    protected Date date;
    protected String station;
    protected Float odometer;
    protected String fuelGrade;
    protected Float fuelAmount;
    protected Float unitCost;

    public Entry(Date date, String station, String fuelGrade, Float odometer, Float fuelAmount, Float unitCost) {
        this.date = date;
        this.station = station;
        this.fuelGrade = fuelGrade;
        this.odometer = odometer;
        this.fuelAmount = fuelAmount;
        this.unitCost = unitCost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Float getOdometer() {
        return odometer;
    }

    public void setOdometer(Float odometer) {
        this.odometer = odometer;
    }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    public Float getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(Float fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }
}
