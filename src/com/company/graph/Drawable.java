package com.company.graph;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.company.References.*;

/**
 * This interface is using to drawing graphs. You can implement how do you want to draw it e.g. how many points do you
 * want to draw or how scale will be looking.
 */
public interface Drawable {
    /**
     * Lower value of the weight scale.
     */
    double bottomWeightValue = 30;

    /**
     * Upper value of the weight scale
     */
    double topWeightValue = 150;

    /**
     * Step between weight values.
     */
    double weightStep = 10;

    /**
     * Width or height (it depends which scale it is) of scale.
     */
    int scaleSize = 40;

    /**
     * This method draws points on graph.
     * @param measurementPoints Map with measurementPoints.
     * @param g Graphics using to draw.
     * @param screenWidth width of {@link GraphPanel}
     * @param screenHeight height of {@link GraphPanel}
     */
    void drawPoints(Map<Date, Double> measurementPoints, Graphics g, int screenWidth, int screenHeight);

    /**
     * This method draws scale with dates.
     * @param g Graphics using to draw.
     * @param screenWidth width of {@link GraphPanel}
     * @param screenHeight height of {@link GraphPanel}
     */
    void drawDateScale(Graphics g, int screenWidth, int screenHeight);

    /**
     * This method draws scale with weights. Default implementation draws weights between {@link this#bottomWeightValue}
     * and {@link this#topWeightValue} with step {@link this#weightStep}.
     * @param g Graphics using to draw.
     * @param screenWidth width of {@link GraphPanel}
     * @param screenHeight height of {@link GraphPanel}
     */
    default void drawWeightScale(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(LIGHT_GRAY);
        g.fillRect(0, 0, scaleSize, screenHeight);
        Graphics2D g2d = ((Graphics2D) g);

        double tick = (screenHeight - scaleSize) / ((topWeightValue - bottomWeightValue) / weightStep);
        for (double i = 0;  i < (topWeightValue - bottomWeightValue) / weightStep + 1; i++) {
            int tHeight = (int) (screenHeight - 1 - scaleSize - tick * i);
            g2d.setPaint(TRANSPARENT_BLUE);
            g2d.drawLine(scaleSize, tHeight, screenWidth, tHeight);
            g2d.setPaint(DARK_GRAY);
            g2d.drawLine(0, tHeight, scaleSize, tHeight);
            g2d.drawString((bottomWeightValue + weightStep * i) + "", 0, tHeight + 13);
        }
    }

    /**
     * This method creates <code>Polygon</code> which is a filled graph. This chart contains measurements
     * from the period of the value of <code>days</code>, starting from <code>startDate</code>.
     * @param screenWidth width of {@link GraphPanel}
     * @param screenHeight height of {@link GraphPanel}
     * @param days Period of time
     * @param measurementPoints Map with measurements
     * @param startDate The beginning date of the period
     * @param space Space between scale and first point (from left).
     * @return Polygon that represents filled graph.
     */
    default Polygon getPolygon(int screenWidth, int screenHeight, int days, Map<Date, Double> measurementPoints, Calendar startDate, int space) {
        double tick = (screenWidth - scaleSize) / (double) days;
        double pxPerKg = (screenHeight - scaleSize) / (topWeightValue - bottomWeightValue);

        Polygon polygon = new Polygon();
        polygon.addPoint(scaleSize, screenHeight - scaleSize);

        int lastIt = 0;
        for (int i = 0; i < days; i++) {
            if (measurementPoints.get(startDate.getTime()) != null) {
                lastIt = i;
                polygon.addPoint(
                        (int) Math.round(scaleSize + space + tick * i),
                        (int) Math.round(screenHeight - scaleSize - (measurementPoints.get(startDate.getTime()) - bottomWeightValue) * pxPerKg));
            }
            startDate.add(Calendar.DAY_OF_WEEK, 1);
        }
        polygon.addPoint((int) Math.round(scaleSize + space + tick * lastIt), screenHeight - scaleSize);

        return polygon;
    }
}
