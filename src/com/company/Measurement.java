package com.company;

import java.util.Calendar;

/**
 * This class represents single measurement. Contains information about it.
 */
public class Measurement {
    private int userID;
    private Calendar calendar;
    private double weight;

    /**
     * Constructor of measurement.
     * @param userID The ID of the user who made the measurement.
     * @param calendar Date when the measurement was made.
     * @param weight Result of measurement.
     */
    public Measurement(int userID, Calendar calendar, double weight) {
        this.userID = userID;
        this.calendar = calendar;
        this.weight = weight;
    }

    /**
     * Constructor of measurement.
     * @param userID The ID of the user who made the measurement.
     * @param date Date when the measurement was made.
     * @param weight Result of measurement.
     */
    public Measurement(int userID, String date, double weight) {
        this(userID, DateTools.StringToCalendar(date), weight);
    }

    public int getUserID() {
        return userID;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * This method returns String with date of measurement in <code>dd/MM/yyyy</code> format.
     * @return Date in String.
     */
    public String getDateInString() {
        return DateTools.calendarToString(calendar);
    }
}
