package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.company.References.*;

/**
 * This class is responsible for creating frame where you can choose or create user. It's start frame.
 */
public class UsersFrame extends JFrame {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JScrollPane usersScrollPane;
    private JPanel usersPanel;
    private JPanel newUserPanel;
    private JTextField nickTextField;
    private JButton addNewUserButton;
    private JLabel nickLabel;

    public UsersFrame() {
        super("Weight Tracker");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method initialize components.
     */
    private void initComponents() {
        DBTools dbTools = new DBTools();

        titleLabel.setFont(new Font(FONT, Font.BOLD, 40));
        titleLabel.setForeground(LIGHT_GRAY);

        nickTextField.setBackground(LIGHT_GRAY);
        nickTextField.setForeground(DARK_GRAY);
        nickTextField.setBorder(BorderFactory.createLineBorder(BLUE));
        nickTextField.setFont(new Font(FONT, Font.BOLD, 20));
        nickTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                addNewUserButton.doClick();
            }
        });

        nickLabel.setFont(new Font(FONT, Font.BOLD, 28));
        nickLabel.setForeground(LIGHT_GRAY);

        addNewUserButton.setBackground(BLUE);
        addNewUserButton.setForeground(DARK_GRAY);
        addNewUserButton.setFont(new Font(FONT, Font.BOLD, 20));

        mainPanel.setBackground(GRAY);
        usersPanel.setBackground(LIGHT_GRAY);
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        newUserPanel.setBackground(GRAY);

        usersScrollPane.setBorder(null);

        addNewUserButton.addActionListener(e -> {
            if (nickTextField.getText().length() == 0)
                JOptionPane.showMessageDialog(this, "Enter the nickname");
            else {
                dbTools.insertNewUser(nickTextField.getText());
                nickTextField.setText("");
                usersPanel.add(dbTools.getLastUser().getPanel());
                revalidate();
                repaint();
            }
        });

        for (User u: dbTools.getUsers())
            usersPanel.add(u.getPanel());
    }
}
