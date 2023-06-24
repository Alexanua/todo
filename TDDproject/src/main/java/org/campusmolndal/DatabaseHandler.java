package org.campusmolndal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseHandler {
    private static final String url = "jdbc:sqlite:Alexanu.db";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            createTableIfNotExists(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createTableIfNotExists(Connection connection) {
        String userTableSql = "CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "age INTEGER," +
                "registerDate TEXT" +
                ")";

        String todoItemTableSql = "CREATE TABLE IF NOT EXISTS ToDoItem (" +
                "id INTEGER PRIMARY KEY," +
                "text TEXT," +
                "done BOOLEAN," +
                "assignedUserId INTEGER," +
                "category TEXT," +
                "assignedTo INTEGER" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(userTableSql);
            stmt.execute(todoItemTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Lägger till en ny användare i databasen.
     *
     * @param user Användaren att lägga till
     */
    public void insertUser(User user) {
        String sql = "INSERT INTO User(id, name, age, registerDate) VALUES(?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getRegisterDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // ... övriga metoder för att hantera användardata i databasen

    /**
     * Tar bort en användare från databasen baserat på användarid.
     *
     * @param userId Användarid för att ta bort användaren
     */
    public void deleteUser(int userId) {
        String sql = "DELETE FROM User WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createToDoItem(int id, String text, boolean done, int assignedUserId, String category) {
        String sql = "INSERT INTO ToDoItem(id, text, done, assignedUserId, category) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setString(2, text);
            statement.setBoolean(3, done);
            statement.setInt(4, assignedUserId);
            statement.setString(5, category);

            statement.executeUpdate();

            statement.close();
            connection.close();

            System.out.println("New item added successfully.");
        } catch (SQLException e) {
            System.out.println("Error: Failed to add new item.");
            e.printStackTrace();
        }
    }


    /**
     * Hämtar en användare från databasen baserat på användarid.
     *
     * @param userId Användarid för att hämta användaren
     * @return Användaren om den hittas, annars null
     */
    public User getUser(int userId) {
        String sql = "SELECT id, name, age, registerDate FROM User WHERE id = ?";
        User user = null;

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    LocalDate registerDate = LocalDate.parse(rs.getString("registerDate"));

                    user = new User(id, name, age, registerDate);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    /**
     * Uppdaterar en användares information i databasen.
     *
     * @param user Den uppdaterade användaren
     */
    public void updateUser(User user) {
        String sql = "UPDATE User SET name = ?, age = ?, registerDate = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setString(3, user.getRegisterDate().toString());
            pstmt.setInt(4, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
