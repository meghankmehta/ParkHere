package com.sparkleusc.sparklesparkhere;

import java.util.Calendar;
import java.util.Date;

class UserTimeAndDate {

    static Integer startYear, startMonth, startDay, startHour, startMinute;
    static Integer endYear, endMonth, endDay, endHour, endMinute;

    static Date componentTimeToTimestamp(int year, int month, int day, int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }
}
