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

/*
    EntryActivity contains all the methods in which both AddEntry and EditEntry inherit from.
 */
public class EntryActivity extends ActionBarActivity {
    public static final String FILENAME = "file.sav";
    public ArrayList<Entry> entries = new ArrayList<Entry>();

    /*
        verifyFields is used to check if the user input is complete and appropriate.
        It does this by using a try/catch block in case there are any errors when
        converting the user input strings into their respective data types.
        If every entry is valid then the method return true, otherwise it returns false.

        The other important part of this method is that it tracks when the user is finished
        entering input in the fuel amount, and fuel unit cost fields. It does this by using
        TextWatcher. This is used so the fuel cost can be dynamically calculated.
     */
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
                    // Otherwise the input is not complete and we must output $0.00
                    //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
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
                    // Otherwise the input is not complete and we must output $0.00
                    //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
                    //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
                    String fuelCost_text = "$0.00";
                    TextView fuelCost_view = (TextView) findViewById(R.id.fuelCost_view);
                    fuelCost_view.setText(fuelCost_text);
                    setResult(RESULT_OK);
                }
            }
        });
        return validFields;
    }

    /*
        updatefuelCost is called when the user has entered in the fields for fuel amount and fuel
        unit cost. It simply takes the input of these two fields and uses them to calculate and
        output the fuel cost.
     */
    public void updatefuelCost() {

        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);

        float fuelAmount = Float.valueOf(edit_fuelAmount.getText().toString());
        float fuelUnitCost = Float.valueOf(edit_fuelUnitCost.getText().toString());
        float fuelCost = fuelAmount * (fuelUnitCost / 100);

        //display fuelCost from http://stackoverflow.com/questions/5402637/displays-float-into-text-view 01-2016-24
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

