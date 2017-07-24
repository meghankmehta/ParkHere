package com.sparkleusc.sparklesparkhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;

import messages.GetClientTokenMessage;
import messages.Message;
import messages.ReservationMessage;
import messages.SearchMessage;
import model.ListingResult;
import model.Reservation;
import model.User;

import static com.sparkleusc.sparklesparkhere.ParkHere.clientToken;

public class MakePayment extends AppCompatActivity {
    String clientToken;
    private ListingResult result;
    private SearchMessage search;
    private User user;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent i = getIntent();
//        result = (ListingResult) i.getSerializableExtra(ParkHere.result);
//        search = (SearchMessage) i.getSerializableExtra(ParkHere.search);
//        user = (User) i.getSerializableExtra(ParkHere.user);
//        setContentView(R.layout.activity_make_payment);
//
//
//        clientToken = (String) getIntent().getSerializableExtra(ParkHere.clientToken);
//
//    }
//
//    public void taskCompletionResult(Message response) {
//        ReservationMessage reservationMessage = (ReservationMessage) response;
//        Log.d("transaction id ", reservationMessage.reservation.getBTTransactionId());
//    }
//
//    public void onBraintreeSubmit(View v) {
//        PaymentRequest paymentRequest = new PaymentRequest()
//                .clientToken(clientToken);
//        startActivityForResult(paymentRequest.getIntent(this), 1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//      //  if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Log.d("got nonce", "got client nonce");
//                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
//                        BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
//                );
//                String nonce = paymentMethodNonce.getNonce();
//
//                ReservationMessage rm = new ReservationMessage();
//                Reservation reservation = new Reservation();
//                reservation.setLenderId(result.listing.getLenderId());
//                reservation.setLender(result.listing.getLender());
//                //Log.d("listing merchant id", result.listing.getLender().toString());
//                reservation.setListing(result.listing);
//                reservation.setSeekerId(user.getSeeker().getSeekerId());
//                reservation.setListingId(result.listing.getListingId());
//                reservation.setBeginDate(search.advanced.getStartTime());
//                reservation.setEndDate(search.advanced.getEndTime());
//                rm.reservation = reservation;
//                rm.nonce = nonce;
//                rm.action = Message.insert;
//                new Client(MakePayment.this).execute(rm);
//
//                // Send the nonce to your server.
//            }
//      //  }
//    }
}



/*
DO NOT DELETE DO NOT DELETE DO NOT DELETE DO NOT DELETE DO NOT DELETE DO NOT DELETE - make reservation network code

            ReservationMessage rm = new ReservationMessage();
            Reservation reservation = new Reservation();
            reservation.setLenderId(result.listing.getLenderId());
            reservation.setSeekerId(user.getSeeker().getSeekerId());
            reservation.setListingId(result.listing.getListingId());
            reservation.setBeginDate(search.advanced.getStartTime());
            reservation.setEndDate(search.advanced.getEndTime());
            rm.reservation = reservation;
            rm.action = Message.insert;
            new Client(MakeReservationActivity.this).execute(rm);
 */