package menu;

import dao.ContactDAO;
import dao.UndoOperationDAO;
import dao.UserDAO;
import model.UndoOperation;
import model.User;
import runninghorse.HorseFrames;
import runninghorse.RunningHorseAnimator;
import service.UndoService;
import util.ColorUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
        // Initialize Scanner with UTF-8 encoding to properly handle Turkish characters
        this.scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
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
                        // Play colorful 3D ASCII donut animation for ~5 seconds, then terminate.
                        try {
                            util.DonutAnimation.play(5000);
                        } catch (Exception e) {
                            // If anything goes wrong with the animation
                        }
                        System.exit(0);
                    } else {
                        // Clear screen immediately on logout - nothing should remain above
                        clearConsole();
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

    /**
     * Clears the console screen completely - AGGRESSIVE WIPE.
     * Uses Windows native cls command for maximum effectiveness on Windows.
     * Ensures nothing remains above after logout.
     */
    protected void clearConsole() {
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");
        
        try {
            // Windows native cls via Runtime
            if (isWindows) {
                try {
                    Runtime.getRuntime().exec("cmd /c cls").waitFor();
                    System.out.flush();
                } catch (Exception e) {
                    // Continue with other methods if cls fails
                }
            }
            
            //  ANSI escape codes - clear entire screen
            System.out.print("\u001B[2J");
            System.out.flush();
            
            // Move cursor to home position (top-left)
            System.out.print("\u001B[H");
            System.out.flush();
            
            // Clear scrollback buffer (if terminal supports it)
            System.out.print("\u001B[3J");
            System.out.flush();
            
            // Nuclear option - print 200 blank lines to push everything off screen
            for (int i = 0; i < 200; i++) {
                System.out.println();
            }
            System.out.flush();
            
            //  Final ANSI clear sequence (double-tap for certainty)
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
            
            //One more Windows cls if on Windows (triple-tap for maximum effect)
            if (isWindows) {
                try {
                    Runtime.getRuntime().exec("cmd /c cls").waitFor();
                    System.out.flush();
                } catch (Exception e) {
                    // Ignore
                }
            }
            
            // METHOD 8: Final blank line push to ensure everything is scrolled off
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.flush();
            
            // Absolute final ANSI clear to ensure cursor at top
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
            
        } catch (Exception e) {
            //  Maximum blank lines
            for (int i = 0; i < 500; i++) {
                System.out.println();
            }
            System.out.flush();
        }
    }
    
    /**
     * Displays undo operations table and allows user to select which operation to undo.
     * Shows all available operations with operation number, type, entity info, and timestamp.
     * 
     * @param undoService the UndoService instance to use
     */
    protected void handleUndoOperation(UndoService undoService) {
        System.out.println("\n" + ColorUtils.header("--- Undo Last Operation ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        List<UndoOperation> operations = undoService.getAllOperations();
        
        if (operations.isEmpty()) {
            System.out.println(ColorUtils.warning("No operations available to undo."));
            System.out.println("\n" + ColorUtils.colorize("Press Enter to continue...", getRolePromptColor()));
            waitForEnter();
            return;
        }
        
        // Display operations table
        displayUndoOperationsTable(operations);
        
        // Get operation selection with strong validation
        System.out.print("\n" + ColorUtils.colorize("Enter operation number to undo (1-" + operations.size() + "), or 0 to cancel: ", getRolePromptColor()));
        String input = scanner.nextLine().trim();
        Integer choice = safeParseInt(input);
        
        // Strong validation: must be valid integer, within range, and not null
        if (choice == null) {
            System.out.println(ColorUtils.error("Invalid input! Please enter a valid number."));
            System.out.println("\n" + ColorUtils.colorize("Press Enter to continue...", getRolePromptColor()));
            waitForEnter();
            return;
        }
        
        if (choice == 0) {
            System.out.println(ColorUtils.info("Operation cancelled."));
            System.out.println("\n" + ColorUtils.colorize("Press Enter to continue...", getRolePromptColor()));
            waitForEnter();
            return;
        }
        
        if (choice < 1 || choice > operations.size()) {
            System.out.println(ColorUtils.error("Invalid operation number! Please enter a number between 1 and " + operations.size() + "."));
            System.out.println("\n" + ColorUtils.colorize("Press Enter to continue...", getRolePromptColor()));
            waitForEnter();
            return;
        }
        
        // Get the selected operation (operations are ordered newest first, so index is choice - 1)
        UndoOperation selectedOperation = operations.get(choice - 1);
        
        // Confirm before undoing
        System.out.print("\n" + ColorUtils.colorize("Are you sure you want to undo this operation? (y/n): ", getRolePromptColor()));
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println(ColorUtils.info("Operation cancelled."));
            System.out.println("\n" + ColorUtils.colorize("Press Enter to continue...", getRolePromptColor()));
            waitForEnter();
            return;
        }
        
        // Execute undo
        boolean success = undoService.undoOperation(selectedOperation.getOperationId());
        
        if (success) {
            System.out.println(ColorUtils.success("Operation undone successfully!"));
        } else {
            System.out.println(ColorUtils.error("Failed to undo operation. The operation may have already been undone or the entity no longer exists."));
        }
        
        System.out.println("\n" + ColorUtils.colorize("Press Enter to continue...", getRolePromptColor()));
        waitForEnter();
    }
    
    /**
     * Displays undo operations in a formatted table.
     * Shows operation number, type, entity type, entity ID, and timestamp.
     * 
     * @param operations the list of operations to display
     */
    private void displayUndoOperationsTable(List<UndoOperation> operations) {
        if (operations.isEmpty()) {
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Table headers
        String[] headers = {"#", "Operation", "Entity Type", "Entity ID", "Timestamp"};
        int[] columnWidths = {3, 10, 12, 10, 19};
        
        // Build separator line
        StringBuilder separator = new StringBuilder();
        for (int width : columnWidths) {
            separator.append("+");
            for (int j = 0; j < width + 1; j++) {
                separator.append("-");
            }
        }
        separator.append("+");
        
        // Display header
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < headers.length; i++) {
            headerLine.append("|");
            headerLine.append(String.format("%-" + columnWidths[i] + "s", headers[i]));
        }
        headerLine.append("|");
        
        System.out.println(ColorUtils.colorize(separator.toString(), ColorUtils.CYAN));
        System.out.println(ColorUtils.header(headerLine.toString()));
        System.out.println(ColorUtils.colorize(separator.toString(), ColorUtils.CYAN));
        
        // Display rows
        for (int i = 0; i < operations.size(); i++) {
            UndoOperation op = operations.get(i);
            StringBuilder rowLine = new StringBuilder();
            
            // Operation number (1-based for user display)
            rowLine.append("|");
            rowLine.append(String.format("%-" + columnWidths[0] + "s", (i + 1)));
            
            // Operation type
            rowLine.append("|");
            rowLine.append(String.format("%-" + columnWidths[1] + "s", op.getOperationType().name()));
            
            // Entity type
            rowLine.append("|");
            rowLine.append(String.format("%-" + columnWidths[2] + "s", op.getEntityType().name()));
            
            // Entity ID
            rowLine.append("|");
            rowLine.append(String.format("%-" + columnWidths[3] + "s", op.getEntityId()));
            
            // Timestamp
            rowLine.append("|");
            String timestamp = op.getOperationTimestamp() != null ? 
                op.getOperationTimestamp().format(formatter) : "N/A";
            rowLine.append(String.format("%-" + columnWidths[4] + "s", timestamp));
            
            rowLine.append("|");
            System.out.println(rowLine.toString());
        }
        
        System.out.println(ColorUtils.colorize(separator.toString(), ColorUtils.CYAN));
    }
}

