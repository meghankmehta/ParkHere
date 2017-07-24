package com.sparkleusc.sparklesparkhere;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import messages.Message;
import messages.ReservationMessage;
import messages.SearchMessage;
import messages.SeekerFavoriteMessage;
import model.ListingResult;
import model.Reservation;
import model.Review;
import model.User;

public class MakeReservationActivity extends AppCompatActivity implements TaskDelegate {

    private ListingResult result;
    private SearchMessage search;
    private User user;
    private String clientToken;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        Intent i = getIntent();
        result = (ListingResult) i.getSerializableExtra(ParkHere.result);
        search = (SearchMessage) i.getSerializableExtra(ParkHere.search);
        user = (User) i.getSerializableExtra(ParkHere.user);
        clientToken = (String) i.getSerializableExtra(ParkHere.clientToken);
        Button reservationButton = (Button) findViewById(R.id.makeReservationButton);
        reservationButton.setVisibility(View.GONE);
        TextView titleParkingSpotTV =(TextView) findViewById(R.id.titleParkingSpotTV);
        TextView priceTV = (TextView) findViewById(R.id.priceTV);
        TextView startTimeTV = (TextView) findViewById(R.id.startTimeTV);
        TextView endTimeTV = (TextView) findViewById(R.id.endTimeTV);
        TextView ratingTV = (TextView) findViewById(R.id.ratingTV);
        TextView distanceTV = (TextView) findViewById(R.id.distanceTV);

        titleParkingSpotTV.setText(result.listing.getTitle());
        priceTV.setText("" + result.listing.getPricePerHr());
        startTimeTV.setText(search.advanced.getStartTime().toString());
        endTimeTV.setText(search.advanced.getEndTime().toString());
        ratingTV.setText("" + result.listing.getAverageRating());
        distanceTV.setText("" + round(result.distance, 2));

       // addReservationListener();
        addFavoriteListener();
        commentsListener();
        addSpotSpecificComments();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void commentsListener(){
        Button lenderProfPageButton = (Button) findViewById(R.id.lenderProfPageButton);
        lenderProfPageButton.setOnClickListener((v -> {
            Intent intent = new Intent(MakeReservationActivity.this, SeekerViewLenderProfPage.class);
            intent.putExtra(ParkHere.user, user);
            intent.putExtra(ParkHere.result, result);
            startActivity(intent);
        }));
    }

    public void addSpotSpecificComments(){
        ListView listView = (ListView) findViewById(R.id.spotSpecificComments);
        List<Review> listingReviews = result.listing.getReviews();

        String[] values;
        if (listingReviews.size() == 0){
            values = new String[1];
        }
        else{
            values = new String[listingReviews.size()];
        }

        for (int i = 0; i < listingReviews.size(); i++){
            values[i] = listingReviews.get(i).getComment();
        }
        if (listingReviews.size() == 0){
            values[0] = "No Reviews";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

    public void onBraintreeSubmit(View v) {
        Log.d("in brain tree", "in correct brain tree submit");
        PaymentRequest paymentRequest = new PaymentRequest()
                .clientToken(clientToken);
        startActivityForResult(paymentRequest.getIntent(this), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  if (requestCode == REQUEST_CODE) {
        if (resultCode == Activity.RESULT_OK) {
            Log.d("got nonce", "got client nonce");
            PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
                    BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
            );
            String nonce = paymentMethodNonce.getNonce();
            Log.d("in correct result", "in correct result send reservation");
            ReservationMessage rm = new ReservationMessage();
            Reservation reservation = new Reservation();
            reservation.setLenderId(result.listing.getLenderId());
            reservation.setLender(result.listing.getLender());
            //Log.d("listing merchant id", result.listing.getLender().toString());
            reservation.setListing(result.listing);
            reservation.setSeekerId(user.getSeeker().getSeekerId());
            reservation.setListingId(result.listing.getListingId());
            reservation.setBeginDate(search.advanced.getStartTime());
            reservation.setEndDate(search.advanced.getEndTime());
            rm.reservation = reservation;
            rm.nonce = nonce;
            rm.action = Message.insert;
            new Client(MakeReservationActivity.this).execute(rm);

            // Send the nonce to your server.
        }
        //  }
    }

//    public void addReservationListener(){
//        Button reservationButton = (Button) findViewById(R.id.makeReservationButton);
//        reservationButton.setOnClickListener(v -> {
//            new Client(MakeReservationActivity.this).execute(new GetClientTokenMessage());
//        });
//    }

    public void addFavoriteListener(){
        Button favoriteButton = (Button) findViewById(R.id.addToFavoritesButton);
        favoriteButton.setOnClickListener(v -> {
            SeekerFavoriteMessage sfm = new SeekerFavoriteMessage();
            sfm.seekerId = user.getSeeker().getSeekerId();
            sfm.listingId = result.listing.getListingId();
            user.getSeeker().getFavorites().put(result.listing.getListingId(), result.listing);
            sfm.action = Message.insert;
            Log.d("Ids:", "" + sfm.seekerId + " " + sfm.listingId);
            new Client(MakeReservationActivity.this).execute(sfm);
        });
    }

    @Override
    public void taskCompletionResult(Message response) {
        if (response instanceof SeekerFavoriteMessage){

            Context context = getApplicationContext();
            CharSequence text = "Listing Favorited";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            ReservationMessage reservationMessage = (ReservationMessage) response;
            if (reservationMessage.success){
                Log.d("transaction id ", reservationMessage.reservation.getBTTransactionId());
                user.getSeeker().getReservations().put(reservationMessage.reservation.getReservationId(), reservationMessage.reservation);
                Intent intent = new Intent(MakeReservationActivity.this, MapsActivity.class);
                intent.putExtra(ParkHere.user, user);
                startActivity(intent);
            }
            else{
                Context context = getApplicationContext();
                CharSequence text = "Something went wrong!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }
    }
}