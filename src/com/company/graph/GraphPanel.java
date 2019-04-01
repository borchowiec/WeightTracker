package com.company.graph;

import com.company.DBTools;
import com.company.User;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Map;

import static com.company.References.GRAY;

/**
 * This class is responsible for drawing weight graphs over various time periods.
 */
public class GraphPanel extends JPanel {
    private Map<Date, Double> measurementPoints;
    private Drawable drawable = new WeekDrawable();

    /**
     * Main constructor of <code>GraphPanel</code>
     * @param user The user for whom the charts will be drawn.
     * @param dbTools {@link DBTools}
     */
    public GraphPanel(User user, DBTools dbTools) {
        this.setPreferredSize(new Dimension(1000, 700));
        measurementPoints = dbTools.getMeasurementsMap(user);
        repaint();
    }

    /**
     * This method adds new measurement point to graph.
     * @param date Date of measurement.
     * @param weight Result of measurement.
     */
    public void addMeasurementPoint(Date date, Double weight) {
        measurementPoints.put(date, weight);
        repaint();
    }

    /**
     * This method set class that paints graph.
     * @param drawable {@link Drawable} interface
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //bg
        g.setColor(GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //graph
        drawable.drawPoints(measurementPoints, g, this.getWidth(), this.getHeight());
        drawable.drawDateScale(g, this.getWidth(), this.getHeight());
        drawable.drawWeightScale(g, this.getWidth(), this.getHeight());
    }
}
