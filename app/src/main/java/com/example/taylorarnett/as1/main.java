package com.example.taylorarnett.as1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class main extends ActionBarActivity {

    private static final String FILENAME = "file.sav";
    private ListView oldEntriesList;
    private ArrayList<Entry> entries = new ArrayList<Entry>(); //this creates a list of entries
    private ArrayAdapter<Entry> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.new_entry);
        oldEntriesList = (ListView) findViewById(R.id.oldEntriesList);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("com.example.taylorarnett.as1.AddEntry");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Entry>(this,
                R.layout.activity_list_entries,entries);
        oldEntriesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // to output statistics
        Entry entry;
        float fuelCostSum = 0;
        for (int i = 0; i < entries.size();i++ ){
            entry = entries.get(i);
            fuelCostSum = fuelCostSum + entry.getFuelCost();
        }
        TextView statistics_view = (TextView) findViewById(R.id.statistics);
        String fuelCostSum_text = String.format("%.2f", fuelCostSum);
        statistics_view.setText("Total Fuel Cost: $" + fuelCostSum_text);

        //http://stackoverflow.com/questions/2468100/android-listview-click-howto
        oldEntriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.taylorarnett.as1.EditEntry");
                String index = String.valueOf(position);
                // http://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-on-android
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFromFile() {
        entries = new ArrayList<Entry>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-2016-19
            Type listType = new TypeToken<ArrayList<Entry>>() {}.getType();
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
