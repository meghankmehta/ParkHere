package com.sparkleusc.sparklesparkhere;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import messages.ListingMessage;
import messages.Message;
import model.Address;
import model.CancellationPolicy;
import model.Category;
import model.Listing;
import model.ListingAvailibility;
import model.User;

import static com.sparkleusc.sparklesparkhere.ParkHere.origin;

public class LenderCreateSpotActivity extends AppCompatActivity implements TaskDelegate{
    private EditText nameOfSpot;
    private EditText descriptionOfSpot;
    private EditText priceOfSpot;
    private CheckBox truck;
    private CheckBox SUV;
    private CheckBox handicapped;
    private CheckBox covered;
    private CheckBox compact;
    private CheckBox tandem;
    private Spinner spinner;
    private Button deleteButton;
    private static Listing updateListing;
    private static Listing newListing;
    private static Address address;
    private static List<ListingAvailibility> listingAvails;
    private Button reviewButt;
    private static User user;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_create);

        descriptionOfSpot = (EditText) findViewById(R.id.editText16);
        nameOfSpot = (EditText) findViewById(R.id.editText10);
        priceOfSpot = (EditText) findViewById(R.id.editText12);
        truck = (CheckBox) findViewById(R.id.checkBox3);
        SUV = (CheckBox) findViewById(R.id.checkBox4);
        tandem = (CheckBox) findViewById(R.id.checkBox5);
        handicapped = (CheckBox) findViewById(R.id.checkBox);
        covered = (CheckBox) findViewById(R.id.checkBox6);
        compact = (CheckBox) findViewById(R.id.checkBox7);
        spinner = (Spinner) findViewById(R.id.cancellation_spinner);
        deleteButton = (Button) findViewById(R.id.deleteListingButton);
        reviewButt = (Button) findViewById(R.id.button19);
        Intent intent = getIntent();
        String origin = (String) intent.getSerializableExtra(ParkHere.origin);

        switch (origin) {
            case ParkHere.fromLeft:
                user = (User) intent.getSerializableExtra(ParkHere.user);
                updateListing = (Listing) intent.getSerializableExtra(ParkHere.updateListing);
                newListing = new Listing();
                listingAvails = null;
                LenderAddAvailability.newList = null;
                AddPhotosActivity.finalList = null;
                LenderAddAvailability.allListings = null;
                LenderAddAvailability.toDelete = null;
                address = null;
                if(updateListing != null) {
                    listingAvails =  new ArrayList<>( updateListing.getAvailabilityList().values());
                    address = updateListing.getAddress();
                }


                populateGUI(updateListing);
                break;
            case ParkHere.fromRight:
                listingAvails = LenderAddAvailability.newList;
                populateGUI(newListing);
                break;
            case ParkHere.fromAddress:
                address = (Address) intent.getSerializableExtra(ParkHere.address);
                populateGUI(newListing);
                break;
            case ParkHere.fromImages:
                populateGUI(newListing);
                break;
            default:
                populateGUI(newListing);
                break;
        }
        if (updateListing == null){
            deleteButton.setVisibility(View.INVISIBLE);
            reviewButt.setVisibility(View.INVISIBLE);
        }

        addSpinner();
        addCreateSpotListener();
        addAvailabilityListener();
        addAddressListener();
        addPhotosListener();
        addReviewListener();
        addDeleteListener();

    }

    public void addReviewListener(){

        reviewButt.setOnClickListener(v -> {

            Intent intent;
            intent = new Intent(LenderCreateSpotActivity.this, LenderReviewPage.class);
            intent.putExtra(ParkHere.user, user);
            if(updateListing != null){
                intent.putExtra(ParkHere.listing, updateListing);
            }

            startActivity(intent);
        });
    }

    public void populateGUI(Listing currentListing){
        Button addAvailability = (Button) findViewById(R.id.addAvailabilityButton);
        if(listingAvails != null) {
            if (listingAvails.size() == 1)
                addAvailability.setText("1 Listing");
            else
                addAvailability.setText(listingAvails.size() + " Listings");
        }

        Button addPhotos = (Button) findViewById(R.id.button18);
        if (AddPhotosActivity.finalList != null){
            if (AddPhotosActivity.finalList.size() == 1)
                addPhotos.setText("1 Image");
            else
                addPhotos.setText(AddPhotosActivity.finalList.size() + " Images");
        }

        if (address != null){
            Button addAddress = (Button) findViewById(R.id.addressButton);
            addAddress.setText(address.getFirstLine());
        }

        if(currentListing != null) {
            nameOfSpot.setText(currentListing.getTitle());
            descriptionOfSpot.setText(currentListing.getDescription());
            priceOfSpot.setText(String.valueOf(currentListing.getPricePerHr()));

            if (currentListing.getCategories() != null && !currentListing.getCategories().isEmpty()) {
                List<String> categories = currentListing.getCategories();
                compact.setChecked(categories.contains(Category.COMPACT));
                covered.setChecked(categories.contains(Category.COVERED));
                handicapped.setChecked(categories.contains(Category.HANDICAPPED));
                SUV.setChecked(categories.contains(Category.SUV));
                tandem.setChecked(categories.contains(Category.TANDEM));
                truck.setChecked(categories.contains(Category.TRUCK));
            }
        }
    }

    public void addPhotosListener(){
        Button addPhotos = (Button) findViewById(R.id.button18);

        addPhotos.setOnClickListener(v -> {
                populateListing();
                Intent intent;
                intent = new Intent(LenderCreateSpotActivity.this, AddPhotosActivity.class);
                if (updateListing != null){
                    intent.putExtra(ParkHere.listing, updateListing.getListingId());
                }
                startActivity(intent);
        });
    }

    public void addAddressListener(){
        Button addAddress = (Button) findViewById(R.id.addressButton);
        addAddress.setOnClickListener(v -> {
            Intent intent;
            populateListing();
            intent = new Intent(LenderCreateSpotActivity.this, AddAddressActivity.class);
            if (updateListing != null){
                intent.putExtra(ParkHere.address, updateListing.getAddress());
            }
            startActivity(intent);
        });
    }

    public void addSpinner(){
        CancellationPolicy.initialize();
        Spinner spinner = (Spinner) findViewById(R.id.cancellation_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CancellationPolicy.viewPolicies);
        spinner.setAdapter(adapter);
        if (updateListing != null) {
            int spinnerPosition = adapter.getPosition(CancellationPolicy.dbToPolicy.get(updateListing.getCancellationPolicy()));
            spinner.setSelection(spinnerPosition);
        }
    }


    @SuppressLint("SetTextI18n")
    public void addAvailabilityListener(){
        Button addAvailability = (Button) findViewById(R.id.addAvailabilityButton);

        addAvailability.setOnClickListener(v -> {
            Intent intent = new Intent(LenderCreateSpotActivity.this, LenderAddAvailability.class);
            populateListing();
            intent.putExtra(origin, ParkHere.fromLeft);
            if (updateListing != null)
                intent.putExtra(ParkHere.availabilities, updateListing.getAvailabilityList());
            startActivity(intent);
        });
    }
    @SuppressLint("SetTextI18n")
    public void addCreateSpotListener(){
        Button createSpot = (Button) findViewById(R.id.button12);
        if(updateListing != null){
            createSpot.setText("Update");
        }

        createSpot.setOnClickListener(v -> {
            newListing.setLenderId(user.getLender().getLenderId());

            populateListing();
            Boolean lANotEmpty = false;
            Boolean hasTitle   = false;
            Boolean hasPrice   = false;
            Boolean hasAddress = false;
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();

            if(listingAvails != null ){
                lANotEmpty = true;
            }
            else
            {
                CharSequence text = "Please add Availabilities.";
                Toast.makeText(context, text, duration).show();
            }

            if(nameOfSpot.getText().toString().length() > 0){
                hasTitle = true;
            }
            else
            {
                CharSequence text = "Please add Title.";
                Toast.makeText(context, text, duration).show();
            }

            if(priceOfSpot.getText().toString().length() > 0 ){
                hasPrice = true;
            }
            else
            {
                CharSequence text = "Please add Price.";
                Toast.makeText(context, text, duration).show();
            }
            if(address != null){
                hasAddress = true;
            }
            else
            {
                CharSequence text = "Please add an Address.";
                Toast.makeText(context, text, duration).show();
            }
            if(lANotEmpty && hasTitle && hasPrice && hasAddress) {
                ListingMessage listingMessage = new ListingMessage();
                listingMessage.listing = newListing;
                listingMessage.availabilities = LenderAddAvailability.newList;
                listingMessage.images = AddPhotosActivity.finalList;

                if(updateListing != null) {

                    listingMessage.deleteAvailabilities = LenderAddAvailability.toDelete;
                    listingMessage.updateListing = updateListing;
                    listingMessage.action = Message.update;
                    user.getLender().getListings().remove(updateListing.getListingId());

                }
                else {
                    listingMessage.action = Message.insert;
                }
                new Client(LenderCreateSpotActivity.this).execute(listingMessage);
            }

        });
    }


    private void populateListing(){
        Log.d("populate listing", "poluating listing");
        if(nameOfSpot != null){
            Log.d("name is not null", "populate listing");
            String nameStr = nameOfSpot.getText().toString();
            newListing.setTitle(nameStr);
        }

        if(descriptionOfSpot != null){
            String descStr = descriptionOfSpot.getText().toString();
            newListing.setDescription(descStr);
        }
        if(priceOfSpot != null && !priceOfSpot.getText().toString().equals("")){
            String priceStr = priceOfSpot.getText().toString();
            newListing.setPrice_per_hr(Double.parseDouble(priceStr));
        }

        newListing.setCategories(new ArrayList<>());
        if (SUV.isChecked()){
            newListing.getCategories().add(Category.SUV);
        }
        if (truck.isChecked()){
            newListing.getCategories().add(Category.TRUCK);
        }
        if (handicapped.isChecked()){
            newListing.getCategories().add(Category.HANDICAPPED);
        }
        if (compact.isChecked()){
            newListing.getCategories().add(Category.COMPACT);
        }
        if (covered.isChecked()){
            newListing.getCategories().add(Category.COVERED);
        }
        if (tandem.isChecked()){
            newListing.getCategories().add(Category.TANDEM);
        }
        newListing.setCancellationPolicy(CancellationPolicy.policiesTodb.get(spinner.getSelectedItem().toString()));
        newListing.setAddress(address);
    }

    private void addDeleteListener(){
        deleteButton.setOnClickListener(v -> {
            ListingMessage message = new ListingMessage();
            message.action = Message.delete;
            message.listing = updateListing;
            new Client(LenderCreateSpotActivity.this).execute(message);
        });
    }

    public void taskCompletionResult(Message response) {
        if(response instanceof ListingMessage){
            ListingMessage mes = (ListingMessage) response;
            if (response.action.equals(Message.delete)){
                user.getLender().getListings().get(updateListing.getListingId()).setDeleted(true);
            }
            else{
                mes.listing.setDeleted(false);
                user.getLender().getListings().put(mes.listing.getListingId(), mes.listing);
            }
            Intent intent = new Intent(LenderCreateSpotActivity.this, LenderListingsPageActivity.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        }
    }
}
