package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import messages.EditPhoneMessage;
import messages.Message;
import messages.ProfilePicMessage;
import model.User;

public class SeekerProfilePageActivity extends AppCompatActivity implements TaskDelegate {
    private User user;
    private ImageButton profilePicButton;
    private static int RESULT_LOAD_IMAGE = 1;
    private boolean real = false;
    private EditText phoneTxt;
    private EditText nameTxt;
    private EditText emailTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_profile_page);
        real = true;
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(ParkHere.user);
        profilePicButton = (ImageButton) findViewById(R.id.seekerProfilePIc);
        profilePicButton.setOnClickListener(arg0 -> {

            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        });

        if (user.getSeeker().getProfilePicture() != null){
            if (user.getSeeker().getProfilePicture().length != 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getSeeker().getProfilePicture(), 0, user.getSeeker().getProfilePicture().length);
                profilePicButton.setImageBitmap(bitmap);
            }
        }

        nameTxt = (EditText) findViewById(R.id.editText7);
        nameTxt.setText(user.getFirstName()+" "+user.getLastName());

        phoneTxt = (EditText) findViewById(R.id.editText5);
        phoneTxt.setText(user.getSeeker().getProfile().getPhoneNumber());

        emailTxt = (EditText) findViewById(R.id.emailET);
        emailTxt.setText(user.getEmail());

        addTransitionToSeekerProfilePage();
        addAccountButton();
        updatePhoneNumber();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("in pic response", "pic response");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Log.d("got an image", "got image");
            // Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(data.getData(),
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            // int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            Bitmap bitmap = PictureGenerator.getProfilePicBitMap(picturePath);
            profilePicButton.setImageBitmap(bitmap);
            byte[] byteArray = PictureGenerator.getProfilePicByteArray(bitmap);

            ProfilePicMessage message = new ProfilePicMessage();
            message.isLender = false;
            message.id = user.getSeeker().getSeekerId();
            message.picture = byteArray;
            user.getSeeker().setProfilePicture(byteArray);
            new Client(SeekerProfilePageActivity.this).execute(message);
        }


    }

    public void updatePhoneNumber(){
        Button updatePhone = (Button) findViewById(R.id.button6);
        updatePhone.setOnClickListener(v -> {

                EditPhoneMessage eMess = new EditPhoneMessage();
                eMess.phone = phoneTxt.getText().toString();
                eMess.id = user.getSeeker().getSeekerId();
                eMess.isLender = false;

                eMess.action = Message.get;
                new Client(this).execute(eMess);



        });
    }
    public void addTransitionToSeekerProfilePage(){
        Button loginButton = (Button) findViewById(R.id.button7);

        loginButton.setOnClickListener(v -> {
            if(user.getLender() != null){
                Intent intent;
                intent = new Intent(SeekerProfilePageActivity.this, LenderProfilePageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addAccountButton(){
        Button loginButton = (Button) findViewById(R.id.button2);
        loginButton.setOnClickListener(v -> {

        });
    }



    @Override
    public void taskCompletionResult(Message response) {

    }
}
