package com.example.taylorarnett.as1;



/**
 * Created by taylorarnett on 2016-01-21.
 */

/*
    this creates an Entry object with all the necessary fields.
    Getters are used for when the user wishes to edit an entry.
    Setter are not used because all the fields are given at once and
    one call to the constructor suffices.
 */
public class Entry {
    protected String date;
    protected String station;
    protected Float odometer;
    protected String fuelGrade;
    protected Float fuelAmount;
    protected Float unitCost;
    protected Float fuelCost;

    // this constructor is used when all parameters are given, including fuel cost.
    public Entry(String date, String station, String fuelGrade, Float odometer, Float fuelAmount, Float unitCost, Float fuelCost) {
        this.date = date;
        this.station = station;
        this.fuelGrade = fuelGrade;
        this.odometer = odometer;
        this.fuelAmount = fuelAmount;
        this.unitCost = unitCost;
        this.fuelCost = fuelCost;
    }

    // constructor used when fuel cost is not previously calculated or passed in as a parameter.
    public Entry(String date, String station, String fuelGrade, Float odometer, Float fuelAmount, Float unitCost) {
        this.date = date;
        this.station = station;
        this.fuelGrade = fuelGrade;
        this.odometer = odometer;
        this.fuelAmount = fuelAmount;
        this.unitCost = unitCost;
        this.fuelCost = fuelAmount * (unitCost/100);
    }

    public String getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public Float getOdometer() {
        return odometer;
    }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public Float getFuelAmount() {
        return fuelAmount;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public Float getFuelCost() {
        return fuelCost;
    }

    @Override
    public String toString() {
        // for correct output
        String odometer_out = String.format("%.1f", odometer);
        String fuelAmount_out = String.format("%.3f",fuelAmount);
        String fuelUnitCost_out = String.format("%.1f", unitCost);
        String fuelCost_out = String.format("%.2f", fuelCost);
        return date + " | "+ station+ " | " + odometer_out + "km | " + fuelGrade
                + " | " + fuelAmount_out + "L | " + fuelUnitCost_out + " cents/L | $" + fuelCost_out;
    }
}
