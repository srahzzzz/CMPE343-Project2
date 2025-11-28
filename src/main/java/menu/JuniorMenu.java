package menu;

import model.User;

/**
 * Menu for Junior Developer role.
 * 
 * <p>Junior Developers can:
 * - Change password
 * - Logout
 * - List all contacts
 * - Search by selected field or fields
 * - Sort results
 * - Update existing contact
 */
public class JuniorMenu extends BaseMenu {

    /**
     * Constructor for JuniorMenu.
     *
     * @param user the logged-in junior developer user
     */
    public JuniorMenu(User user) {
        super(user);
    }

    @Override
    protected void displayMenu() {
        System.out.println("1. List all contacts");
        System.out.println("2. Search by field");
        System.out.println("3. Search by multiple fields");
        System.out.println("4. Sort contacts");
        System.out.println("5. Update contact");
        System.out.println("6. Change password");
        System.out.println("0. Logout");
    }

    @Override
    protected void handleOption(int choice) {
        switch (choice) {
            case 1:
                listAllContacts();
                break;
            case 2:
                searchByField();
                break;
            case 3:
                searchByMultipleFields();
                break;
            case 4:
                sortContacts();
                break;
            case 5:
                updateContact();
                break;
            case 6:
                changePassword();
                break;
            default:
                System.out.println("\nInvalid option! Please try again.");
        }
    }

    private void listAllContacts() {
        System.out.println("\n--- List All Contacts ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement list all contacts
    }

    private void searchByField() {
        System.out.println("\n--- Search by Field ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement single field search
    }

    private void searchByMultipleFields() {
        System.out.println("\n--- Search by Multiple Fields ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement multi-field search
    }

    private void sortContacts() {
        System.out.println("\n--- Sort Contacts ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement sorting
    }

    private void updateContact() {
        System.out.println("\n--- Update Contact ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement update contact
    }

    private void changePassword() {
        System.out.println("\n--- Change Password ---");
        System.out.println("(Not yet implemented)");
        // TODO: Implement change password
    }
}

