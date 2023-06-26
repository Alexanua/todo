package org.campusmolndal;

import java.util.Scanner;

import java.util.Scanner;

public class ToDoMenu {
    private static final int MENU_EXIT = 4;

    private Scanner scanner;
    private final ToDoService toDoService;

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ToDoMenu(ToDoService toDoService) {
        this.toDoService = toDoService;
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            displayMenu();
            choice = readChoice();
            executeChoice(choice);
        } while (choice != MENU_EXIT);
    }

    private void displayMenu() {
        System.out.println("\nTO-DO MENU:");
        System.out.println("1. Add New Item");
        System.out.println("2. Mark Item as Done");
        System.out.println("3. Delete Item");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    private int readChoice() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a valid number.");
            return -1;
        }
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 1:
                addItem();
                break;
            case 2:
                markItemAsDone();
                break;
            case 3:
                deleteItem();
                break;
            case MENU_EXIT:
                System.out.println("Exiting ToDo Menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void addItem() {
        try {
            System.out.print("Enter item ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter item text: ");
            String text = scanner.nextLine();

            System.out.print("Is the item done? (true/false): ");
            boolean done = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Enter assigned user ID: ");
            int assignedUserId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter item category: ");
            String category = scanner.nextLine();

            toDoService.createToDoItem(id, text, done,  assignedUserId , category);
            System.out.println("New item added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void markItemAsDone() {
        try {
            System.out.print("Enter item ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            toDoService.markToDoItemAsDone(id);
            System.out.println("Item marked as done.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void deleteItem() {
        try {
            System.out.print("Enter item ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            toDoService.deleteToDoItem(id);
            System.out.println("Item deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}
