package com.example.taylorarnett.as1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
    EditEntry is called upon the main screen when the user clicks on any of the list items.
    It extends from the EntryActivity class where it inherits:
    - loadFromFile()
    - saveInFile()
    - verifyFields()
    - updatefuelCost()

    The main difference from AddEntry, is that it includes a delete option - which removes
    the entry chosen from the main screen. Also, the save button replaces the original entry
    in the file.sav file by using the index passed from the intent of the main screen.
    If the user does not completely fill the fields, the only option is to quit the screen.

 */

public class EditEntry extends EntryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // cancel button just returns user to main screen with no changes.
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // to return to the home screen
                Intent intent = new Intent(EditEntry.this, main.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        /*
            The index of the entry that was clicked in the list of entries displayed on the main
            screen is passed through the intent. Here is where we access it
        */
        String index_receive = intent.getStringExtra("index");
        final int index_r = Integer.parseInt(index_receive);
        loadFromFile();
        initializeFields(index_r);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        /*
           The differnce with the save button in this view is that upon saving, the original
           entry is removed from the list, and the new entry is added in the same index.
           The index is recieved from the intent passed from the main screen activity.
         */
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
                    //float fuelCost = fuelAmount * (fuelUnitCost / 100);
                    Entry latestEntry = new Entry(date, station, fuelGrade, odometer, fuelAmount, fuelUnitCost);
                    // remove the original entry
                    entries.remove(index_r);
                    // add the latest editted entry at the same index to preserve list order.
                    entries.add(index_r, latestEntry);
                    saveInFile();
                    // to return to the home screen
                    Intent intent = new Intent(EditEntry.this, main.class);
                    startActivity(intent);
                }
            }


    });
        /*
           If the user chooses "Delete", the current entry will be removed from the entries array.
         */
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                entries.remove(index_r);
                saveInFile();
                // to return to the home screen
                Intent intent = new Intent(EditEntry.this, main.class);
                startActivity(intent);
            }
        });
        /*
           important to call verifyFields because it will update fuel cost even if only fuel amount
           and fuel unit cost fields are inputted.
        */
        verifyFields();

    }

    /*
        initializeFields takes the index of the entry that is being eddited, and fills
        the input fields in the UI with the corresponding entries previously inputted by
        the user when they first added the entry.
     */
    public void initializeFields(int index) {
        EditText edit_date = (EditText) findViewById(R.id.date_text);
        EditText edit_station = (EditText) findViewById(R.id.station_text);
        EditText edit_odometer = (EditText) findViewById(R.id.odometer_text);
        EditText edit_fuelGrade = (EditText) findViewById(R.id.fuelGrade_text);
        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);
        TextView fuelCost_view = (TextView) findViewById(R.id.fuelCost_view);

        edit_date.setText(entries.get(index).getDate());
        edit_station.setText(entries.get(index).getStation());

        String odometer_text = String.format("%.1f", entries.get(index).getOdometer());

        edit_odometer.setText(odometer_text);

        edit_fuelGrade.setText(entries.get(index).getFuelGrade());

        String fuelAmount_text = String.format("%.3f", entries.get(index).getFuelAmount());
        edit_fuelAmount.setText(fuelAmount_text);

        String fuelUnitCost_text = String.format("%.1f", entries.get(index).getUnitCost());
        edit_fuelUnitCost.setText(fuelUnitCost_text);

        String fuelCost_text = String.format("%.2f", entries.get(index).getFuelCost());
        fuelCost_view.setText("$" + fuelCost_text);

    }

}


