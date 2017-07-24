package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import messages.Message;
import messages.SearchMessage;
import model.Address;
import model.AdvancedSearch;

public class SeekerEnterAddressActivity extends AppCompatActivity implements TaskDelegate{
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText city;
    private EditText state;
    private EditText zip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_enter_address);

        addListener();
    }
    public void addListener() {
        addressLine1 = (EditText) findViewById(R.id.addressLine1Merchant);
        addressLine2 = (EditText) findViewById(R.id.addressLine2Merchant);
        city = (EditText) findViewById(R.id.cityMerchant);
        state = (EditText) findViewById(R.id.stateMerchant);
        Button addAddress = (Button) findViewById(R.id.addButtonMerchant);
        zip = (EditText) findViewById(R.id.zipMerchant);
        Address address = new Address();
        addAddress.setOnClickListener(v -> {
            if (addressLine1 != null) {
                String addrStr1 = addressLine1.getText().toString();
                address.setFirstLine(addrStr1);
            }
            if (addressLine2 != null) {
                String nameStr = addressLine2.getText().toString();
                address.setSecondLine(nameStr);
            }
            if (city != null) {
                String cityStr = city.getText().toString();
                address.setCity(cityStr);
            }
            if (state != null) {
                String stateStr1 = state.getText().toString();
                address.setState(stateStr1);
            }
            if (zip != null) {
                String zippy = zip.getText().toString();
                address.setZipCode(zippy);
            }
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
                Geocoder geocoder = new Geocoder(SeekerEnterAddressActivity.this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                    if(addressList.isEmpty() || !addressList.get(0).hasLatitude() ||
                            !addressList.get(0).hasLongitude()) {                   //Failed search
                        Context context = getApplicationContext();
                        CharSequence  text = "Unable to find address.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {                                                        //Successful search
                        android.location.Address address1 = addressList.get(0);
                        AdvancedSearch advanced = (AdvancedSearch) getIntent().getSerializableExtra("advanced");
                        advanced.setLat(address1.getLatitude());
                        advanced.setLon(address1.getLongitude());
                        SearchMessage searchMessage = new SearchMessage();
                        searchMessage.advanced = advanced;
                        searchMessage.useTimes = true;
                        new Client(SeekerEnterAddressActivity.this).execute(searchMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void taskCompletionResult(Message m) {
        SearchMessage sm = (SearchMessage) m;
        Intent intent = new Intent(SeekerEnterAddressActivity.this, MapsActivity.class);
        intent.putExtra("results", sm);
        intent.putExtra(ParkHere.user, getIntent().getSerializableExtra(ParkHere.user));
        startActivity(intent);
    }
}
