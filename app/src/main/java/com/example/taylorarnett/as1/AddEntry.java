package com.example.taylorarnett.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
    AddEntry is called upon the main screen when the user clicks "Add New Entry".
    It extends from the EntryActivity class where it uses:
    - loadFromFile()
    - saveInFile()
    - verifyFields()
    - updatefuelCost()

    The main difference from EditEntry, is the absence of the delete functionality, and the
    saveButton appends the entry to the entries list.
    If the user does not completely fill the fields, the only option is to quit the screen.
 */
public class AddEntry extends EntryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadFromFile();
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            // If the user chooses "Cancel", nothing will be changed in the file.
            public void onClick(View v) {
                // to return to the home screen
                Intent intent = new Intent(AddEntry.this, main.class);
                startActivity(intent);
            }
        });
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // will only successfully save if the user inputs are complete and valid
                Boolean valid;
                valid = verifyFields();
                if (valid) {
                    // to get user input
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
                    //float fuelCost = fuelAmount * (fuelUnitCost/100);
                    Entry latestEntry = new Entry(date, station, fuelGrade, odometer, fuelAmount, fuelUnitCost);
                    entries.add(latestEntry);

                    saveInFile();
                    // to return to the home screen
                    Intent intent = new Intent(AddEntry.this, main.class);
                    startActivity(intent);
                }
            }
        });
        /* important to call verifyFields because it will update fuel cost even if only fuel amount
           and fuel unit cost fields are inputted.
        */
        verifyFields();

    }



}
