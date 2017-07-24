package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import model.Listing;
import model.Review;
import model.User;

/**
 * Created by Meghan on 11/27/16.
 */

public class LenderReviewPage extends AppCompatActivity {
    //    private ArrayList<CustomArrayList> rrAry;
//    private static User lend;
    User user;
    Listing listing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_review);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(ParkHere.user);
        listing = (Listing) intent.getSerializableExtra(ParkHere.listing);
        populateReviewAndRatings();

    }

    public void populateReviewAndRatings(){
        RatingBar rBar = (RatingBar) findViewById(R.id.ratingBar2);
        TextView tView = (TextView) findViewById(R.id.textView32);
        if (listing.getAverageRating() != 0){
            rBar.setRating((float)listing.getAverageRating());
           // rBar.setNumStars((int)listing.getAverageRating());
        }
        //rBar.setRating((float)5);
        rBar.setClickable(false);
     //   rBar.setActivated(false);
        rBar.setEnabled(false);
        //RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) rBar.getProgressDrawable();
        stars.setColorFilter(Color.parseColor("#FFCC00"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        String reviews = null;
        List<Review> listString = listing.getReviews();
        if (listing.getReviews().isEmpty()){
            tView.setText("This parking spot has not yet been reviewed");
        }
        else{
            for(Review r : listing.getReviews()){
                reviews += r.getFirstName()+": "+r.getRating()+" stars: "+r.getComment();
               reviews += System.getProperty("line.separator");

            }

            tView.setText(reviews);
        }

    }
}
//        Intent intent = getIntent();
//        lend = (User) intent.getSerializableExtra(user);
//
//        rrAry = new ArrayList<CustomArrayList>();
//        //populate rr array
//        if(lend.getLender().getListings() != null){
//            for(Listing l : lend.getLender().getListings().values()){
//                int tempCast = (int)l.getAverageRating();
//                l.getReviews();
//                CustomArrayList c = new CustomArrayList(tempCast, l.getReviews().toString());
//                if(c != null){
//                    rrAry.add(c);
//                }
//
//            }
//
//        }
//
//        ArrayAdapter<CustomArrayList> ratRevAry = new ArrayAdapter<CustomArrayList>(this, 0, rrAry);
//
//        ListView lv = (ListView) findViewById(R.id.customList);
//        lv.setAdapter(ratRevAry);
//
//
//    }
//
//    public void putShitInAry(){
//
//    }


//}
