package com.company.graph;

import com.company.DateTools;

import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.company.References.*;

/**
 * This implementation draws measurements from the current week.
 */
public class WeekDrawable implements Drawable {

    @Override
    public void drawPoints(Map<Date, Double> measurementPoints, Graphics g, int screenWidth, int screenHeight) {
        Calendar calendar = DateTools.getCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        g.setColor(LIGHTER_GRAY);
        g.fillPolygon(getPolygon(screenWidth, screenHeight, 7, measurementPoints, calendar, 20));
    }

    @Override
    public void drawDateScale(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(LIGHT_GRAY);
        g.fillRect(0, screenHeight - scaleSize, screenWidth, scaleSize);
        Graphics2D g2d = ((Graphics2D) g);

        double tick = (screenWidth - scaleSize) / 7.0;
        int width;
        for (int i = 0; i < 7; i++) {
            width = (int) Math.round(20 + scaleSize + tick*i);
            String day = DateFormatSymbols.getInstance().getShortWeekdays()[(i + 2) % 7];
            if (day.equals(""))
                day = DateFormatSymbols.getInstance().getShortWeekdays()[7];
            g2d.setPaint(TRANSPARENT_BLUE);
            g2d.drawLine(width, screenHeight - scaleSize, width, 0);
            g2d.setPaint(DARK_GRAY);
            g2d.drawLine(width, screenHeight - scaleSize, width, screenHeight);
            g2d.drawString(day, width + 2, screenHeight - 10);
        }
    }
}
