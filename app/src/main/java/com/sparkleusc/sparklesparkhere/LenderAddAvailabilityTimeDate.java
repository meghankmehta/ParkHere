package com.sparkleusc.sparklesparkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import model.ListingAvailibility;

import static com.sparkleusc.sparklesparkhere.UserTimeAndDate.componentTimeToTimestamp;

public class LenderAddAvailabilityTimeDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_add_availability_time_date);

        EditText startTime = (EditText) findViewById(R.id.start_time_label);
        EditText endTime = (EditText)  findViewById(R.id.end_time_label);
        EditText startDate = (EditText)  findViewById(R.id.start_date_label);
        EditText endDate = (EditText) findViewById(R.id.end_date_label);
        startTime.setEnabled(false);
        endTime.setEnabled(false);
        startDate.setEnabled(false);
        endDate.setEnabled(false);

        if (UserTimeAndDate.startYear != null){
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, UserTimeAndDate.startYear);
            cal.set(Calendar.MONTH,UserTimeAndDate.startMonth);
            cal.set(Calendar.DAY_OF_MONTH, UserTimeAndDate.startDay);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            java.util.Date date = cal.getTime();

            // Date todayWithZeroTime = formatter.parse(formatter.format(date));
            startDate.setText(formatter.format(date));
        }

        if (UserTimeAndDate.endYear != null){
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, UserTimeAndDate.endYear);
            cal.set(Calendar.MONTH,UserTimeAndDate.endMonth);
            cal.set(Calendar.DAY_OF_MONTH, UserTimeAndDate.endDay);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            java.util.Date date = cal.getTime();
            endDate.setText(formatter.format(date));
        }

        if (UserTimeAndDate.startHour != null){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 0);
            cal.set(Calendar.MONTH,0);
            cal.set(Calendar.DAY_OF_MONTH, 0);
            cal.set(Calendar.HOUR_OF_DAY, UserTimeAndDate.startHour);
            cal.set(Calendar.MINUTE, UserTimeAndDate.startMinute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            java.util.Date date = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
            startTime.setText(sdf.format(date));
        }

        if (UserTimeAndDate.endHour != null){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 0);
            cal.set(Calendar.MONTH,0);
            cal.set(Calendar.DAY_OF_MONTH, 0);
            cal.set(Calendar.HOUR_OF_DAY, UserTimeAndDate.endHour);
            cal.set(Calendar.MINUTE, UserTimeAndDate.endMinute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            java.util.Date date = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
            endTime.setText(sdf.format(date));
        }
        addTimeListener();
        endTimeListener();
        addDateListener();
        endDateListener();

        doneButtonListener();
       // user = (User)intent.getSerializableExtra(ParkHere.user);

    }

    public void addTimeListener(){
        Button addTime = (Button) findViewById(R.id.addStartTimeButton);
        addTime.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LenderAddAvailabilityTimeDate.this, LenderStartTime.class);
            startActivity(intent);
        });
    }

    public void endTimeListener(){
        Button endTime = (Button) findViewById(R.id.endTimeButton);
        endTime.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LenderAddAvailabilityTimeDate.this, LenderEndTime.class);

            // intent.putExtra(ParkHere.user, user);
            startActivity(intent);
        });
    }

   public void addDateListener(){
        Button addDate = (Button) findViewById(R.id.startDateButton);
        addDate.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LenderAddAvailabilityTimeDate.this, LenderStartDate.class);
            startActivity(intent);
        });
    }


    public void endDateListener(){
        Button endTime = (Button) findViewById(R.id.endDateButton);
        endTime.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LenderAddAvailabilityTimeDate.this, LenderEndDate.class);
            startActivity(intent);
        });
    }

    public void doneButtonListener(){
        Button done = (Button) findViewById(R.id.doneButton);
        done.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(LenderAddAvailabilityTimeDate.this, LenderAddAvailability.class);
            intent.putExtra(ParkHere.availability, sendTimeAndDateToServer());
            intent.putExtra(ParkHere.origin, ParkHere.fromRight);
            startActivity(intent);
        });
    }

    private ListingAvailibility sendTimeAndDateToServer(){
        Date endTimeStamp = componentTimeToTimestamp(UserTimeAndDate.endYear, UserTimeAndDate.endMonth, UserTimeAndDate.endDay, UserTimeAndDate.endHour, UserTimeAndDate.endMinute);
        Date startTimeStamp = componentTimeToTimestamp(UserTimeAndDate.startYear, UserTimeAndDate.startMonth, UserTimeAndDate.startDay, UserTimeAndDate.startHour, UserTimeAndDate.startMinute);

        ListingAvailibility la = new ListingAvailibility();
        Timestamp setTemp = new Timestamp(startTimeStamp.getTime());
        la.setBeginDateTime(setTemp);
        Timestamp endTemp = new Timestamp(endTimeStamp.getTime());
        la.setEndDateTime(endTemp);
        return la;
    }
}
