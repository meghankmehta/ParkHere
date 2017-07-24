package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import messages.MerchantAccountMessage;
import model.Address;
import model.User;

public class AddAddressMerchantActivity extends AppCompatActivity {
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText city;
    private EditText state;
    private EditText zip;

    private Address address;
    private User user;
    private MerchantAccountMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_enter_address);

        //    zip = (EditText)findViewById(R.id.editText15);
        addressLine1 = (EditText)findViewById(R.id.addressLine1Merchant);
        addressLine2 = (EditText)findViewById(R.id.addressLine2Merchant);
        city = (EditText)findViewById(R.id.cityMerchant);
        state = (EditText)findViewById(R.id.stateMerchant);
        zip = (EditText)findViewById(R.id.zipMerchant);
//      String nameStr = nameOfSpot.getText().toString();
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra(ParkHere.user);
        message = (MerchantAccountMessage) intent.getSerializableExtra(ParkHere.message);
        addAddListener();
    }

    public void addAddListener() {
        Button addAddress = (Button) findViewById(R.id.addButtonMerchant);

        addAddress.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                address = new Address();
                if(addressLine1 != null){
                    String addrStr1 = addressLine1.getText().toString();
                    address.setFirstLine(addrStr1);
                }
                if(addressLine2 != null){
                    String nameStr = addressLine2.getText().toString();
                    address.setSecondLine(nameStr);
                }
                if(city != null){
                    String cityStr = city.getText().toString();
                    address.setCity(cityStr);
                }
                if(state != null){
                    String stateStr1 = state.getText().toString();
                    address.setState(stateStr1);
                }
                if(zip != null){
                    String zippy = zip.getText().toString();
                    address.setZipCode(zippy);
                }
                Intent intent = new Intent(AddAddressMerchantActivity.this, AddMerchantAccountActivity.class);
                intent.putExtra(ParkHere.address, address);
                intent.putExtra(ParkHere.user, user);
                intent.putExtra(ParkHere.message, message);
                //intent.putExtra(ParkHere.origin, ParkHere.fromAddress);
                startActivity(intent);
            }
        });
    }
}

