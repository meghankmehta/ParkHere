package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;

import static com.sparkleusc.sparklesparkhere.R.id.endDatePicker;

public class LenderEndDate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_end_date);
        endDatePicker();
    }

    public void endDatePicker(){
        Button endDate = (Button) findViewById(R.id.setEndDateButton);
        endDate.setOnClickListener(v -> {
            DatePicker dp = (DatePicker) findViewById(endDatePicker);
            int day = dp.getDayOfMonth();
            int month = dp.getMonth();
            int year = dp.getYear();

            UserTimeAndDate.endDay = day;
            UserTimeAndDate.endMonth = month;
            UserTimeAndDate.endYear = year;

            Intent intent;
            intent = new Intent(LenderEndDate.this, LenderAddAvailabilityTimeDate.class);
            startActivity(intent);
        });
    }
}
