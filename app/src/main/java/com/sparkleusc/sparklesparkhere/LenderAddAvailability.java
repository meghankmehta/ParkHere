package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Listing;
import model.ListingAvailibility;

public class LenderAddAvailability extends AppCompatActivity {
    private static ListingAvailibility la;

    static HashMap<Long, ListingAvailibility> allListings;
    static List<ListingAvailibility> newList;
    static List<Long> toDelete;
    private ListAdapter adapter;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_add_availability);

        if (newList == null) {
            newList = new ArrayList<>();
        }
        if (toDelete == null){
            toDelete = new ArrayList<>();
        }

        Intent intent = getIntent();
        if (intent.getSerializableExtra(ParkHere.origin).equals(ParkHere.fromLeft)){
            la = null;
            if (allListings == null) {
                allListings=(HashMap<Long, ListingAvailibility>) intent.getSerializableExtra(ParkHere.availabilities);
                if (allListings != null) {
                    for (ListingAvailibility listing : allListings.values()){
                        if (!listing.getDeleted()){
                            newList.add(listing);
                        }
                    }
                }
            }
        }
        else {
            la = (ListingAvailibility) intent.getSerializableExtra(ParkHere.availability);

            if (la != null){
                if (allListings == null){
                    allListings = new HashMap<>();
                }
                if(intent.getSerializableExtra(ParkHere.origin).equals(ParkHere.fromRight)){
                    la.setDeleted(false);
                    newList.add(la);
                }

            }
        }

        if (newList != null){
            retrieveTimeDate();
        }
        addLenderAddAvailabilityListener();
        returnToCreateSpotPage();
    }

    public void addLenderAddAvailabilityListener(){
        Button addAvailability = (Button) findViewById(R.id.addAvailabilityButton);

        addAvailability.setOnClickListener(v -> {
            UserTimeAndDate.endDay = null;
            UserTimeAndDate.startDay = null;
            UserTimeAndDate.endHour = null;
            UserTimeAndDate.startHour = null;
            UserTimeAndDate.startMonth= null;
            UserTimeAndDate.endMonth = null;
            UserTimeAndDate.endYear = null;
            UserTimeAndDate.startYear = null;
            UserTimeAndDate.endMinute = null;
            UserTimeAndDate.startMinute = null;
            Intent intent = new Intent(LenderAddAvailability.this, LenderAddAvailabilityTimeDate.class);
            startActivity(intent);
        });
    }
    public void returnToCreateSpotPage(){
        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LenderAddAvailability.this, LenderCreateSpotActivity.class);
            intent.putExtra(ParkHere.origin, ParkHere.fromRight);
            startActivity(intent);
        });
    }

    public void retrieveTimeDate(){
        ListView list = (ListView) findViewById(R.id.list);
        if (newList != null){
            System.out.println("newlist size: "+newList.size());

            adapter = new ListAdapter(this);
            // Assign adapter to ListView
            list.setAdapter(adapter);
        }

        list.setOnItemClickListener((parent, view, position, id) -> {
            removeAvailability(position);
        });

    }

    private void removeAvailability(int index){

        ListingAvailibility currentAvail = newList.get(index);
        if (currentAvail.getAvailabilityId() != 0){
            toDelete.add(currentAvail.getAvailabilityId());
        }
        newList.remove(index);
        adapter.notifyDataSetChanged();
        Context context = getApplicationContext();
        CharSequence text = "The listing has been removed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public class ListAdapter extends ArrayAdapter<ListingAvailibility> {

        public ListAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }


        @Override
        public int getCount() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public ListingAvailibility getItem(int position) {
            return (newList == null || position >= newList.size()) ? null : newList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(android.R.layout.simple_list_item_1, null);
            }

            ListingAvailibility p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(android.R.id.text1);
                String listingString = p.getBeginDateTime().toString() + " --- " +
                        p.getEndDateTime().toString();
                tt1.setText(listingString);
            }

            return v;
        }

    }

}
