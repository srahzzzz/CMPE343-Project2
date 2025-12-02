package menu;

import model.User;
import util.ColorUtils;
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
        String roleColor = getRoleHeaderColor();
        System.out.println("\n" + ColorUtils.colorize("=======================================", roleColor));
        System.out.printf(ColorUtils.colorize("   Welcome, %s %s%n", roleColor),
            currentUser.getName(),
            currentUser.getSurname());
        System.out.println(ColorUtils.colorize("=======================================\n", roleColor));
    }
    
    /**
     * Gets the role-specific header color.
     * @return ANSI color code for the role
     */
    protected String getRoleHeaderColor() {
        String role = currentUser.getRole();
        switch (role) {
            case "Tester": return ColorUtils.BOLD + ColorUtils.BRIGHT_CYAN;
            case "Junior": return ColorUtils.BOLD + ColorUtils.BRIGHT_MAGENTA;
            case "Senior": return ColorUtils.BOLD + ColorUtils.BRIGHT_GREEN;
            case "Manager": return ColorUtils.BOLD + ColorUtils.BRIGHT_YELLOW;
            default: return ColorUtils.BOLD + ColorUtils.BRIGHT_CYAN;
        }
    }
    
    /**
     * Gets the role-specific prompt color.
     * @return ANSI color code for prompts
     */
    protected String getRolePromptColor() {
        String role = currentUser.getRole();
        switch (role) {
            case "Tester": return ColorUtils.BRIGHT_CYAN;
            case "Junior": return ColorUtils.BRIGHT_MAGENTA;
            case "Senior": return ColorUtils.BRIGHT_GREEN;
            case "Manager": return ColorUtils.BRIGHT_YELLOW;
            default: return ColorUtils.BRIGHT_BLUE;
        }
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
     * @author sarah nauman
     */
    public void run() {
        boolean running = true;
        while (running) {
            displayHeader();
            displayMenu();
            System.out.print("\n" + ColorUtils.colorize("Enter your choice: ", getRolePromptColor()));

            try {
                String input = scanner.nextLine();
                Integer choice = safeParseInt(input);

                if (choice == null) {
                    System.out.println("\n" + ColorUtils.error("Invalid input! Please enter a valid number."));
                    continue;
                }

                if (choice == getLogoutOption()) {
                    String confirm;
                    while (true) {
                        System.out.print("\n" + ColorUtils.colorize("Do you want to terminate the program? (y/n): ", getRolePromptColor()));
                        confirm = scanner.nextLine().trim().toLowerCase();

                        if (confirm.equals("y") || confirm.equals("n")) {
                            break;
                        }

                        System.out.println(ColorUtils.error("Invalid choice. Please enter 'y' or 'n'."));
                    }

                    if (confirm.equals("y")) {
                        System.out.println("\n" + ColorUtils.info("Terminating program... Goodbye!"));
                        System.exit(0);
                    } else {
                        System.out.println("\n" + ColorUtils.info("Logging out... Returning to login screen..."));
                        running = false; // leave menu and go back to login loop
                    }
                } else {
                    handleOption(choice);
                }
            } catch (Exception e) {
                System.out.println("\n" + ColorUtils.error("Error: " + e.getMessage()));
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

    /**
     * Waits for the user to press Enter (and only Enter).
     * Ignores any other input (Tab, spaces, characters, etc.) and only proceeds
     * when Enter is pressed with no other characters.
     *
     * @author sarah nauman
     */
    protected void waitForEnter() {
        String input;
        do {
            input = scanner.nextLine();
            // Only proceed if input is empty (just Enter was pressed)
            if (input.trim().isEmpty()) {
                break; // Empty input = Enter was pressed, proceed
            }
            // Non-empty input (Tab, spaces, characters, etc.), ignore and wait again
        } while (true);
    }
}

