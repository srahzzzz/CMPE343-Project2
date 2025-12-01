package menu;

import model.User;
import dao.ContactDAO;
import model.Contact;
import service.AuthService;

import java.util.*;

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

    private final ContactDAO contactDAO = new ContactDAO();

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
            case 1 -> listAllContacts();
            case 2 -> searchByField();
            case 3 -> searchByMultipleFields();
            case 4 -> sortContacts();
            case 5 -> updateContact();
            case 6 -> changePassword();
            default -> System.out.println("\nInvalid option! Please try again.");
        }
    }

    private void listAllContacts() {
        System.out.println("\n--- All Contacts ---");
        List<Contact> contacts = contactDAO.findAll();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.printf("%-5s %-15s %-15s %-15s %-15s %-15s %-25s %-20s %-12s%n",
                "ID", "First Name", "Middle Name", "Last Name", "Phone1", "Phone2",
                "Email", "LinkedIn", "Birth Date");

        for (Contact c : contacts) {
            System.out.printf("%-5d %-15s %-15s %-15s %-15s %-15s %-25s %-20s %-12s%n",
                    c.getContactId(),
                    c.getFirstName(),
                    c.getMiddleName() != null ? c.getMiddleName() : "",
                    c.getLastName(),
                    c.getPhonePrimary(),
                    c.getPhoneSecondary() != null ? c.getPhoneSecondary() : "",
                    c.getEmail(),
                    c.getLinkedinUrl() != null ? c.getLinkedinUrl() : "",
                    c.getBirthDate() != null ? c.getBirthDate() : "");
        }
    }

    private void searchByField() {
        System.out.println("\n--- Search by Field ---");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Phone Number");
        System.out.print("Choose a field: ");

        Integer choice = safeParseInt(scanner.nextLine());
        if (choice == null || choice < 1 || choice > 3) {
            System.out.println("Invalid input!");
            return;
        }

        System.out.print("Enter search term: ");
        String term = scanner.nextLine().trim();
        List<Contact> results = new ArrayList<>();

        switch (choice) {
            case 1 -> results = contactDAO.searchByFirstName(term);
            case 2 -> results = contactDAO.searchByLastName(term);
            case 3 -> results = contactDAO.searchByPhone(term);
        }

        if (results.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println("\nSearch Results:");
        listContacts(results);
    }

    private void searchByMultipleFields() {
        System.out.println("\n--- Search by Multiple Fields ---");
        Map<String, String> criteria = new HashMap<>();

        System.out.print("First Name (or leave empty): ");
        String first = scanner.nextLine().trim();
        if (!first.isEmpty()) criteria.put("first_name", first);

        System.out.print("Last Name (or leave empty): ");
        String last = scanner.nextLine().trim();
        if (!last.isEmpty()) criteria.put("last_name", last);

        System.out.print("Phone (or leave empty): ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) criteria.put("phone", phone);

        System.out.print("Email (or leave empty): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) criteria.put("email", email);

        System.out.print("Birth Month (1-12, or leave empty): ");
        String month = scanner.nextLine().trim();
        if (!month.isEmpty()) criteria.put("birth_month", month);

        List<Contact> results = contactDAO.searchByMultipleFields(criteria);
        if (results.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println("\nSearch Results:");
        listContacts(results);
    }

    private void sortContacts() {
        System.out.println("\n--- Sort Contacts ---");
        System.out.print("Enter field to sort by (first_name, last_name, email, birth_date): ");
        String field = scanner.nextLine().trim();

        System.out.print("Ascending? (y/n): ");
        String asc = scanner.nextLine().trim();
        boolean ascending = asc.equalsIgnoreCase("y");

        List<Contact> results = contactDAO.findAllSorted(field, ascending);
        if (results.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println("\nSorted Contacts:");
        listContacts(results);
    }

    private void updateContact() {
        System.out.println("\n--- Update Contact ---");
        System.out.print("Enter Contact ID to update: ");
        Integer id = safeParseInt(scanner.nextLine());
        if (id == null) {
            System.out.println("Invalid ID!");
            return;
        }

        Contact c = contactDAO.findById(id);
        if (c == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println("Leave field empty to keep current value.");

        System.out.print("First Name (" + c.getFirstName() + "): ");
        String first = scanner.nextLine().trim();
        if (!first.isEmpty()) c.setFirstName(first);

        System.out.print("Middle Name (" + (c.getMiddleName() != null ? c.getMiddleName() : "") + "): ");
        String middle = scanner.nextLine().trim();
        if (!middle.isEmpty()) c.setMiddleName(middle);

        System.out.print("Last Name (" + c.getLastName() + "): ");
        String last = scanner.nextLine().trim();
        if (!last.isEmpty()) c.setLastName(last);

        System.out.print("Phone Primary (" + c.getPhonePrimary() + "): ");
        String phone1 = scanner.nextLine().trim();
        if (!phone1.isEmpty()) c.setPhonePrimary(phone1);

        System.out.print("Phone Secondary (" + (c.getPhoneSecondary() != null ? c.getPhoneSecondary() : "") + "): ");
        String phone2 = scanner.nextLine().trim();
        if (!phone2.isEmpty()) c.setPhoneSecondary(phone2);

        System.out.print("Email (" + c.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) c.setEmail(email);

        System.out.print("LinkedIn (" + (c.getLinkedinUrl() != null ? c.getLinkedinUrl() : "") + "): ");
        String linkedin = scanner.nextLine().trim();
        if (!linkedin.isEmpty()) c.setLinkedinUrl(linkedin);

        System.out.print("Birth Date (yyyy-mm-dd) (" + (c.getBirthDate() != null ? c.getBirthDate() : "") + "): ");
        String birth = scanner.nextLine().trim();
        if (!birth.isEmpty()) {
            try {
                c.setBirthDate(java.time.LocalDate.parse(birth));
            } catch (Exception e) {
                System.out.println("Invalid date format. Birth date not changed.");
            }
        }

        boolean updated = contactDAO.update(c);
        System.out.println(updated ? "Contact updated successfully!" : "Failed to update contact.");
    }

    private void changePassword() {
        System.out.println("\n--- Change Password ---");

        System.out.print("Enter current password: ");
        String oldPass = scanner.nextLine().trim();

        if (!AuthService.verifyPasswordStatic(oldPass, currentUser)) {
            System.out.println("Incorrect current password. Password not changed.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine().trim();

        System.out.print("Confirm new password: ");
        String confirmPass = scanner.nextLine().trim();

        if (!newPass.equals(confirmPass)) {
            System.out.println("New passwords do not match. Password not changed.");
            return;
        }

        String hash = AuthService.hashPassword(newPass);
        new dao.UserDAO().updatePasswordHash(currentUser.getUserId(), hash);
        currentUser.setPasswordHash(hash);

        System.out.println("Password changed successfully!");
    }

    private void listContacts(List<Contact> contacts) {
        System.out.printf("%-5s %-15s %-15s %-15s %-15s %-15s %-25s %-20s %-12s%n",
                "ID", "First Name", "Middle Name", "Last Name", "Phone1", "Phone2",
                "Email", "LinkedIn", "Birth Date");

        for (Contact c : contacts) {
            System.out.printf("%-5d %-15s %-15s %-15s %-15s %-15s %-25s %-20s %-12s%n",
                    c.getContactId(),
                    c.getFirstName(),
                    c.getMiddleName() != null ? c.getMiddleName() : "",
                    c.getLastName(),
                    c.getPhonePrimary(),
                    c.getPhoneSecondary() != null ? c.getPhoneSecondary() : "",
                    c.getEmail(),
                    c.getLinkedinUrl() != null ? c.getLinkedinUrl() : "",
                    c.getBirthDate() != null ? c.getBirthDate() : "");
        }
    }
}
