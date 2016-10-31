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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DisplayItinerariesActivity extends AppCompatActivity 
	implements View.OnClickListener {

    // ArrayList to store most recently searched for Itineraries
    private ArrayList<Itinerary> searchedItins;
    private Button bBack;
    private boolean isAdmin;

    /**
     * On creation of this Activity, get the Itineraries to display as a String
     * and use an Adapter to display the Itineraries as a list of boxes.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_itins);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        isAdmin = bundle.getBoolean("isAdmin");

        final ListView lvItins = (ListView) findViewById(R.id.lvItins);

        String[] searchedItins = bundle.getString("searchedItins").split("~");
        final ArrayList<String> itinList = new ArrayList<>();
        for (int i=0; i < searchedItins.length; i++) {
            itinList.add(searchedItins[i]);
        }

        final ItinArrayAdapter adapter = new ItinArrayAdapter(this,
                android.R.layout.simple_list_item_1, itinList);
        lvItins.setAdapter(adapter);
        final Intent BookItinerary = new Intent(this, 
			BookItineraryActivity.class);
        //need to pass in the itinerary that is getting booked somehow

        // When an Itinerary is selected, take the User to the Booking Activity
        // with the selected Itinerary as the one being booked
        lvItins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, 
		    int position, long id) {
                String itinString = String.valueOf(parent.getItemAtPosition(
				position));
                BookItinerary.putExtra("itinerary", itinString);
                if (isAdmin){
                    BookItinerary.putExtra("isAdmin", true);
                }
                startActivity(BookItinerary);
                finish();
            }
        });
    }

    /**
     * A subclass of ArrayAdapter used to display Itineraries in a list.
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
	 * Returns the ID of the item at position.
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

    /**
     * Listens for the back button being clicked, go back when it is.
     */
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.bBack:
                finish();
                break;
        }
    }

    /**
     * Sorts Itineraries searchedItins according to time, from least to
     * greatest.
     *
     * @return the String representation of this Itinerary sorted by time
     */
    public String sortByTime() {
        // itineraries already generated and stored in searchedItins variable
        Collections.sort(searchedItins, Itinerary.timeComparator);
        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString();
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
            itineraryStr += itineraries.toString();
        }
        if (itineraryStr.equals("")) {
            itineraryStr += "No matching itineraries found.";
        }
        return itineraryStr;
    }
}
