package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;
import service.ValidationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeniorMenu extends BaseMenu {

    private final ContactDAO contactDAO;
    private final UserDAO userDAO;

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";

    public SeniorMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println(CYAN + "=== Senior Developer Menu ===" + RESET);
        System.out.println(YELLOW + "1. List all contacts" + RESET);
        System.out.println(YELLOW + "2. Search by field" + RESET);
        System.out.println(YELLOW + "3. Search by multiple fields" + RESET);
        System.out.println(YELLOW + "4. Sort contacts" + RESET);
        System.out.println(YELLOW + "5. Update contact" + RESET);
        System.out.println(YELLOW + "6. Add new contact(s)" + RESET);
        System.out.println(YELLOW + "7. Delete contact(s)" + RESET);
        System.out.println(YELLOW + "8. Change password" + RESET);
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
                updateContact();
                break;
            case 6:
                addContacts();
                break;
            case 7:
                deleteContacts();
                break;
            case 8:
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

    private void updateContact() {
        System.out.println(CYAN + "\n--- Update Contact ---" + RESET);
        System.out.println("=======================================");
    }

    private void addContacts() {
        System.out.println(CYAN + "\n--- Add New Contact(s) ---" + RESET);
        System.out.println("=======================================");
    }

    private void deleteContacts() {
        System.out.println(CYAN + "\n--- Delete Contact(s) ---" + RESET);
        System.out.println("=======================================");
    }

    private void changePassword() {
        System.out.println(CYAN + "\n--- Change Password ---" + RESET);
        System.out.println("=======================================");
    }

    private void displayContactsTable(List<Contact> contacts) {
        // You can add colors inside table later if needed, for now keep default
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
