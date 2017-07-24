package com.sparkleusc.sparklesparkhere;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messages.GetListingImagesMessage;
import messages.ListingImageMessage;
import messages.Message;
import messages.ProfilePicMessage;
import model.ListingAvailibility;
import model.ListingImage;

/**
 * Created by Meghan on 11/25/16.
 */

public class AddPhotosActivity extends AppCompatActivity implements TaskDelegate{
    private ArrayList<ListingImage> currentImages;
    private ArrayList<ImageButton> buttons;
    private ArrayList<Boolean> hasImage;
    private List<Long> toDelete;
    private static int currentIndex;
    private static int RESULT_LOAD_IMAGE = 1;
    public static List<ListingImage> finalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_add_photo);

        buttons = new ArrayList<>();
        buttons.add((ImageButton)findViewById(R.id.listingImage1));
        buttons.add((ImageButton)findViewById(R.id.listingImage2));
        buttons.add((ImageButton)findViewById(R.id.listingImage3));
        buttons.add((ImageButton)findViewById(R.id.listingImage4));
        buttons.add((ImageButton)findViewById(R.id.listingImage5));
        buttons.add((ImageButton)findViewById(R.id.listingImage6));

        hasImage = new ArrayList<>();
        toDelete = new ArrayList<>();
        currentImages = new ArrayList<>();

        Long listingId = (Long)getIntent().getSerializableExtra(ParkHere.listing);
        if (listingId != null && listingId != 0 && (finalList == null )){

            for (int i = 0; i<6; i++){
                buttons.get(i).setImageResource(R.drawable.parking_car);
            }
            GetListingImagesMessage message = new GetListingImagesMessage();
            message.id = listingId;
            new Client(AddPhotosActivity.this).execute(message);
        }
        else{
            populateImages(finalList);
        }

    }

    private void populateImages(List<ListingImage> givenImages){
        int size = givenImages == null ? 0 : givenImages.size();
        for (int i = 0; i<6; i++) {

            if (i < size) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(givenImages.get(i).getImage(), 0, givenImages.get(i).getImage().length);
                buttons.get(i).setImageBitmap(bitmap);
                currentImages.add(givenImages.get(i));
                hasImage.add(true);
            } else {
                hasImage.add(false);
                System.out.println(i);
                currentImages.add(null);
                buttons.get(i).setImageResource(R.drawable.parking_car);
            }

            buttons.get(i).setOnClickListener(new MyOnClickListener(i));
        }

        doneListener();
    }

    @Override
    public void taskCompletionResult(Message response) {

        if (response instanceof ListingImageMessage){
            done();
        }
        else if (response instanceof GetListingImagesMessage){
            GetListingImagesMessage mess = (GetListingImagesMessage) response;

            Map<Long, ListingImage> imagesMap = mess.images;
            List<ListingImage> images = new ArrayList<>(imagesMap.values());
            if (images == null) {
                images = new ArrayList<>();
            }
            if (finalList != null) {
                images.addAll(finalList);
            }

            populateImages(images);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int index;

        public MyOnClickListener(int index){
            this.index = index;
        }

        @Override
        public void onClick(View v) {

            if (hasImage.get(index)){
                ListingImage listingImage = currentImages.get(index);
                if (listingImage.getListing_image_id() != 0){
                    toDelete.add(listingImage.getListing_image_id());
                }

                hasImage.set(index, false);
                currentImages.set(index, null);
                buttons.get(index).setImageResource(R.drawable.parking_car);
            }
            else{
                currentIndex = index;
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                AddPhotosActivity.this.startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(data.getData(),
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            String picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            Bitmap bitmap = PictureGenerator.getProfilePicBitMap(picturePath);
            buttons.get(currentIndex).setImageBitmap(bitmap);
            byte[] byteArray = PictureGenerator.getProfilePicByteArray(bitmap);
            ListingImage listingImage = new ListingImage();
            listingImage.setImage(byteArray);
            currentImages.set(currentIndex, listingImage);
            hasImage.set(currentIndex, true);
        }

    }

    private void done(){
        Intent intent = new Intent(AddPhotosActivity.this, LenderCreateSpotActivity.class);

        finalList = new ArrayList();
        for (ListingImage listingImage : currentImages){
            if (listingImage != null) finalList.add(listingImage);
        }

        intent.putExtra(ParkHere.origin, ParkHere.fromImages);
        startActivity(intent);
    }

    private void doneListener() {
        Button doneButt = (Button) findViewById(R.id.button8);

        doneButt.setOnClickListener(v -> {
            if (toDelete.isEmpty()){
                done();
            }
            else{
                ListingImageMessage listingImageMessage = new ListingImageMessage();
                listingImageMessage.imagesToDelete = toDelete;
                new Client(AddPhotosActivity.this).execute(listingImageMessage);
            }

        });
    }
}

