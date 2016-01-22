package com.example.taylorarnett.as1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
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

}
