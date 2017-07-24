package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import model.Address;

public class AddAddressActivity extends AppCompatActivity {
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText city;
    private EditText state;
    private EditText zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        addressLine1 = (EditText)findViewById(R.id.editText9);
        addressLine2 = (EditText)findViewById(R.id.editText11);
        city = (EditText)findViewById(R.id.editText13);
        state = (EditText)findViewById(R.id.editText14);
        zip = (EditText)findViewById(R.id.editText15);

        Intent intent = getIntent();
        Address address = (Address)intent.getSerializableExtra(ParkHere.address);
        if(address != null) {
            addressLine1.setText(address.getFirstLine());
            if (address.getSecondLine() != null)
            {
                addressLine2.setText(address.getSecondLine());
            }
            if (address.getCity() != null)
            {
                city.setText(address.getCity());
            }
            zip.setText(address.getZipCode());
            state.setText(address.getState());
        }
        addAddListener();
    }

    public void addAddListener() {
        Button addAddress = (Button) findViewById(R.id.button14);
        Address address = new Address();

        addAddress.setOnClickListener(v -> {
            String addrStr1 = addressLine1.getText().toString();
            address.setFirstLine(addrStr1);
            String nameStr = addressLine2.getText().toString();
            address.setSecondLine(nameStr);
            String cityStr = city.getText().toString();
            address.setCity(cityStr);
            String stateStr1 = state.getText().toString();
            address.setState(stateStr1);
            String zippy = zip.getText().toString();
            address.setZipCode(zippy);
            StringBuilder sb = new StringBuilder();
            sb.append(address.getFirstLine());
            String secondLine = address.getSecondLine();
            if(secondLine == null || secondLine.trim().equals(""))
                sb.append(", ");
            else {
                sb.append(" ").append(secondLine).append(", ");
            }
            sb.append(address.getCity()).append(", ").append(address.getState());
            String location = sb.toString();

            List<android.location.Address> addressList = null;

            if (!location.equals("")) {
                Geocoder geocoder = new Geocoder(AddAddressActivity.this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                    if(addressList.isEmpty() || !addressList.get(0).hasLatitude() ||
                            !addressList.get(0).hasLongitude()) {                   //Failed search
                        Context context = getApplicationContext();
                        CharSequence text = "Unable to find address.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {                                                        //Successful search
                        android.location.Address firstResult = addressList.get(0);
                        address.setLatitude(firstResult.getLatitude());
                        address.setLongitude(firstResult.getLongitude());
                        Log.d("Address latlong:", "" + address.getLatitude() + ", "
                                + address.getLongitude());
                        Intent intent = new Intent(AddAddressActivity.this, LenderCreateSpotActivity.class);
                        intent.putExtra(ParkHere.address, address);
                        intent.putExtra(ParkHere.origin, ParkHere.fromAddress);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

