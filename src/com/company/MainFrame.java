package com.company;

import com.company.graph.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.company.References.*;

/**
 * This class is responsible for creating main frame. This frame contains {@link GraphPanel} and and components which can add
 * new measurement point.
 */
public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel graphContainer;
    private JPanel inputDataPanel;
    private JSpinner dateSpinner;
    private JSpinner weightSpinner;
    private JButton addButton;
    private JPanel timePanel;
    private JRadioButton weekRadioButton;
    private JRadioButton monthRadioButton;
    private JRadioButton yearRadioButton;

    private GraphPanel graph;
    private User user;
    private DBTools dbTools;

    public MainFrame(User user, DBTools dbTools) {
        super("Weight Tracker");

        this.user = user;
        this.dbTools = dbTools;

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graph = new GraphPanel(user, dbTools);
        initComponents();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method initialize components.
     */
    private void initComponents() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(DARK_GRAY);

        JButton changeUser = new JButton("Change user");
        setLook(changeUser);
        changeUser.addActionListener(e -> {
            new UsersFrame();
            this.dispose();
        });

        JLabel msgLabel = new JLabel("  Hello " + user.getNick() + "!");
        msgLabel.setForeground(BLUE);
        msgLabel.setFont(new Font(FONT, Font.ITALIC, 28));

        topPanel.add(changeUser, BorderLayout.EAST);
        topPanel.add(msgLabel, BorderLayout.WEST);
        graphContainer.add(topPanel, BorderLayout.NORTH);

        mainPanel.setBackground(DARK_GRAY);
        inputDataPanel.setBackground(DARK_GRAY);
        timePanel.setBackground(DARK_GRAY);

        dateSpinner.setModel(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, new SimpleDateFormat(" dd.MM.yyyy").toPattern()));
        weightSpinner.setModel(new SpinnerNumberModel(80.0, 1.0, Short.MAX_VALUE, 1.0));

        setLook(dateSpinner);
        setLook(weightSpinner);
        setLook(addButton);

        setRadio(weekRadioButton, new WeekDrawable());
        setRadio(monthRadioButton, new MonthDrawable());
        setRadio(yearRadioButton, new YearDrawable());

        graphContainer.add(graph, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) dateSpinner.getValue());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.clear(Calendar.MINUTE);
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MILLISECOND);
            graph.addMeasurementPoint(calendar.getTime(), (double) weightSpinner.getValue());
            dbTools.addMeasurement(new Measurement(user.ID, calendar, (double) weightSpinner.getValue()));
        });
    }

    /**
     * Set look and functionalities for radio. This radio will be sending {@link Drawable} implementation to {@link GraphPanel}.
     * @param radio Radio you want to set up.
     * @param drawable Drawable implementation you want to send to {@link GraphPanel}.
     */
    private void setRadio(JRadioButton radio, Drawable drawable) {
        radio.setBackground(DARK_GRAY);
        radio.setFont(new Font(FONT, Font.BOLD, 20));
        radio.setForeground(LIGHT_GRAY);
        radio.addActionListener(e -> graph.setDrawable(drawable));
    }

    /**
     * Set look of spinner.
     * @param spinner Spinner you want to set up.
     */
    private void setLook(JSpinner spinner) {
        spinner.getEditor().getComponent(0).setBackground(LIGHT_GRAY);
        spinner.getEditor().getComponent(0).setForeground(DARK_GRAY);
        spinner.setBorder(BorderFactory.createLineBorder(BLUE));
        spinner.setFont(new Font(FONT, Font.BOLD, 20));
    }

    /**
     * Set look of button.
     * @param addButton Button you want to set up.
     */
    private void setLook(JButton addButton) {
        addButton.setBackground(BLUE);
        addButton.setForeground(DARK_GRAY);
        addButton.setFont(new Font(FONT, Font.BOLD, 20));
    }
}
