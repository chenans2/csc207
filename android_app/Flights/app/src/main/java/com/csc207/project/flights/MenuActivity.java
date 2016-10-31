package com.csc207.project.flights;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends AppCompatActivity 
	implements View.OnClickListener {

    private EditText etMenuEditText;
    private Button bEditClient, bBookedItins, bLogout, bSearchFlights, 
	    bSearchItins, bUploadFiles;
    protected static String signedInEmail;
    private ClientManager clientManager = LoginActivity.clientManager;
    private FlightManager flightManager = LoginActivity.flightManager;
    protected static Boolean isAdmin;

    /**
     * On creation, check if the User is an Admin or not and display additional
     * options if they are. Otherwise, show buttons for features for Clients.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etMenuEditText = (EditText) findViewById(R.id.etMenuEditText);
        bEditClient = (Button) findViewById(R.id.bEditClient);
        bEditClient.setOnClickListener(this);
        bBookedItins = (Button) findViewById(R.id.bBookedItins);
        bBookedItins.setOnClickListener(this);
        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);
        bSearchFlights = (Button) findViewById(R.id.bSearchFlights);
        bSearchFlights.setOnClickListener(this);
        bSearchItins = (Button) findViewById(R.id.bSearchItins);
        bSearchItins.setOnClickListener(this);
        bUploadFiles = (Button) findViewById(R.id.bUploadFiles);
        bUploadFiles.setVisibility(View.INVISIBLE);

        // Get the information from Login Activity
        Bundle bundle = getIntent().getExtras();
        signedInEmail = bundle.getString("email");

        // Determine if an Admin has signed in
        if (clientManager.getClients().keySet().contains(signedInEmail)) {
            // The email is in ClientManager -> the user signed in is a Client
            isAdmin = false;
            etMenuEditText.setText("Welcome!");
        } else {
            // The user signed in is a Admin
            isAdmin = true;
            Administrator admin = new Administrator(signedInEmail);

            // Show additional buttons for uploading
            etMenuEditText.setClickable(true);
            etMenuEditText.setCursorVisible(true);
            etMenuEditText.setFocusable(true);
            etMenuEditText.setFocusableInTouchMode(true);
            etMenuEditText.setHint("Enter client's email to search");
            bEditClient.setText("Search Clients");
            bUploadFiles.setVisibility(View.VISIBLE);
            bUploadFiles.setOnClickListener(this);
        }
    }

    /**
     * Listen for button clicks and take the User to the corresponding Activity.
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.bEditClient:

                Intent loadClient = new Intent(this, ClientActivity.class);
                String email;

                if (isAdmin) {
                    // Admins enter the email of the client they want to view
                    email = etMenuEditText.getText().toString();

                    // Check if valid email
                    if (clientManager.getClients().keySet().contains(email)) {
                        loadClient.putExtra("email", email);
                        startActivity(loadClient);
                    } else {
                        // Display error message
                        etMenuEditText.setText("");
                        etMenuEditText.setHint("Client not found");
                        etMenuEditText.setHintTextColor(Color.parseColor(
						"#FF4081"));
                    }
                } else {
                    // Client edits their own info
                    email = signedInEmail;
                    loadClient.putExtra("email", email);
                    startActivity(loadClient);
                }
                break;

            case R.id.bSearchFlights:
                Intent searchFlights = new Intent(this, 
				SearchFlightsActivity.class);
                startActivity(searchFlights);
                break;

            case R.id.bSearchItins:
                Intent searchItins = new Intent(this, 
				SearchItinsActivity.class);
                if (isAdmin){
                    searchItins.putExtra("isAdmin", true);
                } else{
                    searchItins.putExtra("isAdmin", false);
                }
                startActivity(searchItins);
                break;

            case R.id.bUploadFiles:
                Intent uploadFiles = new Intent(this, UploadActivity.class);
                startActivity(uploadFiles);
                break;

            case R.id.bBookedItins:
                Intent DisplayBookedItineraries = new Intent(this, 
				DisplayBookedItineraries.class);
                if (isAdmin){
                // Admins enter the email of the client whose booked itins 
		// they want to view

                    email = etMenuEditText.getText().toString();

                    // Check if valid email
                    if (clientManager.getClients().keySet().contains(email)) {
                        DisplayBookedItineraries.putExtra("email", email);
                        startActivity(DisplayBookedItineraries);
                    } else {
                        // Display error message
                        etMenuEditText.setText("");
                        etMenuEditText.setHint("Client not found");
                        etMenuEditText.setHintTextColor(Color.parseColor(
						"#FF4081"));
                    }
                } else {
                    email = signedInEmail;
                    DisplayBookedItineraries.putExtra("email", email);
                    startActivity(DisplayBookedItineraries);
                }
                break;

            case R.id.bLogout:
                finish();
                break;
        }
    }

}
