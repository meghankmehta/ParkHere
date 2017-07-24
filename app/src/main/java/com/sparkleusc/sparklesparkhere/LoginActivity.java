package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import messages.Message;
import messages.UserMessage;
import model.Seeker;
import model.User;

public class LoginActivity extends AppCompatActivity implements TaskDelegate {

    private EditText emailET;
    private String email = null;
    private EditText passwordET;
    private String password = null;
    private LoginActivity thisActivity;
    private ProgressBar pb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_login);
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        addSignUpListener();
        addLoginListener();
    }

    public void taskCompletionResult(Message response) {
        UserMessage um = (UserMessage) response;
        try {
            if (um.action.equals(Message.check_validity)){
                if (um.success){
                    //user.setPassword(password)
                    UserMessage mes = new UserMessage();
                    mes.user = um.user;
                    mes.action = Message.get;
                    new Client(thisActivity).execute(mes);
                }
                else {
                    pb.setVisibility(View.INVISIBLE);
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid username/password";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
            else{
                User user = um.user;
                Intent intent;
                pb.setVisibility(View.INVISIBLE);
                if (user.getCurrent_role() instanceof Seeker){
                    intent = new Intent(LoginActivity.this, MapsActivity.class);
                    intent.putExtra(ParkHere.user, user);
                    startActivity(intent);
                }
                else{
                    intent = new Intent(LoginActivity.this, LenderListingsPageActivity.class);
                    intent.putExtra(ParkHere.user, user);
                    startActivity(intent);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSignUpListener(){
        Button signUpButton = (Button) findViewById(R.id.createAccountButton);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public void addLoginListener(){
        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(v -> {
            pb.setVisibility(View.VISIBLE);
            Boolean emailBool = false;
            emailET = (EditText) findViewById(R.id.email);
            email = emailET.getText().toString();
            if (email.length()>0){
                emailBool = true;
            }

            Boolean passwordBool = false;
            passwordET = (EditText)  findViewById(R.id.password);
            password = passwordET.getText().toString();
            if (password.length() > 0){
                passwordBool = true;
            }

            if (emailBool && passwordBool){
                User user = new User();
                user.setEmail(email);

                user.setPassword(password);
                UserMessage mes = new UserMessage();
                mes.user = user;
                mes.action = Message.check_validity;
                new Client(thisActivity).execute(mes);
            } else {
                pb.setVisibility(View.INVISIBLE);
                Context context = getApplicationContext();
                CharSequence text = "1 or more fields are missing inputs";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}

