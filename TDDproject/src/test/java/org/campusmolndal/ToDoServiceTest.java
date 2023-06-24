package org.campusmolndal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ToDoServiceTest {

    private ToDoService toDoService;
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

        // Skapa instansen av ToDoService med de beroende objekten
        toDoService = new ToDoService(databaseHandler);

        try {
            // Mocka beteendet för databashanteraren och anslutningen
            when(databaseHandler.connect()).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateToDoItem() {
        int id = 1;
        String text = "Buy groceries";
        boolean done = false;
        int assignedTo = 2;
        String category = "Grocery";

        // Anropa testmetoden
        toDoService.createToDoItem(id, text, done, assignedTo, category);

        try {
            // Verifiera att korrekta SQL-frågor utförs
            verify(preparedStatement).setInt(1, id);
            verify(preparedStatement).setString(2, text);
            verify(preparedStatement).setBoolean(3, done);
            verify(preparedStatement).setInt(4, assignedTo);
            verify(preparedStatement).setString(5, category);
            verify(preparedStatement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadToDoItem() throws SQLException {
        int itemId = 1;

        // Mocka ResultSet för att returnera testdata
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(itemId);
        when(resultSet.getString("text")).thenReturn("Buy groceries");
        when(resultSet.getBoolean("done")).thenReturn(false);
        when(resultSet.getInt("assignedTo")).thenReturn(2);
        when(resultSet.getString("category")).thenReturn("Grocery");

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

            // Skapa instansen av ToDoService med mockade objekt
            ToDoService toDoService = new ToDoService(databaseHandler);

            // Anropa testmetoden
            ToDoItem item = toDoService.readToDoItem(itemId);

            // Verifiera att korrekt ToDoItem skapas
            assertEquals(itemId, item.getId());
            assertEquals("Buy groceries", item.getText());
            assertFalse(item.isDone());
            assertEquals(2, item.getAssignedTo());
            assertEquals("Grocery", item.getCategory());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateToDoItem() {
        ToDoItem item = new ToDoItem(1, "Buy groceries", false, 2, "Grocery");

        // Mocka PreparedStatement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try {
            // Mocka Connection för att returnera PreparedStatement
            Connection connection = mock(Connection.class);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

            // Mocka DatabaseHandler för att returnera Connection
            DatabaseHandler databaseHandler = mock(DatabaseHandler.class);
            when(databaseHandler.connect()).thenReturn(connection);

            // Skapa instansen av ToDoService med mockade objekt
            ToDoService toDoService = new ToDoService(databaseHandler);

            // Anropa testmetoden
            toDoService.updateToDoItem(item);

            // Verifiera att korrekt SQL-fråga utförs
            verify(preparedStatement).setString(1, item.getText());
            verify(preparedStatement).setBoolean(2, item.isDone());
            verify(preparedStatement).setInt(3, item.getAssignedTo());
            verify(preparedStatement).setString(4, item.getCategory());
            verify(preparedStatement).setInt(5, item.getId());
            verify(preparedStatement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteToDoItem() {
        int itemId = 1;

        // Mocka PreparedStatement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try {
            // Mocka Connection för att returnera PreparedStatement
            Connection connection = mock(Connection.class);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

            // Mocka DatabaseHandler för att returnera Connection
            DatabaseHandler databaseHandler = mock(DatabaseHandler.class);
            when(databaseHandler.connect()).thenReturn(connection);

            // Skapa instansen av ToDoService med mockade objekt
            ToDoService toDoService = new ToDoService(databaseHandler);

            // Anropa testmetoden
            toDoService.deleteToDoItem(itemId);

            // Verifiera att korrekt SQL-fråga utförs
            verify(preparedStatement).setInt(1, itemId);
            verify(preparedStatement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
