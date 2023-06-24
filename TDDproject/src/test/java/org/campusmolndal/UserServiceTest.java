package org.campusmolndal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userService;
    private DatabaseHandler databaseHandler;
    private Connection connection;
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() {
        // Skapa en separat testdatabasfil
        String dbFilePath = "path/to/testdatabase.db";

        // Skapa instanser av de beroende objekten
        databaseHandler = Mockito.mock(DatabaseHandler.class);
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);

        // Skapa instansen av UserService med de beroende objekten
        userService = new UserService(databaseHandler);

        try {
            // Mocka beteendet för databashanteraren och anslutningen
            when(databaseHandler.connect()).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRegisterUser() {
        int id = 1;
        String name = "Alex";
        int age = 36;
        LocalDate registerDate = LocalDate.now();

        User user = new User(id, name, age, registerDate);

        // Anropa testmetoden
        userService.registerUser(user);

        try {
            // Verifiera att korrekt SQL-fråga utförs
            verify(preparedStatement).setInt(1, id);
            verify(preparedStatement).setString(2, name);
            verify(preparedStatement).setInt(3, age);
            verify(preparedStatement).setString(4, registerDate.toString());
            verify(preparedStatement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteUser() {
        int userId = 1;

        // Anropa testmetoden
        userService.deleteUser(userId);

        try {
            // Verifiera att korrekt SQL-fråga utförs
            verify(preparedStatement).setInt(1, userId);
            verify(preparedStatement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetUser() throws SQLException {
        int userId = 1;

        // Mocka ResultSet för att returnera testdata
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(userId);
        when(resultSet.getString("name")).thenReturn("Alex");
        when(resultSet.getInt("age")).thenReturn(36);
        when(resultSet.getString("registerDate")).thenReturn(LocalDate.now().toString());

        // Mocka PreparedStatement för att returnera ResultSet
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        try {
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // Mocka Connection för att returnera PreparedStatement
            Connection connection = mock(Connection.class);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

            // Mocka DatabaseHandler för att returnera Connection
            DatabaseHandler databaseHandler = mock(DatabaseHandler.class);
            when(databaseHandler.connect()).thenReturn(connection);

            // Skapa instansen av UserService med mockade objekt
            UserService userService = new UserService(databaseHandler);

            // Anropa testmetoden
            User user = userService.getUser(userId);

            // Verifiera att korrekt User skapas
            assertEquals(userId, user.getId());
            assertEquals("Alex", user.getName());
            assertEquals(36, user.getAge());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateUser() {
        int userId = 1;
        String newName = "John";
        int newAge = 40;
        LocalDate newRegisterDate = LocalDate.now();

        User user = new User(userId, newName, newAge, newRegisterDate);

        // Anropa testmetoden
        userService.updateUser(user);

        try {
            // Verifiera att korrekt SQL-fråga utförs
            verify(preparedStatement).setString(1, newName);
            verify(preparedStatement).setInt(2, newAge);
            verify(preparedStatement).setString(3, newRegisterDate.toString());
            verify(preparedStatement).setInt(4, userId);
            verify(preparedStatement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
