package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;

import model.AdvancedSearch;
import model.User;

import static com.sparkleusc.sparklesparkhere.UserTimeAndDate.componentTimeToTimestamp;

public class AdvancedSettingsActivity extends AppCompatActivity {


    private SeekBar priceSB;
    private SeekBar distanceSB;
    private RatingBar lenderRB;
    private CheckBox coveredCB;
    private CheckBox handicappedCB;
    private CheckBox compactCB;
    private CheckBox tandemCB;
    private CheckBox truckCB;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);
        priceSB = (SeekBar) findViewById(R.id.priceSeekBar);
        distanceSB = (SeekBar) findViewById(R.id.distanceSeekBar);
        lenderRB = (RatingBar) findViewById(R.id.lenderRatingBar);
        coveredCB = (CheckBox) findViewById(R.id.coveredCB);
        handicappedCB = (CheckBox) findViewById(R.id.handicappedCB);
        compactCB = (CheckBox) findViewById(R.id.compactCB);
        tandemCB = (CheckBox) findViewById(R.id.tandemCB);
        truckCB = (CheckBox) findViewById(R.id.truckCB);
        user = (User) getIntent().getSerializableExtra(ParkHere.user);

        TextView priceTV = (TextView)findViewById(R.id.priceTV);
        priceSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceTV.setText("$" + String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        TextView distanceTV = (TextView)findViewById(R.id.distanceTV);
        distanceSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceTV.setText(String.valueOf(progress) + " miles");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        doneButtonListener();
        enterStartTime();
        enterEndTime();
        enterStartDate();
        enterEndDate();

    }

    public void doneButtonListener(){
        Button done = (Button) findViewById(R.id.doneButton);
        done.setOnClickListener(v -> {
            int price = priceSB.getProgress();
            int distance = distanceSB.getProgress();
            float rating = lenderRB.getRating();
            boolean covered = coveredCB.isChecked();
            boolean handicapped = handicappedCB.isChecked();
            boolean compact = compactCB.isChecked();
            boolean tandem = tandemCB.isChecked();
            boolean truck = truckCB.isChecked();
            Date endTimeStamp = componentTimeToTimestamp(SeekerTimeAndDate.endYear, SeekerTimeAndDate.endMonth, SeekerTimeAndDate.endDay, SeekerTimeAndDate.endHour, SeekerTimeAndDate.endMinute);
            Date startTimeStamp = componentTimeToTimestamp(SeekerTimeAndDate.startYear, SeekerTimeAndDate.startMonth, SeekerTimeAndDate.startDay, SeekerTimeAndDate.startHour, SeekerTimeAndDate.startMinute);
            AdvancedSearch advanced = new AdvancedSearch(price, distance, rating, covered,
                    handicapped, compact, tandem, truck, new Timestamp(startTimeStamp.getTime()), new Timestamp(endTimeStamp.getTime()), 0, 0);
            Intent intent = new Intent(AdvancedSettingsActivity.this, SeekerEnterAddressActivity.class);
            intent.putExtra("advanced", advanced);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    public void enterStartTime(){
        Button startTime = (Button) findViewById(R.id.startTimeButton);
        int startHour = SeekerTimeAndDate.startHour;
        if(startHour != -1) {
            startTime.setText(startHour + ":" + SeekerTimeAndDate.startMinute);
        }
        startTime.setOnClickListener(v -> {
            Intent intent = new Intent(AdvancedSettingsActivity.this, SeekerStartTime.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

    public void enterEndTime(){
        Button endTime = (Button) findViewById(R.id.endTimeButton);
        int endHour = SeekerTimeAndDate.endHour;
        if(endHour != -1) {
            endTime.setText(endHour + ":" + SeekerTimeAndDate.endMinute);
        }
        endTime.setOnClickListener(v -> {
            Intent intent = new Intent(AdvancedSettingsActivity.this, SeekerEndTime.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });

    }
    public void enterStartDate(){
        Button startDate = (Button) findViewById(R.id.startDateButton);
        int startDay = SeekerTimeAndDate.startDay;
        if(startDay != -1) {
            startDate.setText(SeekerTimeAndDate.startMonth + "/" + startDay + "/" +
                    SeekerTimeAndDate.startYear);
        }
        startDate.setOnClickListener(v -> {
            Intent intent = new Intent(AdvancedSettingsActivity.this, SeekerStartDate.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });

    }
    public void enterEndDate(){
        Button endDate = (Button) findViewById(R.id.enterEndDate);
        int endDay = SeekerTimeAndDate.endDay;
        if(endDay != -1) {
            endDate.setText(SeekerTimeAndDate.endMonth + "/" + endDay + "/" +
                    SeekerTimeAndDate.endYear);
        }
        endDate.setOnClickListener(v -> {
            Intent intent = new Intent(AdvancedSettingsActivity.this, SeekerEndDate.class);
            intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });

    }


}
