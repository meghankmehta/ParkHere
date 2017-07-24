package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import messages.EditPhoneMessage;
import messages.Message;
import messages.ProfilePicMessage;
import model.User;

public class LenderProfilePageActivity extends AppCompatActivity implements TaskDelegate{
    private User user;
    private ImageButton imageButton;
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText phoneTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_profile_page);
        imageButton = (ImageButton) findViewById(R.id.lenderProfilePIc);

        imageButton.setOnClickListener(arg0 -> {

            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        });

        addTransitionToSeekerProfilePage();
        addAccountButton();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(ParkHere.user);

        if (user.getLender().getProfilePicture() != null){
            if (user.getLender().getProfilePicture().length != 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getLender().getProfilePicture(), 0, user.getLender().getProfilePicture().length);
                imageButton.setImageBitmap(bitmap);
            }
        }


        EditText nameTxt =
                (EditText) findViewById(R.id.editDateText);
        nameTxt.setText(user.getFirstName()+" "+user.getLastName());
        phoneTxt =
                (EditText) findViewById(R.id.editText2);
        phoneTxt.setText(user.getLender().getProfile().getPhoneNumber());
        EditText emailTxt =
                (EditText) findViewById(R.id.editText3);
        emailTxt.setText(user.getEmail());

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
            imageButton.setImageBitmap(bitmap);
            byte[] byteArray = PictureGenerator.getProfilePicByteArray(bitmap);

            ProfilePicMessage message = new ProfilePicMessage();
            message.isLender = true;
            message.id = user.getLender().getLenderId();
            message.picture = byteArray;
            new Client(LenderProfilePageActivity.this).execute(message);
        }


    }

    public void updatePhoneNumber(){
        Button updatePhone = (Button) findViewById(R.id.button4);
        updatePhone.setOnClickListener(v -> {
            EditPhoneMessage eMess = new EditPhoneMessage();
            eMess.phone = phoneTxt.getText().toString();
            eMess.id = user.getLender().getLenderId();
            eMess.isLender = true;

            eMess.action = Message.get;
            new Client(this).execute(eMess);
        });
    }

    public void addTransitionToSeekerProfilePage(){
        Button loginButton = (Button) findViewById(R.id.button5);

        loginButton.setOnClickListener(v -> {
            if(user.getSeeker() != null){
                Intent intent;
                intent = new Intent(LenderProfilePageActivity.this, SeekerProfilePageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addAccountButton(){
        Button accountButton = (Button) findViewById(R.id.button3);

        accountButton.setOnClickListener(v -> {

        });
    }

    @Override
    public void taskCompletionResult(Message response) {


    }
}
