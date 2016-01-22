package com.example.taylorarnett.as1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class AddEntry extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        //Intent intent = getIntent();
    }

    public void saveFields (View view) {
        EditText edit_date = (EditText) findViewById(R.id.date_text);
        EditText edit_station = (EditText) findViewById(R.id.station_text);
        EditText edit_odometer = (EditText) findViewById(R.id.odometer_text);
        EditText edit_fuelGrade = (EditText) findViewById(R.id.fuelGrade_text);
        EditText edit_fuelAmount = (EditText) findViewById(R.id.fuelAmount_text);
        EditText edit_fuelUnitCost = (EditText) findViewById(R.id.fuelUnitCost_text);

    }

}
