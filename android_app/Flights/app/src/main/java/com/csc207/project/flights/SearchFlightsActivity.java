package com.csc207.project.flights;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * An Activity for Users to search Flights.
 */
public class SearchFlightsActivity extends AppCompatActivity
       	implements View.OnClickListener {

    private Button bSearchFlights, bToMenu;
    private EditText etOrigin, etDest, etDepDate;
    private TextView tvFlightsError;
    private FlightManager flightManager = LoginActivity.flightManager;
    private int timeOrCost = 0;

    /**
     * On creation, load up the text fields for the User to enter 
     * search criteria and a button to submit.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etOrigin = (EditText) findViewById(R.id.etFlightOrigin);
        etDest = (EditText) findViewById(R.id.etFlightDest);
        etDepDate = (EditText) findViewById(R.id.etFlightDepDate);
        bSearchFlights = (Button) findViewById(R.id.bSearchFlights);
        bSearchFlights.setOnClickListener(this);
        bToMenu = (Button) findViewById(R.id.bToMenu);
        bToMenu.setOnClickListener(this);
        tvFlightsError = (TextView) findViewById(R.id.tvFlightsError);
        tvFlightsError.setVisibility(View.INVISIBLE);
    }


    /**
     * Listen for button clicks, find matching Flighst when submit is clicked.
     */
    @Override
    public void onClick(View v){

        String depDate = etDepDate.getText().toString();
        String dest = etDest.getText().toString();
        String origin = etOrigin.getText().toString();

        switch(v.getId()) {
            case R.id.bSearchFlights:

                tvFlightsError.setVisibility(View.INVISIBLE);

                // Get matching flights
                String matchingFlights = flightManager.getFlights(depDate,
			       	origin, dest);


                // No matching flights
                if (matchingFlights.equals("")) {
                    // Display error message
                    tvFlightsError.setVisibility(View.VISIBLE);
                } else {
                    // Load Display Flights Activity
                    Intent DisplayFlights = new Intent(this, 
				    DisplayFlightsActivity.class);
                    DisplayFlights.putExtra("matching flights",
				    matchingFlights);
                    DisplayFlights.putExtra("flight manager", flightManager);
                    startActivity(DisplayFlights);
                }
                break;

            case R.id.bToMenu:
                finish();
                break;
        }
    }
}
