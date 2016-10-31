package com.csc207.project.flights;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DisplayBookedItineraries extends AppCompatActivity {
    private String itins;
    protected ItinArrayAdapter adapter;
    private ClientManager clientManager = LoginActivity.clientManager;
    private Client myClient;
    private String myClientEmail;
    private ArrayList<Itinerary> bookedItins = new ArrayList<>();

    /**
     * On creation of this Activity, get the booked Itineraries of the given 
     * Client and display them.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_booked_itineraries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        myClientEmail = bundle.getString("email");

        HashMap<String, Client> clients = clientManager.getClients();
        myClient = clients.get(myClientEmail);
        bookedItins = myClient.getBookedItineraries();

        final ListView lvBookedItins = (ListView) findViewById(
			R.id.lvBookedItins);

        final ArrayList<String> itinList = new ArrayList<>();
        for (Itinerary itin : bookedItins) {
            itinList.add(itin.toString());
        }

        adapter = new ItinArrayAdapter(this, android.R.layout.simple_list_item_1, itinList);
        lvBookedItins.setAdapter(adapter);

        lvBookedItins.setOnItemClickListener(
			new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
                }
            });
        }

    /**
     * An Itinerary Adapter for displaying the Itineraries.
     */
    private class ItinArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap= new HashMap<>();

        public ItinArrayAdapter(Context context, int textViewResourceId,
		       	List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); i++) {
                mIdMap.put(objects.get(i), i);
            }
        }

	/**
	 * Returns the item ID at position.
	 *
	 * @param position the position of the item
	 * @return the item's id
	 */
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
