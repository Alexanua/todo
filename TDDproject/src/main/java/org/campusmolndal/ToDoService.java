package org.campusmolndal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoService {
    private Connection connection;
    private DatabaseHandler databaseHandler;

    public ToDoService(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public void createToDoItemTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ToDoItem (" +
                "id INTEGER PRIMARY KEY," +
                "text TEXT," +
                "done BOOLEAN," +
                "assignedTo INTEGER," +
                "category TEXT" +
                ")";

        try (Connection connection = databaseHandler.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating ToDoItem table: " + e.getMessage());
        }
    }

    public void createToDoItem(int id, String text, boolean done, int assignedUserId, String category) {
        String sql = "INSERT INTO ToDoItem(id, text, done, assignedUserId, category) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, text);
            pstmt.setBoolean(3, done);
            pstmt.setInt(4, assignedUserId);
            pstmt.setString(5, category);

            pstmt.executeUpdate();
            System.out.println("New item added successfully.");
        } catch (SQLException e) {
            System.out.println("Error: Failed to add new item.");
            e.printStackTrace();
        }
    }

    public ToDoItem readToDoItem(int itemId) {
        String sql = "SELECT id, text, done, assignedTo, category FROM ToDoItem WHERE id = ?";
        ToDoItem item = null;

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = new ToDoItem(rs.getInt("id"), rs.getString("text"), rs.getBoolean("done"),
                            rs.getInt("assignedTo"), rs.getString("category"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    public void updateToDoItem(ToDoItem item) {
        String sql = "UPDATE ToDoItem SET text = ?, done = ?, assignedTo = ?, category = ? WHERE id = ?";

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, item.getText());
            pstmt.setBoolean(2, item.isDone());
            pstmt.setInt(3, item.getAssignedTo());
            pstmt.setString(4, item.getCategory());
            pstmt.setInt(5, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteToDoItem(int itemId) {
        String sql = "DELETE FROM ToDoItem WHERE id = ?";

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ToDoItem> getAllToDoItems() {
        String sql = "SELECT id, text, done, assignedTo, category FROM ToDoItem";
        List<ToDoItem> items = new ArrayList<>();

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ToDoItem item = new ToDoItem(rs.getInt("id"), rs.getString("text"), rs.getBoolean("done"),
                        rs.getInt("assignedTo"), rs.getString("category"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public void markToDoItemAsDone(int id) {
        String sql = "UPDATE ToDoItem SET done = ? WHERE id = ?";

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setBoolean(1, true);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("ToDoItem marked as done.");
        } catch (SQLException e) {
            System.out.println("Error marking ToDoItem as done.");
            e.printStackTrace();
        }
    }

}
