package org.campusmolndal;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    private UserService userService;
    private Scanner scanner;

    public Menu() {
        userService = new UserService(new DatabaseHandler());
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to ToDo List!");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMENU:");
            System.out.println("1. Register User");
            System.out.println("2. Delete User");
            System.out.println("3. Get User");
            System.out.println("4. Update User");
            System.out.println("5. Exit");

            System.out.println("Enter your choice (1-5):");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    getUser();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    public void registerUser() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter user name:");
        String name = scanner.nextLine();

        System.out.println("Enter user age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(id, name, age, LocalDate.now());
        userService.registerUser(user);

        System.out.println("User registered successfully.");
    }

    public void deleteUser() {
        System.out.println("Enter user ID:");
        int userId = scanner.nextInt();
        scanner.nextLine();

        userService.deleteUser(userId);

        System.out.println("User deleted successfully.");
    }

    public void getUser() {
        System.out.println("Enter user ID:");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userService.getUser(userId);

        if (user != null) {
            System.out.println("User details:");
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    public void updateUser() {
        System.out.println("Enter user ID:");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter updated name:");
        String name = scanner.nextLine();

        System.out.println("Enter updated age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(userId, name, age, LocalDate.now());
        userService.updateUser(user);

        System.out.println("User updated successfully.");
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}
