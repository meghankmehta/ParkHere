package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;

public class SeekerStartDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_start_date);
        doneButton();
    }

    public void doneButton(){
        Button startDate = (Button) findViewById(R.id.doneButton);
        startDate.setOnClickListener(v -> {
            DatePicker dp = (DatePicker) findViewById(R.id.sDatePickerStart);
            int day = dp.getDayOfMonth();
            int month = dp.getMonth();
            int year = dp.getYear();

            SeekerTimeAndDate.startDay = day;
            SeekerTimeAndDate.startMonth = month;
            SeekerTimeAndDate.startYear = year;

            Intent intent = new Intent(SeekerStartDate.this, AdvancedSettingsActivity.class);
            intent.putExtra(ParkHere.user, getIntent().getSerializableExtra(ParkHere.user));
            startActivity(intent);
        });
    }
}
