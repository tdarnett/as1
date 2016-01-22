package com.example.taylorarnett.as1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                saveFields(v);
                //Intent intent = new Intent("com.example.taylorarnett.as1.main");
                //startActivity(intent);
            }
        });
    }

    public void saveFields (View view) throws InvalidEntryException{
        Boolean goodInput = false;
        EditText edit_date = (EditText) findViewById(R.id.date_text);
        EditText edit_station = (EditText) findViewById(R.id.station_text);
        EditText edit_odometer = (EditText) findViewById(R.id.odometer_text);
        EditText edit_fuelGrade = (EditText) findViewById(R.id.fuelGrade_text);
        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);
        setResult(RESULT_OK);

        String date = edit_date.getText().toString();
        String station = edit_station.getText().toString();
        String fuelGrade = edit_fuelGrade.getText().toString();
        //String odometer = edit_odometer.getText().toString();
        //String fuel
        // conversion to float found from http://stackoverflow.com/questions/4229710/string-from-edittext-to-float 01-2016-22
        while (!goodInput) {
            try {
                float odometer = Float.valueOf(edit_odometer.getText().toString());
                float fuelAmount = Float.valueOf(edit_fuelAmount.getText().toString());
                float fuelUnitCost = Float.valueOf(edit_fuelUnitCost.getText().toString());
                goodInput = true;
            } catch (Exception e) {
                throw new InvalidEntryException();
                float odometer = -1;
                float fuelAmount = -1;
                float fuelUnitCost = -1;
            }
        }
        
        Entry latestEntry = new Entry(date, station, fuelGrade, odometer, fuelAmount, fuelUnitCost);
        entries.add(latestEntry);
        //tweets.add(latestTweet);	//now we are just adding the latest tweet to the list of tweets
        //adapter.notifyDataSetChanged(); //this tells adapter to update itself
        saveInFile();
        //finish();

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
