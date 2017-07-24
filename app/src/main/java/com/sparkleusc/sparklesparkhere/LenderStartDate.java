package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;

import static com.sparkleusc.sparklesparkhere.R.id.startDatePicker;

public class LenderStartDate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_start_date);
        setDateListener();
    }
    public void setDateListener(){
        Button startDate = (Button) findViewById(R.id.setStartDateButton);
        startDate.setOnClickListener(v -> {

            DatePicker dp = (DatePicker) findViewById(startDatePicker);
            int day = dp.getDayOfMonth();
            int month = dp.getMonth();
            int year = dp.getYear();

            UserTimeAndDate.startDay = day;
            UserTimeAndDate.startMonth = month;
            UserTimeAndDate.startYear = year;

            Intent intent = new Intent(LenderStartDate.this, LenderAddAvailabilityTimeDate.class);
            startActivity(intent);
        });
    }
}
