package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.User;
import service.AuthService;

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

    /**
     * Constructor for ManagerMenu.
     *
     * @param user the logged-in manager user
     */
    public ManagerMenu(User user) {
        super(user);
        this.userDAO = new UserDAO();
        this.contactDAO = new ContactDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println("1. Contacts statistical info");
        System.out.println("2. List all users");
        System.out.println("3. Update user");
        System.out.println("4. Add new user");
        System.out.println("5. Delete user");
        System.out.println("6. Change password");
        System.out.println("0. Logout");
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
                deleteUser();
                break;
            case 6:
                changePassword();
                break;
            default:
                System.out.println("\nInvalid option! Please try again.");
        }
    }

    /**
     * Displays contacts statistical information.
     * Shows various statistics about contacts including counts, names, ages, and LinkedIn presence.
     */
    private void showStatistics() {
        System.out.println("\n--- Contacts Statistical Info ---");
        System.out.println("=======================================");
        
        java.util.Map<String, Object> stats = contactDAO.getStatistics();
        
        if (stats.isEmpty()) {
            System.out.println("Unable to retrieve statistics. Please check database connection.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Total contacts
        if (stats.containsKey("total_contacts")) {
            System.out.println("\nTotal Contacts: " + stats.get("total_contacts"));
        }
        
        // LinkedIn statistics
        if (stats.containsKey("contacts_with_linkedin") && stats.containsKey("contacts_without_linkedin")) {
            System.out.println("\nLinkedIn Statistics:");
            System.out.println("  - Contacts with LinkedIn: " + stats.get("contacts_with_linkedin"));
            System.out.println("  - Contacts without LinkedIn: " + stats.get("contacts_without_linkedin"));
        }
        
        // Most common first name
        if (stats.containsKey("most_common_first_name") && stats.containsKey("most_common_first_name_count")) {
            System.out.println("\nMost Common First Name:");
            System.out.println("  - Name: " + stats.get("most_common_first_name"));
            System.out.println("  - Count: " + stats.get("most_common_first_name_count") + " contact(s)");
        }
        
        // Most common last name
        if (stats.containsKey("most_common_last_name") && stats.containsKey("most_common_last_name_count")) {
            System.out.println("\nMost Common Last Name:");
            System.out.println("  - Name: " + stats.get("most_common_last_name"));
            System.out.println("  - Count: " + stats.get("most_common_last_name_count") + " contact(s)");
        }
        
        // Age statistics
        if (stats.containsKey("youngest_contact") && stats.containsKey("youngest_birth_date")) {
            System.out.println("\nYoungest Contact:");
            System.out.println("  - Name: " + stats.get("youngest_contact"));
            System.out.println("  - Birth Date: " + stats.get("youngest_birth_date"));
        }
        
        if (stats.containsKey("oldest_contact") && stats.containsKey("oldest_birth_date")) {
            System.out.println("\nOldest Contact:");
            System.out.println("  - Name: " + stats.get("oldest_contact"));
            System.out.println("  - Birth Date: " + stats.get("oldest_birth_date"));
        }
        
        if (stats.containsKey("average_age")) {
            System.out.println("\nAverage Age: " + stats.get("average_age") + " years");
        }
        
        System.out.println("\n=======================================");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Lists all users in the system with their details.
     */
    private void listAllUsers() {
        System.out.println("\n--- List All Users ---");
        System.out.println("=======================================");
        
        List<User> users = userDAO.findAll();
        
        if (users.isEmpty()) {
            System.out.println("No users found in the system.");
        } else {
            System.out.printf("%-8s %-15s %-20s %-20s %-15s%n", 
                "ID", "Username", "Name", "Surname", "Role");
            System.out.println("-------------------------------------------------------------------");
            
            for (User user : users) {
                System.out.printf("%-8d %-15s %-20s %-20s %-15s%n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getName(),
                    user.getSurname(),
                    user.getRole());
            }
            
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Total users: " + users.size());
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Updates an existing user's information.
     * Prompts for user ID and allows updating username, name, surname, and role.
     * @author sarah nauman
     */
    private void updateUser() {
        System.out.println("\n--- Update User ---");
        System.out.println("=======================================");
        
        System.out.print("Enter User ID to update: ");
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);
        
        if (userId == null) {
            System.out.println("Invalid user ID. Please enter a valid number.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println("User with ID " + userId + " not found.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Display current user info
        System.out.println("\nCurrent User Information:");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Surname: " + user.getSurname());
        System.out.println("Role: " + user.getRole());
        System.out.println("\nEnter new values (press Enter to keep current value):");
        
        // Update username
        System.out.print("Username [" + user.getUsername() + "]: ");
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) {
            user.setUsername(username);
        }
        
        // Update name
        System.out.print("Name [" + user.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            user.setName(name);
        }
        
        // Update surname
        System.out.print("Surname [" + user.getSurname() + "]: ");
        String surname = scanner.nextLine().trim();
        if (!surname.isEmpty()) {
            user.setSurname(surname);
        }
        
        // Update role
        System.out.print("Role [" + user.getRole() + "] (Tester/Junior/Senior/Manager): ");
        String role = scanner.nextLine().trim();
        if (!role.isEmpty()) {
            if (role.equalsIgnoreCase("Tester") || role.equalsIgnoreCase("Junior") || 
                role.equalsIgnoreCase("Senior") || role.equalsIgnoreCase("Manager")) {
                user.setRole(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
            } else {
                System.out.println("Invalid role. Keeping current role.");
            }
        }
        
        // Ask about password update
        System.out.print("Do you want to change password? (y/n): ");
        String changePass = scanner.nextLine().trim().toLowerCase();
        if (changePass.equals("y") || changePass.equals("yes")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine().trim();
            if (!newPassword.isEmpty()) {
                String hashedPassword = AuthService.hashPassword(newPassword);
                user.setPasswordHash(hashedPassword);
            } else {
                System.out.println("Password cannot be empty. Password not changed.");
            }
        }
        
        // Perform update
        if (userDAO.update(user)) {
            System.out.println("\nUser updated successfully!");
        } else {
            System.out.println("\nFailed to update user. Please check the error messages above.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Adds a new user to the system.
     * Prompts for all required user information and creates the user.
     */
    private void addUser() {
        System.out.println("\n--- Add New User ---");
        System.out.println("=======================================");
        
        User newUser = new User();
        
        // Get username
        System.out.print("Username (required): ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty. User creation cancelled.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        newUser.setUsername(username);
        
        // Get password
        System.out.print("Password (required): ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty. User creation cancelled.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        String hashedPassword = AuthService.hashPassword(password);
        newUser.setPasswordHash(hashedPassword);
        
        // Get name
        System.out.print("Name (required): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. User creation cancelled.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        newUser.setName(name);
        
        // Get surname
        System.out.print("Surname (required): ");
        String surname = scanner.nextLine().trim();
        if (surname.isEmpty()) {
            System.out.println("Surname cannot be empty. User creation cancelled.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        newUser.setSurname(surname);
        
        // Get role
        System.out.print("Role (Tester/Junior/Senior/Manager): ");
        String role = scanner.nextLine().trim();
        if (role.isEmpty()) {
            role = "Tester";
        }
        if (role.equalsIgnoreCase("Tester") || role.equalsIgnoreCase("Junior") || 
            role.equalsIgnoreCase("Senior") || role.equalsIgnoreCase("Manager")) {
            newUser.setRole(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
        } else {
            System.out.println("Invalid role. Defaulting to Tester.");
            newUser.setRole("Tester");
        }
        
        // Create user
        if (userDAO.create(newUser)) {
            System.out.println("\nUser created successfully!");
            System.out.println("New User ID: " + newUser.getUserId());
        } else {
            System.out.println("\nFailed to create user. Username may already exist.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Deletes a user from the system.
     * Prompts for user ID and confirms before deletion.
     */
    private void deleteUser() {
        System.out.println("\n--- Delete User ---");
        System.out.println("=======================================");
        
        System.out.print("Enter User ID to delete: ");
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);
        
        if (userId == null) {
            System.out.println("Invalid user ID. Please enter a valid number.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Check if user exists
        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println("User with ID " + userId + " not found.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Prevent deleting yourself
        if (user.getUserId() == currentUser.getUserId()) {
            System.out.println("You cannot delete your own account!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Display user info and confirm
        System.out.println("\nUser to be deleted:");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName() + " " + user.getSurname());
        System.out.println("Role: " + user.getRole());

        // need to add check ????????
        System.out.print("\nAre you sure you want to delete this user? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            if (userDAO.delete(userId)) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Failed to delete user.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Changes the password for the currently logged-in user.
     */
    private void changePassword() {
        System.out.println("\n--- Change Password ---");
        System.out.println("=======================================");
        
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine().trim();
        
        // Verify current password
        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println("Error: Could not retrieve user information.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
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
            System.out.println("Current password is incorrect.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Get new password
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println("Password cannot be empty.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine().trim();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Password not changed.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Update password
        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        System.out.println("Password changed successfully!");
        // Update current user object
        currentUser.setPasswordHash(hashedPassword);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}

