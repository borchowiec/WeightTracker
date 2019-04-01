package com.company.graph;

import com.company.DateTools;

import java.awt.*;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.company.References.*;

/**
 * This implementation draws measurements from the current year.
 */
public class YearDrawable implements Drawable {

    @Override
    public void drawPoints(Map<Date, Double> measurementPoints, Graphics g, int screenWidth, int screenHeight) {
        Calendar calendar = DateTools.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);

        int daysOfMonth = 365;
        if (calendar.get(Calendar.YEAR) % 4 == 0)
            daysOfMonth = 366;

        g.setColor(LIGHTER_GRAY);
        g.fillPolygon(getPolygon(screenWidth, screenHeight, daysOfMonth, measurementPoints, calendar, 15));
    }

    @Override
    public void drawDateScale(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(LIGHT_GRAY);
        g.fillRect(0, screenHeight - scaleSize, screenWidth, scaleSize);
        Graphics2D g2d = ((Graphics2D) g);

        Calendar calendar = Calendar.getInstance();
        boolean isLeapYear = (calendar.get(Calendar.YEAR) % 4 == 0);
        double tick;

        if (isLeapYear)
            tick = (screenWidth - scaleSize) / 366.0;
        else
            tick = (screenWidth - scaleSize) / 365.0;

        g2d.setPaint(TRANSPARENT_BLUE);
        g2d.drawLine(15 + scaleSize, screenHeight - scaleSize, 15 + scaleSize, 0);
        g2d.setPaint(DARK_GRAY);
        g2d.drawLine(15 + scaleSize, screenHeight - scaleSize, 15 + scaleSize, screenHeight);
        g2d.drawString(DateFormatSymbols.getInstance().getMonths()[0], 17 + scaleSize, screenHeight - 10);
        int days = 0;

        for (int i = 1; i < 12; i++) {
            YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), i);
            days += yearMonth.lengthOfMonth();
            int width = (int) Math.round(15 + scaleSize + tick*days);
            String month = DateFormatSymbols.getInstance().getMonths()[i];

            g2d.setPaint(TRANSPARENT_BLUE);
            g2d.drawLine(width, screenHeight - scaleSize, width, 0);
            g2d.setPaint(DARK_GRAY);
            g2d.drawLine(width, screenHeight - scaleSize, width, screenHeight);
            g2d.drawString(month, width + 2, screenHeight - 10);
        }
    }
}
