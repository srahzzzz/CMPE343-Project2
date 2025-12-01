package menu;

import model.User;
import java.util.Scanner;

/**
 * Base abstract class for all role-based menus.
 * Provides common functionality like displaying user info and handling logout.
 * author sarah nauman
 */
public abstract class BaseMenu {
    protected User currentUser;
    protected Scanner scanner;

    /**
     * Constructor for BaseMenu.
     *
     * @param user the currently logged-in user
     */
    public BaseMenu(User user) {
        this.currentUser = user;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the menu header with user information.
     */
    protected void displayHeader() {
        System.out.println("\n=======================================");
        System.out.printf("   Welcome, %s %s (%s)%n", 
            currentUser.getName(), 
            currentUser.getSurname(), 
            currentUser.getRole());
        System.out.println("=======================================\n");
    }

    /**
     * Displays the menu options. Must be implemented by each role.
     */
    protected abstract void displayMenu();

    /**
     * Handles menu option selection. Must be implemented by each role.
     */
    protected abstract void handleOption(int choice);

    /**
     * Main menu loop that runs until user logs out or explicitly terminates the program.
     *
     * <p>When the user selects the logout option (default: 0), they are asked whether they
     * want to terminate the entire application. Answering "yes" exits the prrogam, any other
     * answer logs out and returns control to the login screen.</p>
     */
    public void run() {
        boolean running = true;
        while (running) {
            displayHeader();
            displayMenu();
            System.out.print("\nEnter your choice: ");

            try {
                String input = scanner.nextLine();
                Integer choice = safeParseInt(input);

                if (choice == null) {
                    System.out.println("\nInvalid input! Please enter a valid number.");
                    continue;
                }

                if (choice == getLogoutOption()) {
                    String confirm;
                    while (true) {
                        System.out.print("\nDo you want to terminate the program? (y/n): ");
                        confirm = scanner.nextLine().trim().toLowerCase();

                        if (confirm.equals("y") || confirm.equals("n")) {
                            break;
                        }

                        System.out.println("Invalid choice. Please enter 'y' or 'n'.");
                    }

                    if (confirm.equals("y")) {
                        System.out.println("\nTerminating program... Goodbye!");
                        System.exit(0);
                    } else {
                        System.out.println("\nLogging out... Returning to login screen...");
                        running = false; // leave menu and go back to login loop
                    }
                } else {
                    handleOption(choice);
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }
        }
    }

    /**
     * Safely parses a string to an Integer, handling invalid inputs gracefully.
     *
     * @param input the string to parse
     * @return the parsed Integer, or null if input is invalid
     */
    // hasti
    // invalid input check
    public static Integer safeParseInt(String input) {
        if (input == null) return null;

        input = input.trim();

        // Reject empty input
        if (input.isEmpty()) return null;

        // Optional leading + or -
        int start = 0;
        if (input.charAt(0) == '+' || input.charAt(0) == '-') {
            if (input.length() == 1) return null; // string is just "+" or "-"
            start = 1;
        }

        // Check all remaining chars are digits
        for (int i = start; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) return null;
        }

        // Handle overflow/underflow manually
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null; // too big, too small, or invalid
        }
    }

    /**
     * Returns the menu option number for logout.
     * Can be overridden by subclasses if needed.
     *
     * @return the logout option number
     */
    protected int getLogoutOption() {
        return 0; // Default logout option
    }
}

