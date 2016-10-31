package com.csc207.project.flights;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * An Activity for booking Itineraries.
 */
public class BookItineraryActivity extends AppCompatActivity 
	implements View.OnClickListener{
    private Button bToMenu,bBookItin;
    private TextView tvItinDepDate, tvItinArrDate, tvItinDest, tvItinCost,
	    tvItinOrigin, tvItineraryBooked;
    private String itinString;
    private Itinerary itin;
    private ClientManager clientManager = LoginActivity.clientManager;
    private FlightManager flightManager = LoginActivity.flightManager;
    private Boolean isAdmin;
    private EditText etClient;
    private Client myClient;
    private boolean isBooked = false;

    /**
     * On creation, show all the selected Itinerary's information and allow
     * a Client to book it for themselves, or an Admin to book for any Client.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_itinerary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        itinString = bundle.getString("itinerary");
        itin = new Itinerary(itinString);
        isAdmin = bundle.getBoolean("isAdmin");

        tvItinDepDate = (TextView) findViewById(R.id.tvItinDepDate);
        tvItinDepDate.setText(itin.getDepDate());
        tvItinArrDate = (TextView) findViewById(R.id.tvItinArrDate);
        tvItinArrDate.setText(itin.getArrDate());
        tvItinOrigin = (TextView) findViewById(R.id.tvItinOrigin);
        tvItinOrigin.setText(itin.getOrigin());
        tvItinDest = (TextView) findViewById(R.id.tvItinDest);
        tvItinDest.setText(itin.getDest());
        tvItinCost = (TextView) findViewById(R.id.tvItinCost);
        tvItinCost.setText("$" + String.format("%.2f", itin.getCost()));
        tvItineraryBooked = (TextView) findViewById(R.id.tvItineraryBooked);

        etClient = (EditText) findViewById(R.id.etClient);
        etClient.setVisibility(View.INVISIBLE);

        bBookItin = (Button) findViewById(R.id.bBookItin);
        bBookItin.setOnClickListener(this);
        bToMenu = (Button) findViewById(R.id.bToMenu);
        bToMenu.setOnClickListener(this);

        if (isAdmin){
            etClient.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Listen for button clicks, if the User is an Admin check if the Client
     * they are booking for exists. If the user is a Client, book the Itinerary
     * for that Client.
     */
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bBookItin:
                String email;
                if (isAdmin) {
                    // Admins enter the email of the client they want to view
                    email = etClient.getText().toString();

                    // Check if valid email
                    if (clientManager.getClients().keySet().contains(email)) {
                        HashMap<String, Client> clients 
				= clientManager.getClients();
                        myClient = clients.get(email);
                        bookItin();

                    } else {
                        // Display error message
                        etClient.setText("");
                        etClient.setHint("Client not found");
                        etClient.setHintTextColor(Color.parseColor("#FF4081"));
                    }
                }else {
                    myClient = clientManager.getClients()
			    .get(MenuActivity.signedInEmail);
                    bookItin();
                }


                break;
            case R.id.bToMenu:
                finish();
                break;
        }
    }

    public void bookItin() {
        // Book the itinerary to myClient
        for (int i=0; i < myClient.getBookedItineraries().size(); i++) {
            if (myClient.getBookedItineraries().get(i).toString().equals(itinString)) {
                isBooked = true;
            }
        }
        if (isBooked) {
            tvItineraryBooked.setText("You already booked this itinerary.");
        } else {
            tvItineraryBooked.setText(myClient.bookItinerary(itin));
            isBooked = true;
            clientManager.saveToFile();
            flightManager.saveToFile();
        }
    }
}
