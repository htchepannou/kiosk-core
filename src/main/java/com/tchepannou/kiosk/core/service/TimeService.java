package com.tchepannou.kiosk.core.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeService {
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    public Date now (){
        return getCalendar().getTime();
    }
    public Date toDate(int year, int month, int day){
        return toDate(year, month, day, 0, 0, 0);
    }

    public Date toDate(int year, int month, int day, int hour, int minute, int second){
        Calendar cal = getCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MINUTE, minute);

        return cal.getTime();
    }

    public String format(Date date){
        final DateFormat fmt = new SimpleDateFormat(DATETIME_FORMAT);
        fmt.setTimeZone(GMT);

        return date != null ? fmt.format(date) : null;
    }

    public Date parse(final String date) throws ParseException{
        final DateFormat fmt = new SimpleDateFormat(DATETIME_FORMAT);
        fmt.setTimeZone(GMT);

        return date != null ? fmt.parse(date) : null;
    }

    private Calendar getCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(GMT);
        return cal;
    }
}
