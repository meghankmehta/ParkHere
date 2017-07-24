package com.sparkleusc.sparklesparkhere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import messages.MerchantAccountMessage;
import messages.Message;
import model.Address;
import model.BankPayment;
import model.Lender;
import model.MerchantPayment;
import model.User;
import model.VenmoPayment;

public class AddMerchantAccountActivity extends AppCompatActivity  implements TaskDelegate{

    Button doneButton;
    EditText accountNumberTV;
    //String accountNumber
    EditText routingNumberTV;
    EditText venmoEmailTV;
    EditText titleLabelTV;
    EditText birthLabelTV;
    EditText birthDateTV;
    EditText orLabelTV;
    Button addAddressButton;
    Address address;
    //Lender lender;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acitivity_add_merchant_account);

        doneButton       = (Button)   findViewById(R.id.create_merchant_account_button);
        accountNumberTV    = (EditText) findViewById(R.id.merchant_bank_account);
        routingNumberTV    = (EditText) findViewById(R.id.merchant_routing_number);
        venmoEmailTV       = (EditText) findViewById(R.id.merchant_venmo_email);
        titleLabelTV       = (EditText) findViewById(R.id.merchant_create_title_label);
        birthLabelTV       = (EditText) findViewById(R.id.birth_date_label);
        birthDateTV        = (EditText) findViewById(R.id.merchant_birth_date);
        orLabelTV          = (EditText) findViewById(R.id.or_label);
        addAddressButton = (Button)   findViewById(R.id.merchant_address_button);

        titleLabelTV.setEnabled(false);
        orLabelTV.setEnabled(false);
        birthLabelTV.setEnabled(false);

        Intent intent = getIntent();

        Address addressTemp = (Address) intent.getSerializableExtra(ParkHere.address);

        if (addressTemp != null )
        {
            address = addressTemp;
        }

        User userTemp = (User) intent.getSerializableExtra(ParkHere.user);
        if (userTemp != null)
        {
            user = userTemp;
        }

        MerchantAccountMessage message = (MerchantAccountMessage)intent.getSerializableExtra(ParkHere.message);
        if (message != null)
        {
            if (message.merchantPayment.getDateOfBirth() != null){
                birthDateTV.setText(message.merchantPayment.getDateOfBirth());
            }

            if (message.merchantPayment != null)
            {
                if (message.merchantPayment instanceof BankPayment)
                {
                    BankPayment bankPayment = (BankPayment) message.merchantPayment;
                    routingNumberTV.setText(bankPayment.getRoutingNumber());
                    accountNumberTV.setText(bankPayment.getAccountNumber());
                }
                else
                {
                    VenmoPayment bankPayment = (VenmoPayment) message.merchantPayment;
                    venmoEmailTV.setText(bankPayment.getEmail());
                }
            }
        }

        addDoneListener();
        addAddressListener();
    }

    private void addDoneListener(){

        doneButton.setOnClickListener(v -> {

                Boolean addressBool       = false; //checks if user inputs address
                Boolean birthDateBool     = false; //checks if user inputs birthDate
                Boolean paymentInfoBool   = false; //checks if user inputs payment info

                if(address != null )
                {
                    addressBool = true;
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter an address";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                if(birthDateTV.getText().toString().length() > 0)
                {
                    birthDateBool = true;
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a birthdate";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                if((accountNumberTV.getText().toString().length() > 0 && routingNumberTV.getText().toString().length() >0 ) ||
                        venmoEmailTV.getText().toString().length() > 0 )
                {
                    paymentInfoBool = true;
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter payment information";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                //if all required fields are filled out then proceed
                if(addressBool && birthDateBool && paymentInfoBool)
                {
                    MerchantAccountMessage message = new MerchantAccountMessage();

                    if (!venmoEmailTV.getText().toString().equals(""))
                    {
                        VenmoPayment venmoPayment = new VenmoPayment();
                        venmoPayment.setEmail(venmoEmailTV.getText().toString());
                        message.merchantPayment = venmoPayment;
                    }
                    else
                    {
                        BankPayment bankPayment = new BankPayment();
                        bankPayment.setAccountNumber(accountNumberTV.getText().toString());
                        bankPayment.setRoutingNumber(routingNumberTV.getText().toString());
                        message.merchantPayment = bankPayment;
                    }

                    message.merchantPayment.setAddress(address);
                    message.merchantPayment.setDateOfBirth(birthDateTV.getText().toString());
                    message.user = user;
                    message.lender = user.getLender();
                    new Client(AddMerchantAccountActivity.this).execute(message);
                }
        });
    }

    private void addAddressListener(){

        addAddressButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddMerchantAccountActivity.this, AddAddressMerchantActivity.class);
            intent.putExtra(ParkHere.user, user);

            Boolean paymentInfoBool = false;
            /*
            firstNameTV = (EditText)  findViewById(R.id.FirstNameET);
            firstName = firstNameTV.getText().toString();
            if (firstName.length() >0){
                firstNameBool = true;
            }
            */
            accountNumberTV    = (EditText) findViewById(R.id.merchant_bank_account);
            routingNumberTV    = (EditText) findViewById(R.id.merchant_routing_number);
            venmoEmailTV       = (EditText) findViewById(R.id.merchant_venmo_email);

            if((accountNumberTV.getText().toString().length() > 0 && routingNumberTV.getText().toString().length() >0 ) ||
                    venmoEmailTV.getText().toString().length() > 0 )
            {
                paymentInfoBool = true;
            }
            else
            {
                Context context = getApplicationContext();
                CharSequence text = "Please enter payment information";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            if(paymentInfoBool) {
                MerchantAccountMessage message = new MerchantAccountMessage();

                if (!venmoEmailTV.getText().toString().equals("")) {
                    VenmoPayment venmoPayment = new VenmoPayment();
                    venmoPayment.setEmail(venmoEmailTV.getText().toString());
                    message.merchantPayment = venmoPayment;
                } else {
                    BankPayment bankPayment = new BankPayment();
                    bankPayment.setAccountNumber(accountNumberTV.getText().toString());
                    bankPayment.setRoutingNumber(routingNumberTV.getText().toString());
                    message.merchantPayment = bankPayment;
                }
                if (!birthDateTV.getText().toString().equals("")) {
                    message.merchantPayment.setDateOfBirth(birthDateTV.getText().toString());
                }

                intent.putExtra(ParkHere.message, message);
                // if (!)
                //Log.d("lender phone", user.getLender().getProfile().getPhoneNumber());
                //Log.d("lender id", Long.toString(user.getLender().getLenderId()));
                startActivity(intent);
            }
        });
    }

    public void taskCompletionResult(Message response) {
        MerchantAccountMessage mess = (MerchantAccountMessage) response;
        if (mess.success){
            user.getLender().setMerchantId(mess.merchantId);
            Intent intent = new Intent(AddMerchantAccountActivity.this, LenderListingsPageActivity.class);
            intent.putExtra(ParkHere.user, user);
            //Log.d("lender phone", user.getLender().getProfile().getPhoneNumber());
            //Log.d("lender id", Long.toString(user.getLender().getLenderId()));
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Please enter a valid account credentials";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}
