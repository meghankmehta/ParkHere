package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class LaunchPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_page);
        new ParkHere();
        addLoginListener();
        addSignUpListener();
    }

    public void addLoginListener(){
        Button loginButton = (Button) findViewById(R.id.LaunchLoginButton);
        loginButton.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LaunchPageActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public void addSignUpListener(){
        Button signUpButton = (Button) findViewById(R.id.LaunchSignupButton);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LaunchPageActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}


