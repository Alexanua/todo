package org.campusmolndal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseHandler databaseHandler = new DatabaseHandler();


        UserService userService = new UserService(databaseHandler);
        UserMenu userMenu = new UserMenu(userService, new Scanner(System.in));
        userMenu.start();

        ToDoService toDoService = new ToDoService(databaseHandler);
        ToDoMenu toDoMenu = new ToDoMenu(toDoService);
        toDoMenu.start();




    }
}
