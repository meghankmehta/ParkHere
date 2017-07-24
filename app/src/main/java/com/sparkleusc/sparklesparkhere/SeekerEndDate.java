package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

public class SeekerEndDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_end_date);

        doneButton();
    }

    public void doneButton(){
        Button startDate = (Button) findViewById(R.id.button16);
        startDate.setOnClickListener(v -> {

            DatePicker dp = (DatePicker) findViewById(R.id.sDatePickerEnd);
            int day = dp.getDayOfMonth();
            int month = dp.getMonth();
            int year = dp.getYear();

            SeekerTimeAndDate.endDay = day;
            SeekerTimeAndDate.endMonth = month;
            SeekerTimeAndDate.endYear = year;

            Intent intent = new Intent(SeekerEndDate.this, AdvancedSettingsActivity.class);
            intent.putExtra(ParkHere.user, getIntent().getSerializableExtra(ParkHere.user));
            startActivity(intent);
        });

    }
}
