package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import messages.GetClientTokenMessage;
import messages.Message;
import messages.SearchMessage;
import model.Address;
import model.AdvancedSearch;
import model.Listing;
import model.ListingResult;
import model.Reservation;
import model.User;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskDelegate,
        GoogleMap.OnMarkerClickListener {

    private User user;
    private PopupWindow pWindow;
    private GoogleApiClient client2;
    private Collection<Reservation> reservationCollection;
    private List<Listing> pastReservations = new ArrayList<>();
    private ListingResult[] allResults;
    private SearchMessage sm;
    private int avgPrice = 0;
    private Timestamp ts = null;

    private int currentNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();


        user = (User) intent.getSerializableExtra(ParkHere.user);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        TextView averagePriceTV = (TextView)findViewById(R.id.avgPriceTV);
        averagePriceTV.setText("");


        addAdvancedListener();
        addProfileListener();
        addFavoritesListener();
        addReservationsListener();
        popupListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    public void popupListener(){

        pWindow = new PopupWindow();

        reservationCollection = user.getSeeker().getReservations().values();
        String[] values = new String[reservationCollection.size()];
        Iterator<Reservation> it = reservationCollection.iterator();
        int index = 0;

        if(reservationCollection != null){
            while (it.hasNext()){
                Reservation r = it.next();

                long currTime = System.currentTimeMillis();
                ts = new Timestamp(currTime);
//                ts.setTime(currTime);

                if(r.getBeginDate() != null && r.getEndDate() != null){
                    if(r.getEndDate().before(ts) && !r.isReviewed()){
                        r.setIsReviewed(true);

                        Intent intent;
                        intent = new Intent(MapsActivity.this, RatingPopupActivity.class);
                        intent.putExtra(ParkHere.user, user);
                        intent.putExtra(ParkHere.reservation, r);
                        //intent.putExtra(ParkHere.listing, r.getListing());
                        startActivity(intent);
                    }
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        LatLng la = new LatLng(34.0522, -118.2437);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(la, 8.0f));
        sm = (SearchMessage) getIntent().getSerializableExtra("results");
        if(sm != null) {
            AdvancedSearch advanced = sm.advanced;
            LatLng latLng = new LatLng(advanced.getLat(), advanced.getLon());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Destination")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
            Map<Long, ListingResult> results = sm.returnListings;
            int i = 0;
            allResults = new ListingResult[results.keySet().size()];
            for(Long l : results.keySet()) {
                ListingResult r = results.get(l);
                allResults[i] = r;
                Address result = r.listing.getAddress();
                avgPrice += r.listing.getPricePerHr();
                i++;
                mMap.addMarker(new MarkerOptions().position(new LatLng(result.getLatitude(),
                        result.getLongitude())).title(i + ": " + r.listing.getTitle())
                        .icon(BitmapDescriptorFactory.defaultMarker(Math.max(0, 40 -
                                r.listing.getReviews().size()))));
            }
            if (results.size() != 0){
                avgPrice = avgPrice/results.size();
            }
            TextView averagePriceTV = (TextView)findViewById(R.id.avgPriceTV);
            averagePriceTV.setText("Avg Price: " + avgPrice);
            mMap.setOnMarkerClickListener(this);

        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        if(!marker.getTitle().equals("Destination")) {
            currentNum = Integer.parseInt(marker.getTitle().split(":")[0]);
            new Client(MapsActivity.this).execute(new GetClientTokenMessage());
        }
        return false;
    }

    public void addAdvancedListener() {
        Button tempButton = (Button) findViewById(R.id.button1);
        tempButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, AdvancedSettingsActivity.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    public void addProfileListener() {
        Button tempButton = (Button) findViewById(R.id.button11);
        tempButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, SeekerProfilePageActivity.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    public void addFavoritesListener() {
        Button favoritesButton = (Button) findViewById(R.id.dropinButton);
        favoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, SeekerFavorites.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    public void addReservationsListener() {
        Button favoritesButton = (Button) findViewById(R.id.button10);
        favoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, SeekerReservations.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    @Override
    public void taskCompletionResult(Message response) {
        Intent intent = new Intent(MapsActivity.this, MakeReservationActivity.class);
        intent.putExtra(ParkHere.user, user);
        intent.putExtra(ParkHere.result, allResults[currentNum - 1]);
        intent.putExtra(ParkHere.search, sm);
        intent.putExtra(ParkHere.clientToken, ((GetClientTokenMessage)response).clientToken);
        startActivity(intent);
    }
}
