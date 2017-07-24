package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TimePicker;

public class LenderStartTime extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_start_time);
        setStartTimeListener();
    }

    @SuppressWarnings("deprecation")
    public void setStartTimeListener(){
        Button startTime = (Button) findViewById(R.id.setStartTimeButton);
        startTime.setOnClickListener(v -> {

            TimePicker startTimeClock = (TimePicker) findViewById(R.id.startTime);

            int hour = startTimeClock.getCurrentHour();
            int minute = startTimeClock.getCurrentMinute();

            UserTimeAndDate.startHour = hour;
            UserTimeAndDate.startMinute = minute;

            Intent intent = new Intent(LenderStartTime.this, LenderAddAvailabilityTimeDate.class);
            startActivity(intent);
        });
    }
}
