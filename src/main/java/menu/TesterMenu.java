package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu for Tester role.
 *
 * <p>Testers can:
 * - Change password,
 * - Logout,
 * - List all contacts,
 * - Search by selected field or fields,
 * - Sort results by a user-selected field.
 */
public class TesterMenu extends BaseMenu {

    private final ContactDAO contactDAO;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";

    public TesterMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println(CYAN + "=== Tester Menu ===" + RESET);
        System.out.println(YELLOW + "1. List all contacts" + RESET);
        System.out.println(YELLOW + "2. Search by field" + RESET);
        System.out.println(YELLOW + "3. Search by multiple fields" + RESET);
        System.out.println(YELLOW + "4. Sort contacts by selected field" + RESET);
        System.out.println(YELLOW + "5. Change password" + RESET);
        System.out.println(RED + "0. Logout" + RESET);
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
                changePassword();
                break;
            default:
                System.out.println(RED + "\nInvalid option! Please try again." + RESET);
        }
    }

    private void listAllContacts() {
        System.out.println(CYAN + "\n--- List All Contacts ---" + RESET);
        System.out.println("=======================================");

        List<Contact> contacts = contactDAO.findAll();

        if (contacts.isEmpty()) {
            System.out.println(YELLOW + "No contacts found in the database." + RESET);
        } else {
            displayContactsTable(contacts);
            System.out.println(GREEN + "Total contacts: " + contacts.size() + RESET);
        }

        System.out.println(CYAN + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    private void searchByField() {
        System.out.println(CYAN + "\n--- Search by Field ---" + RESET);
        System.out.println("=======================================");
        System.out.println(BLUE + "1. Search by First Name" + RESET);
        System.out.println(BLUE + "2. Search by Last Name" + RESET);
        System.out.println(BLUE + "3. Search by Phone Number" + RESET);
        System.out.println(BLUE + "4. Search by Email" + RESET);
        System.out.print(YELLOW + "\nSelect search type: " + RESET);
    }

    private void searchByMultipleFields() {
        System.out.println(CYAN + "\n--- Search by Multiple Fields ---" + RESET);
        System.out.println("=======================================");
        System.out.println(YELLOW + "Enter search criteria (press Enter to skip a field):" + RESET);
    }

    private void sortContacts() {
        System.out.println(CYAN + "\n--- Sort Contacts ---" + RESET);
        System.out.println("=======================================");
        System.out.println(YELLOW + "Select field to sort by:" + RESET);
    }

    private void changePassword() {
        System.out.println(CYAN + "\n--- Change Password ---" + RESET);
        System.out.println("=======================================");
    }

    private void displayContactsTable(List<Contact> contacts) {
        // Header coloring
        System.out.printf(PURPLE + "%-6s %-15s %-15s %-15s %-15s %-15s %-15s %-25s %-25s %-12s%n" + RESET,
                "ID", "First Name", "Middle Name", "Last Name", "Nickname",
                "Phone 1", "Phone 2", "Email", "LinkedIn", "Birth Date");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Contact contact : contacts) {
            String firstName = contact.getFirstName() != null ? contact.getFirstName() : "";
            String middleName = contact.getMiddleName() != null ? contact.getMiddleName() : "";
            String lastName = contact.getLastName() != null ? contact.getLastName() : "";
            String nickname = contact.getNickname() != null ? contact.getNickname() : "";
            String phonePrimary = contact.getPhonePrimary() != null ? contact.getPhonePrimary() : "";
            String phoneSecondary = contact.getPhoneSecondary() != null ? contact.getPhoneSecondary() : "";
            String email = contact.getEmail() != null ? contact.getEmail() : "";
            String linkedinUrl = contact.getLinkedinUrl() != null ? contact.getLinkedinUrl() : "";
            String birthDate = contact.getBirthDate() != null ?
                    contact.getBirthDate().format(dateFormatter) : "N/A";

            // Truncate for display
            if (firstName.length() > 13) firstName = firstName.substring(0, 10) + "...";
            if (middleName.length() > 13) middleName = middleName.substring(0, 10) + "...";
            if (lastName.length() > 13) lastName = lastName.substring(0, 10) + "...";
            if (nickname.length() > 13) nickname = nickname.substring(0, 10) + "...";
            if (phonePrimary.length() > 13) phonePrimary = phonePrimary.substring(0, 10) + "...";
            if (phoneSecondary.length() > 13) phoneSecondary = phoneSecondary.substring(0, 10) + "...";
            if (email.length() > 23) email = email.substring(0, 20) + "...";
            if (linkedinUrl.length() > 23) linkedinUrl = linkedinUrl.substring(0, 20) + "...";

            System.out.printf("%-6d %-15s %-15s %-15s %-15s %-15s %-15s %-25s %-25s %-12s%n",
                    contact.getContactId(),
                    firstName,
                    middleName.isEmpty() ? "N/A" : middleName,
                    lastName,
                    nickname.isEmpty() ? "N/A" : nickname,
                    phonePrimary.isEmpty() ? "N/A" : phonePrimary,
                    phoneSecondary.isEmpty() ? "N/A" : phoneSecondary,
                    email.isEmpty() ? "N/A" : email,
                    linkedinUrl.isEmpty() ? "N/A" : linkedinUrl,
                    birthDate);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}


