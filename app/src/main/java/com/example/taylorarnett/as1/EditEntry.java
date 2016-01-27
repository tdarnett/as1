package com.example.taylorarnett.as1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
// much of this is copied from AddEntry.java.
public class EditEntry extends ActionBarActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Entry> entries = new ArrayList<Entry>();

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
                    entries.add(index_r,latestEntry);


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
    
    public void initializeFields (int index) {
        EditText edit_date = (EditText) findViewById(R.id.date_text);
        EditText edit_station = (EditText) findViewById(R.id.station_text);
        EditText edit_odometer = (EditText) findViewById(R.id.odometer_text);
        EditText edit_fuelGrade = (EditText) findViewById(R.id.fuelGrade_text);
        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);
        TextView fuelCost_view = (TextView) findViewById(R.id.fuelCost_view);
        
        edit_date.setText(entries.get(index).getDate());

        edit_station.setText(entries.get(index).getStation());

        String odometer_text = String.format("%.1f",entries.get(index).getOdometer());
        edit_odometer.setText(odometer_text);

        edit_fuelGrade.setText(entries.get(index).getFuelGrade());

        String fuelAmount_text = String.format("%.3f",entries.get(index).getFuelAmount());
        edit_fuelAmount.setText(fuelAmount_text);

        String fuelUnitCost_text = String.format("%.1f",entries.get(index).getUnitCost());
        edit_fuelUnitCost.setText(fuelUnitCost_text);

        String fuelCost_text = String.format("%.2f", entries.get(index).getFuelCost());
        fuelCost_view.setText("$"+fuelCost_text);

    }

    public void updatefuelCost() {

        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);

        float fuelAmount = Float.valueOf(edit_fuelAmount.getText().toString());
        float fuelUnitCost = Float.valueOf(edit_fuelUnitCost.getText().toString());
        float fuelCost = fuelAmount * (fuelUnitCost / 100);

        //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
        //String fuelCost_text = Float.toString(fuelCost);
        String fuelCost_text = String.format("%.2f", fuelCost);

        TextView fuelCost_view = (TextView) findViewById(R.id.fuelCost_view);
        fuelCost_view.setText("$" + fuelCost_text);
        setResult(RESULT_OK);

    }
// this verifies that the fields are valid then saves them

    public boolean verifyFields() {
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
                    TextView fuelCost_view = (TextView) findViewById(R.id.fuelCost_view);
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
                    TextView fuelCost_view = (TextView) findViewById(R.id.fuelCost_view);
                    fuelCost_view.setText(fuelCost_text);
                    setResult(RESULT_OK);
                }
            }
        });
        return validFields;
    }

    // from lonelyTwitter application
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

    // from lonelyTwitter application
    private void loadFromFile() {
        entries = new ArrayList<Entry>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-2016-19
            Type listType = new TypeToken<ArrayList<Entry>>() {
            }.getType();
            entries = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            entries = new ArrayList<Entry>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }
}

