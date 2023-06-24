package org.campusmolndal;// UserService.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserService {
    private DatabaseHandler databaseHandler;

    public UserService(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    /**
     * Registrerar en ny användare i databasen.
     *
     * @param user Användaren att registrera
     */
    public void registerUser(User user) {
        String sql = "INSERT INTO User(id, name, age, registerDate) VALUES(?, ?, ?, ?)";

        try (Connection connection = databaseHandler.connect();
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

    /**
     * Tar bort en användare från databasen baserat på användarid.
     *
     * @param userId Användarid för att ta bort användaren
     */
    public void deleteUser(int userId) {
        String sql = "DELETE FROM User WHERE id = ?";

        try (Connection connection = databaseHandler.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

        try (Connection connection = databaseHandler.connect();
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

        try (Connection connection = databaseHandler.connect();
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
