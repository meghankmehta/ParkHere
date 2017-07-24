package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import messages.LenderMessage;
import messages.Message;
import messages.SeekerMessage;
import messages.UserMessage;
import model.Lender;
import model.Profile;
import model.Seeker;
import model.User;

public class SignUpActivity extends AppCompatActivity implements TaskDelegate {
    private EditText firstNameTV;
    private String firstName = null;
    private EditText lastNameTV;
    private String lastName = null;
    private EditText phoneNumberTV;
    private String phoneNumber = null;
    private EditText emailTV;
    private String email = null;
    private EditText passwordTV;
    private String password = null;
    private User user;
    private RadioButton seekerRadio = null;
    private RadioButton lenderRadio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        seekerRadio = (RadioButton)findViewById(R.id.rbSeeker);
        lenderRadio = (RadioButton) findViewById(R.id.rbLender);

        addCreateProfileListener();
    }

    public static boolean validatePassword(String s)
    {
        Pattern p = Pattern.compile("(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?[^\\w]).{10,}");
        Matcher m = p.matcher(s);
        return m.find();
    }

    public void addCreateProfileListener(){
        Button createProfileButton = (Button) findViewById(R.id.CreateProfileButton);
        createProfileButton.setOnClickListener(v -> {

            Boolean firstNameBool = false;
            firstNameTV = (EditText)  findViewById(R.id.FirstNameET);
            firstName = firstNameTV.getText().toString();
            if (firstName.length() >0){
                firstNameBool = true;
            }
            Boolean lastNameBool = false;
            lastNameTV = (EditText)  findViewById(R.id.LastNameET);
            lastName = lastNameTV.getText().toString();
            if (lastName.length() > 0){
                lastNameBool = true;
            }
            Boolean phoneNumberBool = false;
            phoneNumberTV = (EditText)  findViewById(R.id.PhoneNumberET);
            phoneNumber = phoneNumberTV.getText().toString();
            if (phoneNumber.length() > 0){
                phoneNumberBool = true;
            }
            Boolean emailBool = false;
            emailTV = (EditText)  findViewById(R.id.EmailAddressET);
            email = emailTV.getText().toString();
            if (email.length() > 0){
                emailBool = true;
            }
            Boolean passwordBool = false;

            passwordTV = (EditText)  findViewById(R.id.passwordET);
            password   = passwordTV.getText().toString();

            if(validatePassword(password))
            {
                passwordBool = true;
            } else
            {
                Context context = getApplicationContext();
                CharSequence text = "PW requires 1 cap. letter, 1 spec. char., and 10 char. length";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (firstNameBool && lastNameBool && phoneNumberBool  && emailBool && passwordBool)
            {
                UserMessage mes = new UserMessage();
                User u = new User();
                u.setEmail(email);
                u.setPassword(password);
                u.setFirstName(firstName);
                u.setLastName(lastName);
                mes.user = u;
                mes.action = Message.insert;
                new Client(SignUpActivity.this).execute(mes);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "1 or more fields are missing inputs";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    public void taskCompletionResult(Message response) {
        if(response instanceof UserMessage) {
            UserMessage um = (UserMessage) response;
            long userID = um.user.getUser_id();
            user = um.user;
            Profile profile = new Profile();
            profile.setPhoneNumber(phoneNumber);

            if(seekerRadio.isChecked()){
                Seeker seeker = new Seeker();
                seeker.setProfile(profile);
                seeker.setUser_id(userID);
                SeekerMessage seekerMessage = new SeekerMessage();
                seekerMessage.action = Message.insert;
                seekerMessage.seeker = seeker;
                new Client(this).execute(seekerMessage);
            }

            if(lenderRadio.isChecked()){
                Lender lender = new Lender();
                lender.setProfile(profile);
                lender.setUser_id(userID);
                LenderMessage lenderMsg = new LenderMessage();
                lenderMsg.lender = lender;
                lenderMsg.action = Message.insert;
                new Client(this).execute(lenderMsg);
            }

        }
        else if (response instanceof SeekerMessage){
            SeekerMessage seekerMessage = (SeekerMessage) response;
            Seeker seeker = seekerMessage.seeker;
            user.setSeeker(seeker);
            user.setCurrent_role(seeker);

            Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        } else {
            LenderMessage lenderMessage = (LenderMessage) response;
            Lender lender = lenderMessage.lender;
            user.setLender(lender);
            user.setCurrent_role(lender);

            Intent intent = new Intent(SignUpActivity.this, AddMerchantAccountActivity.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        }
    }
}
