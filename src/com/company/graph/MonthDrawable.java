package com.company.graph;

import com.company.DateTools;

import java.awt.*;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.company.References.*;

/**
 * This implementation draws measurements from the current month.
 */
public class MonthDrawable implements Drawable {

    @Override
    public void drawPoints(Map<Date, Double> measurementPoints, Graphics g, int screenWidth, int screenHeight) {
        Calendar calendar = DateTools.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        int daysOfMonth = yearMonth.lengthOfMonth();

        g.setColor(LIGHTER_GRAY);
        g.fillPolygon(getPolygon(screenWidth, screenHeight, daysOfMonth, measurementPoints, calendar, 10));
    }

    @Override
    public void drawDateScale(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(LIGHT_GRAY);
        g.fillRect(0, screenHeight - scaleSize, screenWidth, scaleSize);

        Graphics2D g2d = ((Graphics2D) g);

        Calendar calendar = Calendar.getInstance();
        int days = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1).lengthOfMonth();
        double tick = (screenWidth - scaleSize) / (double) days;
        for (int i = 0; i < days; i++) {
            int width = (int) Math.round(10 + scaleSize + tick*i);
            g2d.setPaint(TRANSPARENT_BLUE);
            g2d.drawLine(width, screenHeight - scaleSize, width, 0);
            g2d.setPaint(DARK_GRAY);
            g2d.drawLine(width, screenHeight - scaleSize, width, screenHeight);
            g2d.drawString((i + 1) + "", width + 2, screenHeight - 10);
        }
    }
}
