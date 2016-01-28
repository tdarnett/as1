package com.example.taylorarnett.as1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditEntry extends EntryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // to return to the home screen
                Intent intent = new Intent(EditEntry.this, main.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String index_receive = intent.getStringExtra("index");
        final int index_r = Integer.parseInt(index_receive);
        loadFromFile();
        initializeFields(index_r);
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
                    float fuelCost = fuelAmount * (fuelUnitCost / 100);
                    Entry latestEntry = new Entry(date, station, fuelGrade, odometer, fuelAmount, fuelUnitCost, fuelCost);
                    entries.remove(index_r);
                    entries.add(index_r, latestEntry);
                    saveInFile();
                    // to return to the home screen
                    Intent intent = new Intent(EditEntry.this, main.class);
                    startActivity(intent);
                }
            }


    });

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
        verifyFields();

    }

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


