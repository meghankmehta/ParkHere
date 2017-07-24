package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import model.Listing;
import model.User;

public class LenderListingsPageActivity extends AppCompatActivity {

    private User user;
    private List<Listing> listings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_listings_page);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra(ParkHere.user);
        AddPhotosActivity.finalList = null;

        ListView listView = (ListView) findViewById(R.id.lendings);
        Collection<Listing> listingsCollection  =  user.getLender().getListings().values();
        if (listingsCollection != null){
            for (Listing list : listingsCollection){
                if (list.getDeleted() == null || !list.getDeleted()) listings.add(list);
            }
            String[] values = new String[listings.size()];
            Iterator<Listing> it = listings.iterator();
            Log.d("test delete", "before adding values");
            int index = 0;
            while (it.hasNext()){
                Listing listing = it.next();
                //  if (!listing.getDeleted())
                //{
                values[index] = listing.getTitle();
                // listings.add(listing);
                index++;
                //}
            }
            Log.d("test delete", "after adding values");
            if (!listings.isEmpty()){
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);
                Log.d("test delete", "after creating adapter");
                // Assign adapter to ListView
                listView.setAdapter(adapter);
            }
        }


        Log.d("test delete", "after adding adapter");
      //   ListView Item Click Listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Listing toEdit = listings.get(position);
            Intent intent1 = new Intent(LenderListingsPageActivity.this, LenderCreateSpotActivity.class);

            //intent.putExtra(ParkHere.listingPackage, listing)
            intent1.putExtra(ParkHere.user, user);
            intent1.putExtra(ParkHere.updateListing, toEdit);
            intent1.putExtra(ParkHere.origin, ParkHere.fromLeft);
            startActivity(intent1);
        });


        addProfileListener();
        addNewListener();
    }

    public void addProfileListener(){
        Button tempButton = (Button) findViewById(R.id.button9);
        tempButton.setOnClickListener(v -> {
            Intent intent = new Intent(LenderListingsPageActivity.this, LenderProfilePageActivity.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    public void addNewListener(){
        Button newButton = (Button) findViewById(R.id.button13);
        newButton.setOnClickListener(v -> {
            Intent intent = new Intent(LenderListingsPageActivity.this, LenderCreateSpotActivity.class);
            intent.putExtra(ParkHere.user, user);
            intent.putExtra(ParkHere.origin, ParkHere.fromLeft);
            startActivity(intent);
        });
    }
}