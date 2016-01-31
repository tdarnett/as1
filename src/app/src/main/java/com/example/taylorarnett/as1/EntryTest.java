package com.example.taylorarnett.as1;

import android.test.ActivityInstrumentationTestCase2;
import java.util.ArrayList;

/**
 * Created by taylorarnett on 2016-01-28.
 */
public class EntryTest extends ActivityInstrumentationTestCase2 {
    public EntryTest() {
        super(main.class);
    }
    public void testAddEntryFullParam () {
        ArrayList<Entry> testEntry = new ArrayList<Entry>();

        Entry entry = new Entry("2012","Esso","regular",Float.valueOf("120"), Float.valueOf("120"), Float.valueOf("120"), Float.valueOf("144"));
        testEntry.add(entry);

        assertTrue(testEntry.contains(entry));

    }

    public void testAddEntryWithoutCostParam () {
        ArrayList<Entry> testEntry = new ArrayList<Entry>();

        Entry entry = new Entry("2012","Esso","regular",Float.valueOf("120"), Float.valueOf("120"), Float.valueOf("120"));
        testEntry.add(entry);
        Float cost = entry.getFuelCost();

        assertTrue(testEntry.contains(entry));
        assertEquals(cost, (entry.getFuelAmount()*(entry.getUnitCost() /100)));
    }

    public void testGetValues () {
        ArrayList<Entry> testEntry = new ArrayList<Entry>();

        Entry entry = new Entry("2012","Esso","regular",Float.valueOf("120"), Float.valueOf("120"), Float.valueOf("120"));
        testEntry.add(entry);

        String date = entry.getDate();
        String station = entry.getStation();
        String fuelGrade = entry.getFuelGrade();
        Float odometer = entry.getOdometer();
        Float fuelAmount = entry.getFuelAmount();
        Float fuelUnitCost = entry.getUnitCost();
        Float fuelCost = entry.getFuelCost();

        assertEquals(date, "2012");
        assertEquals(station,"Esso");
        assertEquals(fuelGrade,"regular");
        assertEquals(odometer, Float.valueOf("120"));
        assertEquals(fuelAmount, Float.valueOf("120"));
        assertEquals(fuelUnitCost, Float.valueOf("120"));
        assertEquals(fuelCost, Float.valueOf("144"));
    }

    public void testToString(){

        Entry entry = new Entry("2012","Esso","regular",Float.valueOf("120"), Float.valueOf("120"), Float.valueOf("120"));
        String returnString = entry.toString();
        String actualString = "2012 | Esso | 120.0km | regular | 120.000L | 120.0 cents/L | $144.00";

        assertEquals(returnString, actualString);
    }


}
