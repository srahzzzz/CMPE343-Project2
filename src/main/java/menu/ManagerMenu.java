package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.User;
import service.AuthService;
import service.ANSI;

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
 */
public class ManagerMenu extends BaseMenu {

    private final UserDAO userDAO;
    private final ContactDAO contactDAO;

    public ManagerMenu(User user) {
        super(user);
        this.userDAO = new UserDAO();
        this.contactDAO = new ContactDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println(ANSI.CYAN + "1. Contacts statistical info" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "2. List all users" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "3. Update user" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "4. Add new user" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "5. Delete user" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "6. Change password" + ANSI.RESET);
        System.out.println(ANSI.YELLOW + "0. Logout" + ANSI.RESET);
    }

    @Override
    protected void handleOption(int choice) {
        switch (choice) {
            case 1 -> showStatistics();
            case 2 -> listAllUsers();
            case 3 -> updateUser();
            case 4 -> addUser();
            case 5 -> deleteUser();
            case 6 -> changePassword();
            default -> System.out.println(ANSI.RED + "\nInvalid option! Please try again." + ANSI.RESET);
        }
    }

    private void showStatistics() {
        System.out.println(ANSI.BLUE + "\n--- Contacts Statistical Info ---" + ANSI.RESET);
        System.out.println(ANSI.BLUE + "=======================================" + ANSI.RESET);

        java.util.Map<String, Object> stats = contactDAO.getStatistics();

        if (stats.isEmpty()) {
            System.out.println(ANSI.YELLOW + "Unable to retrieve statistics. Please check database connection." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        if (stats.containsKey("total_contacts")) {
            System.out.println(ANSI.CYAN + "\nTotal Contacts: " + stats.get("total_contacts") + ANSI.RESET);
        }

        if (stats.containsKey("contacts_with_linkedin") && stats.containsKey("contacts_without_linkedin")) {
            System.out.println(ANSI.CYAN + "\nLinkedIn Statistics:" + ANSI.RESET);
            System.out.println("  - Contacts with LinkedIn: " + stats.get("contacts_with_linkedin"));
            System.out.println("  - Contacts without LinkedIn: " + stats.get("contacts_without_linkedin"));
        }

        if (stats.containsKey("most_common_first_name") && stats.containsKey("most_common_first_name_count")) {
            System.out.println(ANSI.CYAN + "\nMost Common First Name:" + ANSI.RESET);
            System.out.println("  - Name: " + stats.get("most_common_first_name"));
            System.out.println("  - Count: " + stats.get("most_common_first_name_count") + " contact(s)");
        }

        if (stats.containsKey("most_common_last_name") && stats.containsKey("most_common_last_name_count")) {
            System.out.println(ANSI.CYAN + "\nMost Common Last Name:" + ANSI.RESET);
            System.out.println("  - Name: " + stats.get("most_common_last_name"));
            System.out.println("  - Count: " + stats.get("most_common_last_name_count") + " contact(s)");
        }

        if (stats.containsKey("youngest_contact") && stats.containsKey("youngest_birth_date")) {
            System.out.println(ANSI.CYAN + "\nYoungest Contact:" + ANSI.RESET);
            System.out.println("  - Name: " + stats.get("youngest_contact"));
            System.out.println("  - Birth Date: " + stats.get("youngest_birth_date"));
        }

        if (stats.containsKey("oldest_contact") && stats.containsKey("oldest_birth_date")) {
            System.out.println(ANSI.CYAN + "\nOldest Contact:" + ANSI.RESET);
            System.out.println("  - Name: " + stats.get("oldest_contact"));
            System.out.println("  - Birth Date: " + stats.get("oldest_birth_date"));
        }

        if (stats.containsKey("average_age")) {
            System.out.println(ANSI.CYAN + "\nAverage Age: " + stats.get("average_age") + " years" + ANSI.RESET);
        }

        System.out.println(ANSI.BLUE + "\n=======================================" + ANSI.RESET);
        System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }

    private void listAllUsers() {
        System.out.println(ANSI.BLUE + "\n--- List All Users ---" + ANSI.RESET);
        System.out.println(ANSI.BLUE + "=======================================" + ANSI.RESET);

        List<User> users = userDAO.findAll();

        if (users.isEmpty()) {
            System.out.println(ANSI.YELLOW + "No users found in the system." + ANSI.RESET);
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
            System.out.println(ANSI.CYAN + "Total users: " + users.size() + ANSI.RESET);
        }

        System.out.println(ANSI.YELLOW + "\nPress Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }

    private void updateUser() {
        System.out.println(ANSI.BLUE + "\n--- Update User ---" + ANSI.RESET);
        System.out.println(ANSI.BLUE + "=======================================" + ANSI.RESET);

        System.out.print(ANSI.YELLOW + "Enter User ID to update: " + ANSI.RESET);
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);

        if (userId == null) {
            System.out.println(ANSI.RED + "Invalid user ID. Please enter a valid number." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println(ANSI.RED + "User with ID " + userId + " not found." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        System.out.println(ANSI.CYAN + "\nCurrent User Information:" + ANSI.RESET);
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Surname: " + user.getSurname());
        System.out.println("Role: " + user.getRole());
        System.out.println(ANSI.YELLOW + "\nEnter new values (press Enter to keep current value):" + ANSI.RESET);

        System.out.print("Username [" + user.getUsername() + "]: ");
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) user.setUsername(username);

        System.out.print("Name [" + user.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) user.setName(name);

        System.out.print("Surname [" + user.getSurname() + "]: ");
        String surname = scanner.nextLine().trim();
        if (!surname.isEmpty()) user.setSurname(surname);

        System.out.print("Role [" + user.getRole() + "] (Tester/Junior/Senior/Manager): ");
        String role = scanner.nextLine().trim();
        if (!role.isEmpty()) {
            if (role.equalsIgnoreCase("Tester") || role.equalsIgnoreCase("Junior") ||
                    role.equalsIgnoreCase("Senior") || role.equalsIgnoreCase("Manager")) {
                user.setRole(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
            } else {
                System.out.println(ANSI.RED + "Invalid role. Keeping current role." + ANSI.RESET);
            }
        }

        System.out.print(ANSI.YELLOW + "Do you want to change password? (y/n): " + ANSI.RESET);
        String changePass = scanner.nextLine().trim().toLowerCase();
        if (changePass.equals("y") || changePass.equals("yes")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine().trim();
            if (!newPassword.isEmpty()) {
                String hashedPassword = AuthService.hashPassword(newPassword);
                user.setPasswordHash(hashedPassword);
            } else {
                System.out.println(ANSI.RED + "Password cannot be empty. Password not changed." + ANSI.RESET);
            }
        }

        if (userDAO.update(user)) {
            System.out.println(ANSI.GREEN + "\nUser updated successfully!" + ANSI.RESET);
        } else {
            System.out.println(ANSI.RED + "\nFailed to update user. Please check the error messages above." + ANSI.RESET);
        }

        System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }

    private void addUser() {
        System.out.println(ANSI.BLUE + "\n--- Add New User ---" + ANSI.RESET);
        System.out.println(ANSI.BLUE + "=======================================" + ANSI.RESET);

        User newUser = new User();

        System.out.print(ANSI.YELLOW + "Username (required): " + ANSI.RESET);
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println(ANSI.RED + "Username cannot be empty. User creation cancelled." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }
        newUser.setUsername(username);

        System.out.print(ANSI.YELLOW + "Password (required): " + ANSI.RESET);
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println(ANSI.RED + "Password cannot be empty. User creation cancelled." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }
        String hashedPassword = AuthService.hashPassword(password);
        newUser.setPasswordHash(hashedPassword);

        System.out.print(ANSI.YELLOW + "Name (required): " + ANSI.RESET);
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(ANSI.RED + "Name cannot be empty. User creation cancelled." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }
        newUser.setName(name);

        System.out.print(ANSI.YELLOW + "Surname (required): " + ANSI.RESET);
        String surname = scanner.nextLine().trim();
        if (surname.isEmpty()) {
            System.out.println(ANSI.RED + "Surname cannot be empty. User creation cancelled." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }
        newUser.setSurname(surname);

        System.out.print(ANSI.YELLOW + "Role (Tester/Junior/Senior/Manager): " + ANSI.RESET);
        String role = scanner.nextLine().trim();
        if (role.isEmpty()) role = "Tester";
        if (role.equalsIgnoreCase("Tester") || role.equalsIgnoreCase("Junior") ||
                role.equalsIgnoreCase("Senior") || role.equalsIgnoreCase("Manager")) {
            newUser.setRole(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
        } else {
            System.out.println(ANSI.RED + "Invalid role. Defaulting to Tester." + ANSI.RESET);
            newUser.setRole("Tester");
        }

        if (userDAO.create(newUser)) {
            System.out.println(ANSI.GREEN + "\nUser created successfully!" + ANSI.RESET);
            System.out.println(ANSI.CYAN + "New User ID: " + newUser.getUserId() + ANSI.RESET);
        } else {
            System.out.println(ANSI.RED + "\nFailed to create user. Username may already exist." + ANSI.RESET);
        }

        System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }

    private void deleteUser() {
        System.out.println(ANSI.BLUE + "\n--- Delete User ---" + ANSI.RESET);
        System.out.println(ANSI.BLUE + "=======================================" + ANSI.RESET);

        System.out.print(ANSI.YELLOW + "Enter User ID to delete: " + ANSI.RESET);
        String idInput = scanner.nextLine().trim();
        Integer userId = BaseMenu.safeParseInt(idInput);

        if (userId == null) {
            System.out.println(ANSI.RED + "Invalid user ID. Please enter a valid number." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println(ANSI.RED + "User with ID " + userId + " not found." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        if (user.getUserId() == currentUser.getUserId()) {
            System.out.println(ANSI.RED + "You cannot delete your own account!" + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        System.out.println(ANSI.CYAN + "\nUser to be deleted:" + ANSI.RESET);
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName() + " " + user.getSurname());
        System.out.println("Role: " + user.getRole());

        System.out.print(ANSI.YELLOW + "\nAre you sure you want to delete this user? (yes/no): " + ANSI.RESET);
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            if (userDAO.delete(userId)) {
                System.out.println(ANSI.GREEN + "User deleted successfully!" + ANSI.RESET);
            } else {
                System.out.println(ANSI.RED + "Failed to delete user." + ANSI.RESET);
            }
        } else {
            System.out.println(ANSI.YELLOW + "Deletion cancelled." + ANSI.RESET);
        }

        System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }

    private void changePassword() {
        System.out.println(ANSI.BLUE + "\n--- Change Password ---" + ANSI.RESET);
        System.out.println(ANSI.BLUE + "=======================================" + ANSI.RESET);

        System.out.print(ANSI.YELLOW + "Enter current password: " + ANSI.RESET);
        String currentPassword = scanner.nextLine().trim();

        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println(ANSI.RED + "Error: Could not retrieve user information." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

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
            System.out.println(ANSI.RED + "Current password is incorrect." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        System.out.print(ANSI.YELLOW + "Enter new password: " + ANSI.RESET);
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ANSI.RED + "Password cannot be empty." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        System.out.print(ANSI.YELLOW + "Confirm new password: " + ANSI.RESET);
        String confirmPassword = scanner.nextLine().trim();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ANSI.RED + "Passwords do not match. Password not changed." + ANSI.RESET);
            System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
            scanner.nextLine();
            return;
        }

        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        currentUser.setPasswordHash(hashedPassword);

        System.out.println(ANSI.GREEN + "Password changed successfully!" + ANSI.RESET);
        System.out.println(ANSI.YELLOW + "Press Enter to continue..." + ANSI.RESET);
        scanner.nextLine();
    }
}
