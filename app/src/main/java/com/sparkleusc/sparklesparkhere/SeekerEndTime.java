package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

public class SeekerEndTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_end_time);

        doneButton();
    }
    @SuppressWarnings("deprecation")
    public void doneButton(){
        Button startDate = (Button) findViewById(R.id.button15);
        startDate.setOnClickListener(v -> {

            TimePicker endTimeClock = (TimePicker) findViewById(R.id.sTimePickerEnd);

            int hour = endTimeClock.getCurrentHour();
            int minute = endTimeClock.getCurrentMinute();

            SeekerTimeAndDate.endHour = hour;
            SeekerTimeAndDate.endMinute = minute;

            Intent intent = new Intent(SeekerEndTime.this, AdvancedSettingsActivity.class);
            intent.putExtra(ParkHere.user, getIntent().getSerializableExtra(ParkHere.user));
            startActivity(intent);
        });

    }
}
