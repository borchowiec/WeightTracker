package com.company;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * This class contains methods that can operate on database.
 */
public class DBTools {

    /**
     * Fills db with measurements
     * @param args
     */
    public static void main(String[] args) {
        DBTools dbTools = new DBTools();

        Calendar calendar = DateTools.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.YEAR, 2019);

        Random rand = new Random();
        for (int i = 0; i < 365; i++) {
            dbTools.addMeasurement(new Measurement(1, calendar, 30 + rand.nextDouble() * 70));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * Constructor of <code>DBTools</code> class. Creates tables if doesn't exists.
     */
    public DBTools() {
        query("CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY, nick text NOT NULL);");
        query("CREATE TABLE IF NOT EXISTS measurements (id integer PRIMARY KEY, user integer, date text, weight real);");
    }

    /**
     * This method connects to database.
     * @return Connection to database.
     */
    private Connection connect() {
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * This method sends query to database. It doesn't return anything so it's useful if you want to edit data in
     * DB e.g. UPDATE.
     * @param sql Content of query.
     */
    private void query(String sql) {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method inserts new user to DB.
     * @param nick Nickname of new user.
     */
    public void insertNewUser(String nick) {
        String sql = "INSERT INTO users (nick) VALUES(?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nick);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the user who was added last
     * @return Last added user.
     */
    public User getLastUser() {
        String sql = "SELECT id, nick FROM users ORDER BY id DESC";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            return new User(rs.getInt("id"), rs.getString("nick"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all users from DB and returns it as a <code>List</code>
     * @return List of users
     */
    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        String sql = "SELECT id, nick FROM users";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next())
                users.add(new User(rs.getInt("id"), rs.getString("nick")));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    /**
     * Get specific measurement from DB.
     * @param userID Measurement user id.
     * @param date Date of measurement.
     * @return Specific measurement.
     */
    private Measurement getMeasurement(int userID, Calendar date) {
        String stringDate = DateTools.calendarToString(date);
        String sql = "SELECT user, date, weight FROM measurements WHERE user = " + userID + " AND date = '" + stringDate + "'";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            return new Measurement(rs.getInt(1), rs.getString(2), rs.getDouble(3));
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Add measurement to DB. If measurement with specific date exists, update that measurement in DB.
     * @param measurement Measurement that you want to add to DB.
     */
    public void addMeasurement(Measurement measurement) {
        //if measurement exists, insert it, else, update
        if (getMeasurement(measurement.getUserID(), measurement.getCalendar()) == null) {
            String sql = ("INSERT INTO measurements (user, date, weight) VALUES(?,?,?)");

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, measurement.getUserID());
                pstmt.setString(2, measurement.getDateInString());
                pstmt.setDouble(3, measurement.getWeight());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            query("UPDATE measurements SET weight = " + measurement.getWeight() + " WHERE user = " + measurement.getUserID() + " AND date = '" + measurement.getDateInString() + "'");
        }
    }

    /**
     * Delete user from DB and all gathered data about the user.
     * @param id ID of user you want to delete.
     */
    public void deleteUser(int id) {
        query("DELETE FROM measurements WHERE user = " + id);
        query("DELETE FROM users WHERE ID = " + id);
    }

    /**
     * Gets all user's measurements and puts it into <code>Map</code>.
     * @param user The user you want to get measurements from.
     * @return Map with user's measurements.
     */
    public Map<Date, Double> getMeasurementsMap(User user) {
        Map<Date, Double> map = new TreeMap<>();
        String sql = "SELECT date, weight FROM measurements WHERE user = " + user.ID;

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next())
                map.put(DateTools.StringToDate(rs.getString(1)), rs.getDouble(2));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return map;
    }
}
