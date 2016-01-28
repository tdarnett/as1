package com.example.taylorarnett.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEntry extends EntryActivity {
    //private static final String FILENAME = "file.sav";
   // private ArrayList<Entry> entries = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadFromFile();
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // to return to the home screen
                Intent intent = new Intent(AddEntry.this, main.class);
                startActivity(intent);
            }
        });
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Boolean valid;
                valid = verifyFields();
                if (valid) {
                    setResult(RESULT_OK);
                    EditText edit_date = (EditText) findViewById(R.id.date_text);
                    EditText edit_station = (EditText) findViewById(R.id.station_text);
                    EditText edit_odometer = (EditText) findViewById(R.id.odometer_text);
                    EditText edit_fuelGrade = (EditText) findViewById(R.id.fuelGrade_text);
                    EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
                    EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);
                    String date = edit_date.getText().toString();
                    String station = edit_station.getText().toString();
                    String fuelGrade = edit_fuelGrade.getText().toString();
                    float odometer = Float.valueOf(edit_odometer.getText().toString());
                    float fuelAmount = Float.valueOf(edit_fuelAmount.getText().toString());
                    float fuelUnitCost = Float.valueOf(edit_fuelUnitCost.getText().toString());
                    float fuelCost = fuelAmount * (fuelUnitCost/100);
                    Entry latestEntry = new Entry(date, station, fuelGrade, odometer, fuelAmount, fuelUnitCost, fuelCost);
                    entries.add(latestEntry);

                    saveInFile();
                    // to return to the home screen
                    Intent intent = new Intent(AddEntry.this, main.class);
                    startActivity(intent);
                }
            }
        });
        verifyFields();

    }



}
