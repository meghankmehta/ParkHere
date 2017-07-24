package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;

import java.util.Collection;
import java.util.Iterator;

import messages.ListingReviewMessage;
import messages.Message;
import messages.UserMessage;
import model.Listing;
import model.Reservation;
import model.User;

/**
 * Created by Meghan on 11/17/16.
 */

public class RatingPopupActivity extends Activity implements TaskDelegate {
    private User user;
    private Reservation curReservation;
    private RatingBar rBar;
    private EditText review;
    private TextView descriptionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Inner debug 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_rate_spot);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        rBar = (RatingBar) findViewById(R.id.seekerRatingBar);
        review = (EditText) findViewById(R.id.editText17);

        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra(ParkHere.user);
        curReservation  = (Reservation) intent.getSerializableExtra(ParkHere.reservation);

//        descriptionText = (TextView) findViewById(R.id.textView27);
//        descriptionText.setText(curReservation.getTitle());


        addSubmitButton();
    }

    public void addSubmitButton(){

        Button submitButton = (Button) findViewById(R.id.button17);
        submitButton.setOnClickListener(v -> {


            ListingReviewMessage mess = new ListingReviewMessage();

            mess.rate = rBar.getNumStars();
            mess.reservation = curReservation;
            mess.comment = review.getText().toString();
            mess.userName = user.getFirstName();
            mess.action = Message.get;
            new Client(this).execute(mess);

         //   curListing.

            //add to
//            curListing.
//            review.toString();



            finish();

        });
    }


    @Override
    public void taskCompletionResult(Message response) {

    }
}
