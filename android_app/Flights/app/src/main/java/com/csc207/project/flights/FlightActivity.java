package com.csc207.project.flights;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightActivity extends AppCompatActivity 
	implements View.OnClickListener {
    private EditText etFlightNo, etAirline, etOrigin, etDestination, etCost, 
	    etDepDate, etArrDate, etNumSeats;
    private TextView tvFlightSaved;
    private Button bFlightUpdate, bBack;
    private Flight myFlight;
    private FlightManager flightManager = LoginActivity.flightManager;
    private Date ArrDate, DepDate;

    /**
     * On creation of this Activity, create editable text fields and fill them
     * with the Flight's information. Also create a button for submitting
     * changes.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String flightID = bundle.getString("flightID");

        try {
            myFlight = LoginActivity.flightManager.getAllFlights().getFlight(
			    Integer.parseInt(flightID));
        } catch (NoSuchFlightException e) { }

        etFlightNo = (EditText) findViewById(R.id.etFlightNo);
        etAirline = (EditText) findViewById(R.id.etAirline);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDest);
        etCost = (EditText) findViewById(R.id.etCost);
        etDepDate = (EditText) findViewById(R.id.etDepDate);
        etArrDate = (EditText) findViewById(R.id.etArrDate);
        etNumSeats = (EditText) findViewById(R.id.etNumSeats);
        tvFlightSaved = (TextView) findViewById(R.id.tvFlightSaved);
        tvFlightSaved.setVisibility(View.INVISIBLE);
        bFlightUpdate = (Button) findViewById(R.id.bFlightUpdate);
        bFlightUpdate.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

        etFlightNo.setText(myFlight.getFlightNo());
        etAirline.setText(myFlight.getAirline());
        etOrigin.setText(myFlight.getOrigin());
        etDestination.setText(myFlight.getDestination());
        etCost.setText(String.format("%.2f", myFlight.getCost()));
        etDepDate.setText(myFlight.getDepDateTime());
        etArrDate.setText(myFlight.getArrDateTime());
        etNumSeats.setText(String.valueOf(myFlight.getNumSeats()));
    }

    /**
     * Listen for button clicks, when 'Update' is selected, change the Flight's
     * information to the information in the text fields and save changes to
     * to the serialized file with all Flights.
     */
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bFlightUpdate:

                // Get the info in the text boxes and update the Flight's info
                myFlight.setFlightNo(etFlightNo.getText().toString());
                myFlight.setAirline(etAirline.getText().toString());
                myFlight.setOrigin(etOrigin.getText().toString());
                myFlight.setDestination(etDestination.getText().toString());
                myFlight.setCost(Double.parseDouble(etCost.getText()
					.toString()));
                myFlight.setNumSeats(Integer.parseInt(etNumSeats.getText().toString()));

                try {
                    ArrDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(
				    etArrDate.toString());
                    DepDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(
				    etDepDate.toString());
                    myFlight.setDepDateTime(DepDate);
                    myFlight.setArrDateTime(ArrDate);

                } catch(ParseException e) {}

                // Save to serialized file
                flightManager.saveToFile();
                tvFlightSaved.setVisibility(View.VISIBLE);
                break;

            case R.id.bBack:
                finish();
                break;
        }
    }
}
