package menu;

import model.User;

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

    /**
     * Constructor for ManagerMenu.
     *
     * @param user the logged-in manager user
     */
    public ManagerMenu(User user) {
        super(user);
    }

    @Override
    protected void displayMenu() {
        System.out.println("1. Contacts statistical info");
        System.out.println("2. List all users");
        System.out.println("3. Update user");
        System.out.println("4. Add/employ new user");
        System.out.println("5. Delete/fire user");
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

    private void showStatistics() {
        System.out.println("\n--- Contacts Statistical Info ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement statistics
    }

    private void listAllUsers() {
        System.out.println("\n--- List All Users ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement list all users
    }

    private void updateUser() {
        System.out.println("\n--- Update User ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement update user
    }

    private void addUser() {
        System.out.println("\n--- Add/Employ New User ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement add user
    }

    private void deleteUser() {
        System.out.println("\n--- Delete/Fire User ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement delete user
    }

    private void changePassword() {
        System.out.println("\n--- Change Password ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement change password
    }
}

