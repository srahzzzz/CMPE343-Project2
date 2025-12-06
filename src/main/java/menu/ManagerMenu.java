package menu;

import dao.ContactDAO;
import dao.UndoOperationDAO;
import dao.UserDAO;
import model.User;
import service.AuthService;
import service.UndoService;
import service.ValidationUtils;
import util.ColorUtils;

import java.util.List;

/**
 * Menu for Manager role.
 * 
 * <p>Managers can:
 * - Change password
 * - Logout
 * - View contacts statistical info
 * - List all users
 * - Update existing user
 * - Add/employ new user
 * - Delete/fire existing user
 *  @author sarah nauman
 */
public class ManagerMenu extends BaseMenu {

    private final UserDAO userDAO;
    private final ContactDAO contactDAO;
    private final UndoService undoService;

    /**
     * Constructor for ManagerMenu.
     *
     * @param user the logged-in manager user
     */
    public ManagerMenu(User user) {
        super(user);
        this.userDAO = new UserDAO();
        this.contactDAO = new ContactDAO();
        this.undoService = new UndoService(new UndoOperationDAO(), contactDAO, userDAO);
    }

    @Override
    protected void displayMenu() {
        System.out.println(ColorUtils.managerMenuOption("1. Statistical info of Contacts"));
        System.out.println(ColorUtils.managerMenuOption("2. List all users"));
        System.out.println(ColorUtils.managerMenuOption("3. Update user"));
        System.out.println(ColorUtils.managerMenuOption("4. Add new user"));
        System.out.println(ColorUtils.managerMenuOption("5. Fire user"));
        System.out.println(ColorUtils.managerMenuOption("6. Delete user"));
        System.out.println(ColorUtils.managerMenuOption("7. Undo last operation"));
        System.out.println(ColorUtils.managerMenuOption("8. Change password"));
        System.out.println(ColorUtils.managerMenuOption("0. Logout"));
    }

    @Override
    protected void handleOption(int choice) {
        switch (choice) {
            case 1:
                showStatistics();
                break;
            case 2:
                listAllUsers();
                break;
            case 3:
                updateUser();
                break;
            case 4:
                addUser();
                break;
            case 5:
                fireUser();
                break;
            case 6:
                deleteUser();
                break;
            case 7:
                handleUndoOperation(undoService);
                break;
            case 8:
                changePassword();
                break;
            default:
                System.out.println("\n" + ColorUtils.error("Invalid option! Please try again."));
        }
    }

    /**
     * Displays contacts statistical information.
     * Shows various statistics about contacts including counts, names, ages, and LinkedIn presence.
     */
    private void showStatistics() {
        System.out.println("\n" + ColorUtils.header("--- Statistical Information of Contacts---"));
        System.out.println(ColorUtils.header("======================================="));
        
        java.util.Map<String, Object> stats = contactDAO.getStatistics();
        
        if (stats.isEmpty()) {
            System.out.println(ColorUtils.error("Unable to retrieve statistics. Please check database connection."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Total contacts
        if (stats.containsKey("total_contacts")) {
            System.out.println("\n" + ColorUtils.info("Total Contacts: " + stats.get("total_contacts")));
        }
        
        // LinkedIn statistics
        if (stats.containsKey("contacts_with_linkedin") && stats.containsKey("contacts_without_linkedin")) {
            System.out.println("\n" + ColorUtils.header("LinkedIn Statistics:"));
            System.out.println(ColorUtils.info("  - Contacts with LinkedIn: " + stats.get("contacts_with_linkedin")));
            System.out.println(ColorUtils.info("  - Contacts without LinkedIn: " + stats.get("contacts_without_linkedin")));
        }
        
        // Most common first name
        if (stats.containsKey("most_common_first_name") && stats.containsKey("most_common_first_name_count")) {
            System.out.println("\n" + ColorUtils.header("Most Common First Name:"));
            System.out.println(ColorUtils.info("  - Name: " + stats.get("most_common_first_name")));
            System.out.println(ColorUtils.info("  - Count: " + stats.get("most_common_first_name_count") + " contact(s)"));
        }
        
        // Most common last name
        if (stats.containsKey("most_common_last_name") && stats.containsKey("most_common_last_name_count")) {
            System.out.println("\n" + ColorUtils.header("Most Common Last Name:"));
            System.out.println(ColorUtils.info("  - Name: " + stats.get("most_common_last_name")));
            System.out.println(ColorUtils.info("  - Count: " + stats.get("most_common_last_name_count") + " contact(s)"));
        }
        
        // Age statistics
        if (stats.containsKey("youngest_contact") && stats.containsKey("youngest_birth_date")) {
            System.out.println("\n" + ColorUtils.header("Youngest Contact:"));
            System.out.println(ColorUtils.info("  - Name: " + stats.get("youngest_contact")));
            System.out.println(ColorUtils.info("  - Birth Date: " + stats.get("youngest_birth_date")));
        }
        
        if (stats.containsKey("oldest_contact") && stats.containsKey("oldest_birth_date")) {
            System.out.println("\n" + ColorUtils.header("Oldest Contact:"));
            System.out.println(ColorUtils.info("  - Name: " + stats.get("oldest_contact")));
            System.out.println(ColorUtils.info("  - Birth Date: " + stats.get("oldest_birth_date")));
        }
        
        if (stats.containsKey("average_age")) {
            System.out.println("\n" + ColorUtils.info("Average Age: " + stats.get("average_age") + " years"));
        }
        
        System.out.println("\n" + ColorUtils.header("======================================="));
        System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Lists all users in the system with their details.
     */
    private void listAllUsers() {
        System.out.println("\n" + ColorUtils.header("--- List All Users ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        List<User> users = userDAO.findAll();
        
        if (users.isEmpty()) {
            System.out.println(ColorUtils.warning("No users found in the system."));
        } else {
            System.out.printf(ColorUtils.header("%-8s %-15s %-20s %-20s %-15s %-12s%n"), 
                "ID", "Username", "Name", "Surname", "Role", "Status");
            System.out.println(ColorUtils.colorize("---------------------------------------------------------------------------------------", ColorUtils.CYAN));
            
            for (User user : users) {
                String status = user.getEmploymentStatus();
                if (status == null || status.isEmpty()) {
                    status = "Employed"; // Default for backward compatibility
                }
                System.out.printf("%-8d %-15s %-20s %-20s %-15s %-12s%n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getName(),
                    user.getSurname(),
                    user.getRole(),
                    status);
            }
            
            System.out.println(ColorUtils.colorize("---------------------------------------------------------------------------------------", ColorUtils.CYAN));
            System.out.println(ColorUtils.info("Total users: " + users.size()));
        }
        
        System.out.println("\n" + ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Updates an existing user's information.
     * Prompts for user ID and allows updating username, name, surname, and role.
     * @author sarah nauman
     */
    private void updateUser() {
        System.out.println("\n" + ColorUtils.header("--- Update User ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        System.out.print(ColorUtils.managerPrompt("Enter User ID to update: "));
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);
        
        if (userId == null) {
            System.out.println(ColorUtils.error("Invalid user ID. Please enter a valid number."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println(ColorUtils.error("User with ID " + userId + " not found."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Record the old state for undo before updating
        undoService.recordUserUpdate(user);
        
        // Display current user info
        System.out.println("\n" + ColorUtils.header("Current User Information:"));
        System.out.println(ColorUtils.info("ID: " + user.getUserId()));
        System.out.println(ColorUtils.info("Username: " + user.getUsername()));
        System.out.println(ColorUtils.info("Name: " + user.getName()));
        System.out.println(ColorUtils.info("Surname: " + user.getSurname()));
        System.out.println(ColorUtils.info("Role: " + user.getRole()));
        String status = user.getEmploymentStatus();
        if (status == null || status.isEmpty()) {
            status = "Employed";
        }
        System.out.println(ColorUtils.info("Status: " + status));
        System.out.println("\n" + ColorUtils.info("Enter new values (press Enter to keep current value):"));
        
        // Update username
        System.out.print(ColorUtils.managerPrompt("Username [" + user.getUsername() + "]: "));
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) {
            if (!ValidationUtils.isValidNickname(username)) {
                System.out.println(ColorUtils.error("Invalid username. Use ONLY letters (Turkish characters supported) and digits. NO spaces, hyphens, dashes, underscores, or ANY special characters allowed."));
                System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
                waitForEnter();
                return;
            }
            user.setUsername(username);
        }
        
        // Update name
        System.out.print(ColorUtils.managerPrompt("Name [" + user.getName() + "]: "));
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            if (!ValidationUtils.isValidName(name)) {
                System.out.println(ColorUtils.error("Invalid name. Use ONLY letters (Turkish characters supported). NO spaces, hyphens, dashes, or ANY special characters allowed."));
                System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
                waitForEnter();
                return;
            }
            user.setName(name);
        }
        
        // Update surname
        System.out.print(ColorUtils.managerPrompt("Surname [" + user.getSurname() + "]: "));
        String surname = scanner.nextLine().trim();
        if (!surname.isEmpty()) {
            if (!ValidationUtils.isValidName(surname)) {
                System.out.println(ColorUtils.error("Invalid surname. Use ONLY letters (Turkish characters supported). NO spaces, hyphens, dashes, or ANY special characters allowed."));
                System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
                waitForEnter();
                return;
            }
            user.setSurname(surname);
        }
        
        // Update role
        System.out.print(ColorUtils.managerPrompt("Role [" + user.getRole() + "] (Tester/Junior/Senior/Manager): "));
        String role = scanner.nextLine().trim();
        if (!role.isEmpty()) {
            if (role.equalsIgnoreCase("Tester") || role.equalsIgnoreCase("Junior") || 
                role.equalsIgnoreCase("Senior") || role.equalsIgnoreCase("Manager")) {
                user.setRole(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
            } else {
                System.out.println(ColorUtils.warning("Invalid role. Keeping current role."));
            }
        }
        
        // Ask about password update
        System.out.print(ColorUtils.managerPrompt("Do you want to change password? (y/n): "));
        String changePass = scanner.nextLine().trim().toLowerCase();
        if (changePass.equals("y") || changePass.equals("yes")) {
            System.out.print(ColorUtils.managerPrompt("Enter new password: "));
            String newPassword = scanner.nextLine().trim();
            if (!newPassword.isEmpty()) {
                String hashedPassword = AuthService.hashPassword(newPassword);
                user.setPasswordHash(hashedPassword);
            } else {
                System.out.println(ColorUtils.error("Password cannot be empty. Password not changed."));
            }
        }
        
        // Perform update
        if (userDAO.update(user)) {
            System.out.println("\n" + ColorUtils.success("User updated successfully!"));
        } else {
            System.out.println("\n" + ColorUtils.error("Failed to update user. Please check the error messages above."));
        }
        
        System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Adds a new user to the system.
     * Prompts for all required user information and creates the user.
     */
    private void addUser() {
        System.out.println("\n" + ColorUtils.header("--- Add New User ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        User newUser = new User();
        
        // Get username
        System.out.print(ColorUtils.managerPrompt("Username (required): "));
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println(ColorUtils.error("Username cannot be empty. User creation cancelled."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        if (!ValidationUtils.isValidNickname(username)) {
            System.out.println(ColorUtils.error("Invalid username. Use ONLY letters (Turkish characters supported) and digits. NO spaces, hyphens, dashes, underscores, or ANY special characters allowed."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        newUser.setUsername(username);
        
        // Get password
        System.out.print(ColorUtils.managerPrompt("Password (required): "));
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println(ColorUtils.error("Password cannot be empty. User creation cancelled."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        String hashedPassword = AuthService.hashPassword(password);
        newUser.setPasswordHash(hashedPassword);
        
        // Get name
        System.out.print(ColorUtils.managerPrompt("Name (required): "));
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(ColorUtils.error("Name cannot be empty. User creation cancelled."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        if (!ValidationUtils.isValidName(name)) {
            System.out.println(ColorUtils.error("Invalid name. Use ONLY letters (Turkish characters supported). NO spaces, hyphens, dashes, or ANY special characters allowed."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        newUser.setName(name);
        
        // Get surname
        System.out.print(ColorUtils.managerPrompt("Surname (required): "));
        String surname = scanner.nextLine().trim();
        if (surname.isEmpty()) {
            System.out.println(ColorUtils.error("Surname cannot be empty. User creation cancelled."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        if (!ValidationUtils.isValidName(surname)) {
            System.out.println(ColorUtils.error("Invalid surname. Use ONLY letters (Turkish characters supported). NO spaces, hyphens, dashes, or ANY special characters allowed."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        newUser.setSurname(surname);
        
        // Get role
        System.out.print(ColorUtils.managerPrompt("Role (Tester/Junior/Senior/Manager): "));
        String role = scanner.nextLine().trim();
        if (role.isEmpty()) {
            role = "Tester";
        }
        if (role.equalsIgnoreCase("Tester") || role.equalsIgnoreCase("Junior") || 
            role.equalsIgnoreCase("Senior") || role.equalsIgnoreCase("Manager")) {
            newUser.setRole(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
        } else {
            System.out.println(ColorUtils.warning("Invalid role. Defaulting to Tester."));
            newUser.setRole("Tester");
        }
        
        // Set employment status to 'Employed' for new users
        newUser.setEmploymentStatus("Employed");
        
        // Create user
        if (userDAO.create(newUser)) {
            System.out.println("\n" + ColorUtils.success("User created successfully!"));
            System.out.println(ColorUtils.success("New User ID: " + newUser.getUserId()));
            // Record the add operation for undo
            undoService.recordUserAdd(newUser.getUserId());
        } else {
            System.out.println("\n" + ColorUtils.error("Failed to create user. Username may already exist."));
        }
        
        System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Fires a user by updating their employment_status to 'Fired'.
     * Prompts for user ID and confirms before firing.
     */
    private void fireUser() {
        System.out.println("\n" + ColorUtils.header("--- Fire User ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        System.out.print(ColorUtils.managerPrompt("Enter User ID to fire: "));
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);
        
        if (userId == null) {
            System.out.println(ColorUtils.error("Invalid user ID. Please enter a valid number."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Check if user exists
        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println(ColorUtils.error("User with ID " + userId + " not found."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Prevent firing yourself
        if (user.getUserId() == currentUser.getUserId()) {
            System.out.println(ColorUtils.error("You cannot fire your own account!"));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Check if already fired
        String currentStatus = user.getEmploymentStatus();
        if (currentStatus != null && currentStatus.equals("Fired")) {
            System.out.println(ColorUtils.warning("User is already fired."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Display user info and confirm
        System.out.println("\n" + ColorUtils.header("User to be fired:"));
        System.out.println(ColorUtils.info("ID: " + user.getUserId()));
        System.out.println(ColorUtils.info("Username: " + user.getUsername()));
        System.out.println(ColorUtils.info("Name: " + user.getName() + " " + user.getSurname()));
        System.out.println(ColorUtils.info("Role: " + user.getRole()));
        System.out.println(ColorUtils.info("Current Status: " + (currentStatus != null ? currentStatus : "Employed")));
        
        // Strict y/n validation
        String confirm;
        while (true) {
            System.out.print("\n" + ColorUtils.managerPrompt("Are you sure you want to fire this user? (y/n): "));
            confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("n")) {
                break;
            }
            
            System.out.println(ColorUtils.error("Invalid choice. Please enter 'y' or 'n'."));
        }
        
        if (confirm.equals("y")) {
            if (userDAO.fireUser(userId)) {
                System.out.println(ColorUtils.success("User fired successfully!"));
            } else {
                System.out.println(ColorUtils.error("Failed to fire user."));
            }
        } else {
            System.out.println(ColorUtils.info("Operation cancelled."));
        }
        
        System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Deletes a user from the system.
     * Prompts for user ID and confirms before deletion.
     */
    private void deleteUser() {
        System.out.println("\n" + ColorUtils.header("--- Delete User ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        System.out.print(ColorUtils.managerPrompt("Enter User ID to delete: "));
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);
        
        if (userId == null) {
            System.out.println(ColorUtils.error("Invalid user ID. Please enter a valid number."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Check if user exists
        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println(ColorUtils.error("User with ID " + userId + " not found."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Prevent deleting yourself
        if (user.getUserId() == currentUser.getUserId()) {
            System.out.println(ColorUtils.error("You cannot delete your own account!"));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Record delete operation before deletion
        undoService.recordUserDelete(user);
        
        // Display user info and confirm
        System.out.println("\n" + ColorUtils.header("User to be deleted:"));
        System.out.println(ColorUtils.info("ID: " + user.getUserId()));
        System.out.println(ColorUtils.info("Username: " + user.getUsername()));
        System.out.println(ColorUtils.info("Name: " + user.getName() + " " + user.getSurname()));
        System.out.println(ColorUtils.info("Role: " + user.getRole()));
        String status = user.getEmploymentStatus();
        if (status == null || status.isEmpty()) {
            status = "Employed";
        }
        System.out.println(ColorUtils.info("Status: " + status));

        // Strict y/n validation
        String confirm;
        while (true) {
            System.out.print("\n" + ColorUtils.managerPrompt("Are you sure you want to delete this user? (y/n): "));
            confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("n")) {
                break;
            }
            
            System.out.println(ColorUtils.error("Invalid choice. Please enter 'y' or 'n'."));
        }
        
        if (confirm.equals("y")) {
            if (userDAO.delete(userId)) {
                System.out.println(ColorUtils.success("User deleted successfully!"));
            } else {
                System.out.println(ColorUtils.error("Failed to delete user."));
            }
        } else {
            System.out.println(ColorUtils.info("Deletion cancelled."));
        }
        
        System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Undoes the last add, update, or delete operation.
     */

    /**
     * Changes the password for the currently logged-in user.
     */
    private void changePassword() {
        System.out.println("\n" + ColorUtils.header("--- Change Password ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        System.out.print(ColorUtils.managerPrompt("Enter current password: "));
        String currentPassword = scanner.nextLine().trim();
        
        // Verify current password
        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println(ColorUtils.error("Error: Could not retrieve user information."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Check password using AuthService logic
        String storedHash = currentUserFromDB.getPasswordHash();
        boolean passwordMatches = false;
        
        if (storedHash != null && !storedHash.isBlank()) {
            if (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
                passwordMatches = org.mindrot.jbcrypt.BCrypt.checkpw(currentPassword, storedHash);
            } else {
                passwordMatches = currentPassword.equals(storedHash);
            }
        }
        
        if (!passwordMatches) {
            System.out.println(ColorUtils.error("Current password is incorrect."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Get new password
        System.out.print(ColorUtils.managerPrompt("Enter new password: "));
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ColorUtils.error("Password cannot be empty."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        System.out.print(ColorUtils.managerPrompt("Confirm new password: "));
        String confirmPassword = scanner.nextLine().trim();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ColorUtils.error("Passwords do not match. Password not changed."));
            System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Update password
        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        System.out.println(ColorUtils.success("Password changed successfully!"));
        // Update current user object
        currentUser.setPasswordHash(hashedPassword);
        
        System.out.println(ColorUtils.managerPrompt("Press Enter to continue..."));
        waitForEnter();
    }
}

