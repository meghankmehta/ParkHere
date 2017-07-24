package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import messages.Message;
import messages.SeekerFavoriteMessage;

import model.User;
import model.Listing;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class SeekerFavorites extends AppCompatActivity implements TaskDelegate{
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private Map<Long, Listing> listingsMap;
    private List<Listing> listings = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private User user;
    private ArrayList<String> values;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_favorites_list);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(ParkHere.user);

        ListView listView = (ListView) findViewById(R.id.favorites);
        listingsMap = user.getSeeker().getFavorites();
        values = new ArrayList<>();
        getValues();

        Button backButton = (Button)findViewById(R.id.seekerFavoritesBack);
        backButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(SeekerFavorites.this, MapsActivity.class);
            intent1.putExtra(ParkHere.user, user);
            startActivity(intent1);
        });

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            currentPos = position;
            SeekerFavoriteMessage message = new SeekerFavoriteMessage();
            message.action = Message.delete;
            message.seekerId = user.getSeeker().getSeekerId();
            message.listingId = listings.get(position).getListingId();
            user.getSeeker().getFavorites().remove(message.listingId);
            listingsMap.remove(message.listingId);
            new Client(SeekerFavorites.this).execute(message);
        });
    }

    private void getValues(){
       // values = new String[listingsMap.size()];

        for (Listing listing : listingsMap.values()){
            values.add(listing.getTitle() + (listing.getDeleted() ? " (THIS LISTING HAS BEEN DELETED) " : ""));
            listings.add(listing);
        }
    }

    @Override
    public void taskCompletionResult(Message response) {
        String item = adapter.getItem(currentPos);
       // values.remove(item);
        adapter.remove(item);

        adapter.notifyDataSetChanged();
        Context context = getApplicationContext();
        CharSequence text = "The favorited listing has been removed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
