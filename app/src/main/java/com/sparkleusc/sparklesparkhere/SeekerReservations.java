package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import model.Address;
import model.Listing;
import model.Reservation;
import model.User;

public class SeekerReservations extends AppCompatActivity {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<Reservation> listings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Debug 1");
        super.onCreate(savedInstanceState);
        System.out.println("Debug 2");
        setContentView(R.layout.activity_seeker_reservations);
        System.out.println("Debug 3");
        Intent intent = getIntent();
        System.out.println("Debug 4");
        User user = (User) intent.getSerializableExtra(ParkHere.user);
        System.out.println("Debug 5");
        ListView listView = (ListView) findViewById(R.id.rez);
        System.out.println("Debug 6");
        Collection<Reservation> listingsCollection = user.getSeeker().getReservations().values();
        System.out.println("Debug 7");
        String[] values = new String[listingsCollection.size()];
        System.out.println("Debug 8");
        Iterator<Reservation> it = listingsCollection.iterator();
        System.out.println("Debug 9");
        int index = 0;
        System.out.println("Debug 10");
        while (it.hasNext()){
            Reservation res = it.next();
            System.out.println("Debug 10.1");
            if(res == null){
                System.out.println("Res iz null");
            } else {
                if (res.getListing() == null) {
                    System.out.println("listing is null");
                } else {
                    Listing listing = res.getListing();
                    Address address = listing.getAddress();
                    StringBuilder sb = new StringBuilder();
                    sb.append(address.getFirstLine()).append("\n");
                    String secondLine = address.getSecondLine();
                    if(secondLine != null && !secondLine.trim().equals(""))
                        sb.append(secondLine).append("\n");
                    sb.append(address.getCity()).append(", ").append(address.getState());
                    sb.append(" ").append(address.getZipCode());
                    values[index] = listing.getTitle() + (listing.getDeleted() ? " (THIS LISTING HAS BEEN DELETED) " : "") + "\n" + sb.toString();
                    listings.add(res);
                    index++;
                }
            }
        }
        System.out.println("Debug 11");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Listing toEdit = listings.get(position);
//                Intent intent = new Intent(LenderListingsPageActivity.this, LenderCreateSpotActivity.class);
//
//                //intent.putExtra(ParkHere.listingPackage, listing)
//                intent.putExtra(ParkHere.user, user);
//                intent.putExtra(ParkHere.updateListing, toEdit);
//                intent.putExtra(ParkHere.origin, ParkHere.fromLeft);
//                startActivity(intent);
//            }
//        });
    }

}



