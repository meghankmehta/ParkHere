package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TimePicker;

public class LenderEndTime extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_end_time);
        setEndTimeListener();
    }

    @SuppressWarnings("deprecation")
    public void setEndTimeListener(){
        Button endTime = (Button) findViewById(R.id.setEndTimeButton);
        endTime.setOnClickListener(v -> {
            TimePicker endTimeClock = (TimePicker) findViewById(R.id.endTimeClock);

            int hour = endTimeClock.getCurrentHour();
            int minute = endTimeClock.getCurrentMinute();

            UserTimeAndDate.endHour = hour;
            UserTimeAndDate.endMinute = minute;

            Intent intent;
            intent = new Intent(LenderEndTime.this, LenderAddAvailabilityTimeDate.class);

            startActivity(intent);
        });


    }



}
