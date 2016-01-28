package com.example.taylorarnett.as1;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

/**
 * Created by taylorarnett on 2016-01-28.
 */
public class EntryActivity extends ActionBarActivity {
    public static final String FILENAME = "file.sav";
    public ArrayList<Entry> entries = new ArrayList<Entry>();

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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

    // from lonelyTwitter application
    public void saveInFile() {
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
    public void loadFromFile() {
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

