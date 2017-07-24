package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import messages.Message;
import messages.ViewLenderMessage;
import model.ListingResult;
import model.Review;
import model.User;

public class SeekerViewLenderProfPage extends AppCompatActivity implements TaskDelegate{
    private User user;
    private TextView nameTxt;
    private List<Review> reviews;
    private double averageRating;
    private String fullName;
    private ListingResult result;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_view_lender_prof_page);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(ParkHere.user);
        result = (ListingResult)intent.getSerializableExtra(ParkHere.result);
        nameTxt = (TextView) findViewById(R.id.nameTV);
        profilePicture = (ImageView) findViewById(R.id.SLenderProfPage);

        ViewLenderMessage message = new ViewLenderMessage();
        message.lenderId = result.listing.getLenderId();
        new Client(SeekerViewLenderProfPage.this).execute(message);
    }


    @Override
    public void taskCompletionResult(Message response) {

        ViewLenderMessage mess = (ViewLenderMessage) response;
        fullName = mess.name;
        averageRating = mess.averageRating;
        reviews = mess.allReviews;

        if (mess.profilePicture != null ){
            Bitmap bitmap = BitmapFactory.decodeByteArray(mess.profilePicture, 0, mess.profilePicture.length);
            profilePicture.setImageBitmap(bitmap);
        }

        nameTxt.setText(fullName);
        ListView lv = (ListView) findViewById(R.id.listview);
        List<String> al = new ArrayList<String>();

        for (int i = 0; i < reviews.size(); i++){
            String temp = reviews.get(i).getFirstName() + " - " + reviews.get(i).getRating() + " - "
                    + reviews.get(i).getComment();
            al.add(temp);
        }
        if (al.size() == 0){
            al.add("No reviews");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, al );

        lv.setAdapter(arrayAdapter);

    }
}
