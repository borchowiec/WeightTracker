package com.company;

import javax.swing.*;
import java.awt.*;

import static com.company.References.*;
import static com.company.References.FONT;

/**
 * This class represents user.
 */
public class User {
    final public int ID;
    private String nick;

    /**
     * Constructor of <code>User</code> class.
     * @param ID ID of user.
     * @param nick User's nickname.
     */
    public User(int ID, String nick) {
        this.ID = ID;
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    /**
     * This method prepares and returns <code>JPanel</code> which represent user in {@link UsersFrame}.
     * @return Panel of user.
     */
    public JPanel getPanel() {
        DBTools dbTools = new DBTools();

        JPanel panel = new JPanel();
        panel.setBackground(LIGHT_GRAY);

        JButton nickButton = new JButton(nick);
        nickButton.setBackground(BLUE);
        nickButton.setForeground(DARK_GRAY);
        nickButton.setFont(new Font(FONT, Font.BOLD, 20));
        nickButton.addActionListener(e -> {
            new MainFrame(this, dbTools);
            ((JFrame) SwingUtilities.getRoot(panel)).dispose();
        });

        JButton removeButton = new JButton("X");
        removeButton.setBackground(RED);
        removeButton.setForeground(DARK_RED);
        removeButton.setFont(new Font(FONT, Font.BOLD, 20));
        removeButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), "Are you sure you want to delete this user and all user data?");
            if (option == JOptionPane.YES_OPTION) {
                dbTools.deleteUser(ID);
                JPanel parent = (JPanel) panel.getParent();
                parent.remove(panel);
                parent.repaint();
                parent.revalidate();
            }
        });

        panel.add(nickButton);
        panel.add(removeButton);

        return panel;
    }
}
