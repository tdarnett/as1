package com.example.taylorarnett.as1;



/**
 * Created by taylorarnett on 2016-01-21.
 */
public class Entry {
    protected String date;
    protected String station;
    protected Float odometer;
    protected String fuelGrade;
    protected Float fuelAmount;
    protected Float unitCost;
    protected Float fuelCost;

    public Entry(String date, String station, String fuelGrade, Float odometer, Float fuelAmount, Float unitCost, Float fuelCost) {
        this.date = date;
        this.station = station;
        this.fuelGrade = fuelGrade;
        this.odometer = odometer;
        this.fuelAmount = fuelAmount;
        this.unitCost = unitCost;
        this.fuelCost = fuelCost;
    }

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

    public void setDate(String date) throws InvalidEntryException{
        String str = "-";
        // contains method found http://stackoverflow.com/questions/2275004/in-java-how-do-i-check-if-a-string-contains-a-substring-ignoring-case
        if ((date.length() != 10) || (!date.contains(str))) {
            throw new InvalidEntryException();
        }
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) throws InvalidEntryException {
        if (station.length() == 0){
            throw new InvalidEntryException();
        }
        this.station = station;
    }

    public Float getOdometer() {
        return odometer;
    }

    public void setOdometer(Float odometer) throws InvalidEntryException{
        try {
            this.odometer = odometer;
        }
        // catch portion from http://stackoverflow.com/questions/9754103/numberformatexception-error 01-2016-22
        catch (Exception e) {
            throw new InvalidEntryException();
        }

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

    public void setFuelAmount(Float fuelAmount) throws InvalidEntryException{
        try {
            this.fuelAmount = fuelAmount;
        }
        // catch portion from http://stackoverflow.com/questions/9754103/numberformatexception-error 01-2016-22
        catch (Exception e) {
            throw new InvalidEntryException();
        }

    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) throws InvalidEntryException{
        try {
            this.unitCost = unitCost;
        }
        // catch portion from http://stackoverflow.com/questions/9754103/numberformatexception-error 01-2016-22
        catch (Exception e) {
            throw new InvalidEntryException();
        }

    }

    public Float getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Float fuelCost) {
        this.fuelCost = fuelCost;
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
