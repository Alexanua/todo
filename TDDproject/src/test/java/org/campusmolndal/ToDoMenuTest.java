package org.campusmolndal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class ToDoMenuTest {
    private ToDoService toDoService;
    private ToDoMenu toDoMenu;

    @BeforeEach
    void setUp() {
        toDoService = mock(ToDoService.class);
        toDoMenu = new ToDoMenu(toDoService);
    }





    @Test
    void testMarkItemAsDone() {
        Scanner scanner = new Scanner("2\n1\n4\n");
        toDoMenu.setScanner(scanner);

        toDoMenu.start();

        verify(toDoService, times(1)).markToDoItemAsDone(1);
    }

    @Test
    void testDeleteItem() {
        Scanner scanner = new Scanner("3\n1\n4\n");
        toDoMenu.setScanner(scanner);

        toDoMenu.start();

        verify(toDoService, times(1)).deleteToDoItem(1);
    }
}
