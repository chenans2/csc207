package com.csc207.project.flights;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An Activity for searching Users to search for Itineraries.
 */
public class SearchItinsActivity extends AppCompatActivity 
	implements View.OnClickListener {

    private Button bSearchItins, bToMenu;
    private EditText etOrigin, etDest, etDepDate;
    private TextView tvItinsError;
    private RadioButton radioTime, radioCost;
    private FlightManager flightManager = LoginActivity.flightManager;
    private int timeOrCost = 0;
    private boolean isAdmin;

    // ArrayList to store most recently searched for Itineraries
    private ArrayList<Itinerary> searchedItins = new ArrayList<>();

    // Graph to store all Flights and their connecting Flights
    public Graph flights = flightManager.getAllFlights();

    /**
     * On creation, create text fields for the User to enter search criteria,
     * and a button for submitting.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itins);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        etOrigin = (EditText) findViewById(R.id.etItinOrigin);
        etDest = (EditText) findViewById(R.id.etItinDest);
        etDepDate = (EditText) findViewById(R.id.etItinDepDate);

        radioTime = (RadioButton) findViewById(R.id.radioTime);
        radioCost = (RadioButton) findViewById(R.id.radioCost);
        tvItinsError = (TextView) findViewById(R.id.tvItinsError);
        tvItinsError.setVisibility(View.INVISIBLE);

        bSearchItins = (Button) findViewById(R.id.bSearchItins);
        bSearchItins.setOnClickListener(this);
        bToMenu = (Button) findViewById(R.id.bToMenu);
        bToMenu.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        isAdmin = bundle.getBoolean("isAdmin");
    }

    /**
     * Set how to sort results when an option is selected.
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioTime:
                if (checked)
                    timeOrCost = 0;
                break;
            case R.id.radioCost:
                if (checked)
                    timeOrCost = 1;
                break;
        }
    }

    /**
     * Listen for button clicks, search for and sort the resulting Itineraries
     * when 'submit' is clicked.
     */
    public void onClick(View v) {

        // Get user input from EditText
        String depDate = etDepDate.getText().toString();
        String dest = etDest.getText().toString();
        String origin = etOrigin.getText().toString();


        switch (v.getId()) {
            case R.id.bSearchItins:

                System.out.println(flights.getFlights().toString());

                // Attempt to generate list of Itineraries
                try {
                    getItineraries(depDate, origin, dest);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // No matching flights
                if (searchedItins.isEmpty()) {
                    // Display error message
                    tvItinsError.setVisibility(View.VISIBLE);
                } else {
                    Intent DisplayItins = new Intent(this, 
				    DisplayItinerariesActivity.class);
                    if (isAdmin) {
                        DisplayItins.putExtra("isAdmin", true);
                    }
                    if (timeOrCost == 0) {
                        DisplayItins.putExtra("searchedItins", sortByTime());
                        startActivity(DisplayItins);
                    } else {
                        DisplayItins.putExtra("searchedItins", sortByCost());
                        startActivity(DisplayItins);
                    }
                }
                break;

            case R.id.bToMenu:
                finish();
                break;
        }
    }


    /**
     * Adds an Itinerary with myFlight as its first Flight
     * and matches the appropriate date, origin, and destination to this
     * Applications searchedItins variable.
     *
     * @param 	flightList The list of Flights that will lead to the searched
     * 			for destination
     * @param 	date The departure date of the latest Flight
     * @param 	origin The origin of the Flight to make an Itinerary starting
     * 			from
     * @param 	destination The destination goal for the Itinerary
     * @throws ParseException
     */
    private void createItineraries(ArrayList<Flight> flightList,
                                   Flight myFlight, String date, String origin,
                                   String destination) throws ParseException {
        flightList.add(myFlight);
        if (myFlight.getDestination().equals(destination)) {
            searchedItins.add(new Itinerary(flightList));
        } else {
            try {
                for (Flight flight : flights.getLinkedFlights(myFlight)) {
                    if (flight.getNumSeats() > 0) {
                        ArrayList<Flight> newFlightList = new ArrayList<>();
                        newFlightList.addAll(flightList);
                        createItineraries(newFlightList, flight,
                                myFlight.getArrDate(),
                                myFlight.getDestination(), destination);
                    }
                }
            } catch (NullPointerException e) {
                //
            }
        }
    }

    /**
     * Return all Itineraries matching the given criteria as a String, and sets
     * the searchedItins variable to match the given criteria.
     *
     * @param date the departure date of the first flight
     * @param origin the origin of the Itineraries to find
     * @param destination the destination of the Itineraries to find
     * @return all Itineraries matching the criteria as a String
     * @throws ParseException
     */
    public String getItineraries(String date, String origin,
                                 String destination) throws ParseException {

        // Since a new search is being made, reset searchedItins
        this.searchedItins.clear();

        for (Flight myFlight: flights.getFlights()) {
            if (myFlight.getDepDate().equals(date) && myFlight.getOrigin()
                    .equals(origin) && myFlight.getNumSeats() > 0) {
                createItineraries(new ArrayList<Flight>(), myFlight, date,
                        origin, destination);
            }
        }
        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString();
        }
        return itineraryStr;
    }

    /**
     * Sorts Itineraries searchedItins according to time, from least to
     * greatest.
     *
     * @return the String representation of this Itinerary sorted by time
     */
    public String sortByTime() {
        // Itineraries already generated and stored in searchedItins variable
        Collections.sort(searchedItins, Itinerary.timeComparator);

        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString() + "~";
        }
        return itineraryStr;
    }

    /**
     * Sorts Itineraries searchedItins according to cost, from least to
     * greatest, then returns the them as a String.
     *
     * @return the String representation of this Itinerary sorted by cost
     */
    public String sortByCost() {
        // 	Itineraries already generated and stored in itineraries variable
        Collections.sort(searchedItins, Itinerary.costComparator);
        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString() + "~";
        }
        return itineraryStr;
    }

}
