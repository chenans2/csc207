package com.csc207.project.flights;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayFlightsActivity extends AppCompatActivity implements View.OnClickListener {

    private String flights;
    protected FlightArrayAdapter adapter;
    private Button bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

        final ListView lvFlights = (ListView) findViewById(R.id.lvFlights);

        Bundle bundle = getIntent().getExtras();
        flights = bundle.getString("matching flights");
        FlightManager flightManager = LoginActivity.flightManager;
        String[] matchingFlights = flights.split("\n");
        final ArrayList<String> flightList = new ArrayList<>();

        // For each matching flight string
        for (String flightStr : matchingFlights) {

            // Get the flight number
            int flightNo = Integer.parseInt(flightStr.split(",")[0]);

            try {
                // Get the flight corresponding to the flight number
                Flight myFlight = flightManager.getAllFlights().getFlight(flightNo);

                // Format a new string to display flight info
                String flightInfo = String.format("Flight No. %s \n"
                                + "Departs on %s from %s \n"
                                + "Arrives on %s at %s \n" + "Airline: %s \n"
                                + "Cost: $%.2f \n" + "Travel Time: %s mins. \n"
                                + "Number of Available Seats: %s",
                        myFlight.getFlightNo(), myFlight.getDepDateTime(),
                        myFlight.getOrigin(), myFlight.getArrDateTime(),
                        myFlight.getDestination(), myFlight.getAirline(),
                        myFlight.getCost(), String.valueOf(myFlight.getTime()),
                        myFlight.getNumSeats());
                flightList.add(flightInfo);
            } catch (NoSuchFlightException e) {
                e.printStackTrace();
            }
        }
        final Intent flightActivity = new Intent(this, FlightActivity.class);
        adapter = new FlightArrayAdapter(this,
                android.R.layout.simple_list_item_1, flightList);
        lvFlights.setAdapter(adapter);

        lvFlights.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            if (MenuActivity.isAdmin) {

                String info = String.valueOf(parent.getItemAtPosition(position));
                String flightId = info.split("\n")[0].split(" ")[2];

                flightActivity.putExtra("flightID", flightId);
                startActivity(flightActivity);
                finish();
            }
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bBack:
                finish();
                break;
        }
    }

    private class FlightArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap= new HashMap<>();

        public FlightArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); i++) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
