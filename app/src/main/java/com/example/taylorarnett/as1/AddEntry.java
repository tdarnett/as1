package com.example.taylorarnett.as1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AddEntry extends ActionBarActivity {
    private static final String FILENAME = "file.txt";
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Boolean valid;
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
                    float fuelCost = fuelAmount * fuelUnitCost;
                    Entry latestEntry = new Entry(date, station, fuelGrade, odometer, fuelAmount, fuelUnitCost, fuelCost);
                    entries.add(latestEntry);

                    //adapter.notifyDataSetChanged(); //this tells adapter to update itself
                    saveInFile();
                    // to return to the home screen
                    Intent intent = new Intent(AddEntry.this, main.class);
                    startActivity(intent);
                }
            }
        });


        verifyFields();

    }

    public void updatefuelCost() {

        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);

        float fuelAmount = Float.valueOf(edit_fuelAmount.getText().toString());
        float fuelUnitCost = Float.valueOf(edit_fuelUnitCost.getText().toString());
        float fuelCost = fuelAmount * fuelUnitCost;

        //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
        //String fuelCost_text = Float.toString(fuelCost);
        String fuelCost_text= String.format("%.2f", fuelCost);

        TextView fuelCost_view = (TextView)findViewById(R.id.fuelCost_view);
        fuelCost_view.setText("$"+fuelCost_text);
        setResult(RESULT_OK);

    }
// this verifies that the fields are valid then saves them

    public boolean verifyFields (){
        Boolean validFields = false;
        String date = "";
        String station = "";
        String fuelGrade = "";
        float odometer = -1;
        float fuelAmount = -1;
        float fuelUnitCost = -1;

        EditText edit_date = (EditText) findViewById(R.id.date_text);
        EditText edit_station = (EditText) findViewById(R.id.station_text);
        EditText edit_odometer = (EditText) findViewById(R.id.odometer_text);
        EditText edit_fuelGrade = (EditText) findViewById(R.id.fuelGrade_text);
        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);
        try {
            date = edit_date.getText().toString();
            station = edit_station.getText().toString();
            fuelGrade = edit_fuelGrade.getText().toString();
            odometer = Float.valueOf(edit_odometer.getText().toString());
            fuelAmount = Float.valueOf(edit_fuelAmount.getText().toString());
            fuelUnitCost = Float.valueOf(edit_fuelUnitCost.getText().toString());
            validFields = true;
        } catch (Exception e) {
            validFields = false;

        }
        // Text Watcher info from http://stackoverflow.com/questions/20824634/android-on-text-change-listener 01-2016-24
        edit_fuelUnitCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    updatefuelCost();
                } catch (Exception e) {
                    //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
                    //String fuelCost_text = Float.toString(0);
                    String fuelCost_text = "$0.00";
                    TextView fuelCost_view = (TextView)findViewById(R.id.fuelCost_view);
                    fuelCost_view.setText(fuelCost_text);
                    setResult(RESULT_OK);

                }
            }
        });
        edit_fuelAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    updatefuelCost();
                } catch (Exception e) {
                    //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
                    //String fuelCost_text = Float.toString(0);
                    String fuelCost_text = "$0.00";
                    TextView fuelCost_view = (TextView)findViewById(R.id.fuelCost_view);
                    fuelCost_view.setText(fuelCost_text);
                    setResult(RESULT_OK);
                }
            }
        });
        return validFields;
    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(entries, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException(); //easier to debug
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
