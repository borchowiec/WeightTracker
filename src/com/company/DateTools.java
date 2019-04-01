package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class contains methods that handle problems with dates e.g. conversion from <code>String</code> to <code>Date</code>.
 */
public class DateTools {
    /**
     * This method gets instance of calendar and resets clock to 0:00:00.
         * @return Calendar withs reset clock.
     */
    public static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    /**
     * This class converts String date to <code>Date</code> object. String has to be like: <code>dd/MM/yyyy</code>.
     * @param stringDate Date in String form.
     * @return Date object with date given in String.
     */
    public static Date StringToDate(String stringDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.parse(stringDate);
            Calendar calendar = sdf.getCalendar();
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This class converts <code>Calendar</code> date to <code>String</code> date. Output format is in <code>dd/MM/yyyy</code> form.
     * @param calendar Calendar that you want to convert.
     * @return Calendar converted to String.
     */
    public static String calendarToString(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.YEAR);
    }

    /**
     * This class converts String date to <code>Calendar</code> object. String has to be like: <code>dd/MM/yyyy</code>.
     * @param date Date in String form.
     * @return Calendar object with date given in String.
     */
    public static Calendar StringToCalendar(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.parse(date);
            return sdf.getCalendar();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
