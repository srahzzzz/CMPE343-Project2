package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu for Senior Developer role.
 *
 * <p>Senior Developers can:
 * - Change password
 * - Logout
 * - List all contacts
 * - Search by selected field or fields
 * - Sort results in ascending or descending order
 * - Update existing contact
 * - Add new contact or contacts
 * - Delete existing contact or contacts
 * </p>
 */
public class SeniorMenu extends BaseMenu {

    private final ContactDAO contactDAO;
    private final UserDAO userDAO;

    /**
     * Constructor for SeniorMenu.
     *
     * @param user the logged-in senior developer user
     */
    public SeniorMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println("1. List all contacts");
        System.out.println("2. Search by field");
        System.out.println("3. Search by multiple fields");
        System.out.println("4. Sort contacts");
        System.out.println("5. Update contact");
        System.out.println("6. Add new contact(s)");
        System.out.println("7. Delete contact(s)");
        System.out.println("8. Change password");
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
                addContacts();
                break;
            case 7:
                deleteContacts();
                break;
            case 8:
                changePassword();
                break;
            default:
                System.out.println("\nInvalid option! Please try again.");
        }
    }

    /**
     * Lists all contacts in the database.
     */
    private void listAllContacts() {
        System.out.println("\n--- List All Contacts ---");
        System.out.println("=======================================");

        List<Contact> contacts = contactDAO.findAll();

        if (contacts.isEmpty()) {
            System.out.println("No contacts found in the database.");
        } else {
            displayContactsTable(contacts);
            System.out.println("Total contacts: " + contacts.size());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Search by a single selected field.
     */
    private void searchByField() {
        System.out.println("\n--- Search by Field ---");
        System.out.println("=======================================");
        System.out.println("1. Search by First Name");
        System.out.println("2. Search by Last Name");
        System.out.println("3. Search by Phone Number");
        System.out.println("4. Search by Email");
        System.out.print("\nSelect search type: ");

        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);

        if (choice == null || choice < 1 || choice > 4) {
            System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        List<Contact> results;

        switch (choice) {
            case 1:
                System.out.print("Enter first name (or partial): ");
                String firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    System.out.println("First name cannot be empty.");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByFirstName(firstName);
                break;

            case 2:
                System.out.print("Enter last name (or partial): ");
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    System.out.println("Last name cannot be empty.");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByLastName(lastName);
                break;

            case 3:
                System.out.print("Enter phone number (or partial): ");
                String phone = scanner.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println("Phone number cannot be empty.");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByPhone(phone);
                break;

            case 4:
                System.out.print("Enter email (or partial): ");
                String email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty.");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                Map<String, String> emailCriteria = new HashMap<>();
                emailCriteria.put("email", email);
                results = contactDAO.searchByMultipleFields(emailCriteria);
                break;

            default:
                results = List.of();
        }

        if (results.isEmpty()) {
            System.out.println("\nNo contacts found matching your search criteria.");
        } else {
            System.out.println("\nSearch Results:");
            displayContactsTable(results);
            System.out.println("Found " + results.size() + " contact(s).");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Search by multiple selected fields combined with AND logic.
     */
    private void searchByMultipleFields() {
        System.out.println("\n--- Search by Multiple Fields ---");
        System.out.println("=======================================");
        System.out.println("Enter search criteria (press Enter to skip a field):\n");

        Map<String, String> criteria = new HashMap<>();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            criteria.put("first_name", firstName);
        }

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            criteria.put("last_name", lastName);
        }

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            criteria.put("phone", phone);
        }

        System.out.print("Email (partial): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            criteria.put("email", email);
        }

        System.out.print("Birth Month (1-12): ");
        String birthMonth = scanner.nextLine().trim();
        if (!birthMonth.isEmpty()) {
            Integer month = BaseMenu.safeParseInt(birthMonth);
            if (month != null && month >= 1 && month <= 12) {
                criteria.put("birth_month", month.toString());
            } else {
                System.out.println("Invalid month. Skipping birth month filter.");
            }
        }

        if (criteria.isEmpty()) {
            System.out.println("\nNo search criteria provided. Please enter at least one field.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        List<Contact> results = contactDAO.searchByMultipleFields(criteria);

        if (results.isEmpty()) {
            System.out.println("\nNo contacts found matching all your search criteria.");
        } else {
            System.out.println("\nSearch Results:");
            displayContactsTable(results);
            System.out.println("Found " + results.size() + " contact(s) matching all criteria.");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Sort contacts by a user-selected field and order.
     */
    private void sortContacts() {
        System.out.println("\n--- Sort Contacts ---");
        System.out.println("=======================================");
        System.out.println("Select field to sort by:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Email");
        System.out.println("4. Birth Date");
        System.out.println("5. Phone Primary");
        System.out.print("\nEnter your choice: ");

        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);

        if (choice == null || choice < 1 || choice > 5) {
            System.out.println("Invalid choice.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        String field;
        switch (choice) {
            case 1: field = "first_name"; break;
            case 2: field = "last_name"; break;
            case 3: field = "email"; break;
            case 4: field = "birth_date"; break;
            case 5: field = "phone_primary"; break;
            default: field = "contact_id";
        }

        System.out.println("\nSort order:");
        if ("birth_date".equals(field)) {
            System.out.println("1. Ascending (oldest to youngest)");
            System.out.println("2. Descending (youngest to oldest)");
        } else {
            System.out.println("1. Ascending (A-Z, 1-9)");
            System.out.println("2. Descending (Z-A, 9-1)");
        }
        System.out.print("Enter your choice: ");

        String orderInput = scanner.nextLine().trim();
        Integer orderChoice = BaseMenu.safeParseInt(orderInput);

        boolean ascending = orderChoice == null || orderChoice != 2;

        List<Contact> contacts = contactDAO.findAllSorted(field, ascending);

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("\nSorted Contacts:");
            displayContactsTable(contacts);
            System.out.println("Total contacts: " + contacts.size());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Update an existing contact by ID.
     */
    private void updateContact() {
        System.out.println("\n--- Update Contact ---");
        System.out.println("=======================================");

        System.out.print("Enter Contact ID to update: ");
        String idInput = scanner.nextLine().trim();
        Integer contactId = BaseMenu.safeParseInt(idInput);

        if (contactId == null) {
            System.out.println("Invalid contact ID. Please enter a valid number.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        Contact contact = contactDAO.findById(contactId);
        if (contact == null) {
            System.out.println("Contact with ID " + contactId + " not found.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nCurrent Contact Information:");
        displayContactsTable(List.of(contact));
        System.out.println("\nEnter new values (press Enter to keep current value):");

        System.out.print("First Name [" + nullToEmpty(contact.getFirstName()) + "]: ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            contact.setFirstName(firstName);
        }

        System.out.print("Middle Name [" + nullToEmpty(contact.getMiddleName()) + "]: ");
        String middleName = scanner.nextLine().trim();
        if (!middleName.isEmpty()) {
            contact.setMiddleName(middleName);
        }

        System.out.print("Last Name [" + nullToEmpty(contact.getLastName()) + "]: ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            contact.setLastName(lastName);
        }

        System.out.print("Nickname [" + nullToEmpty(contact.getNickname()) + "]: ");
        String nickname = scanner.nextLine().trim();
        if (!nickname.isEmpty()) {
            contact.setNickname(nickname);
        }

        System.out.print("Phone 1 [" + nullToEmpty(contact.getPhonePrimary()) + "]: ");
        String phone1 = scanner.nextLine().trim();
        if (!phone1.isEmpty()) {
            contact.setPhonePrimary(phone1);
        }

        System.out.print("Phone 2 [" + nullToEmpty(contact.getPhoneSecondary()) + "]: ");
        String phone2 = scanner.nextLine().trim();
        if (!phone2.isEmpty()) {
            contact.setPhoneSecondary(phone2);
        }

        System.out.print("Email [" + nullToEmpty(contact.getEmail()) + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            contact.setEmail(email);
        }

        System.out.print("LinkedIn URL [" + nullToEmpty(contact.getLinkedinUrl()) + "]: ");
        String linkedin = scanner.nextLine().trim();
        if (!linkedin.isEmpty()) {
            contact.setLinkedinUrl(linkedin);
        }

        String currentBirthDate = contact.getBirthDate() != null
                ? contact.getBirthDate().format(dateFormatter)
                : "N/A";
        System.out.print("Birth Date (" + currentBirthDate + ") [yyyy-MM-dd or Enter to keep]: ");
        String birthDateInput = scanner.nextLine().trim();
        if (!birthDateInput.isEmpty()) {
            try {
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                contact.setBirthDate(birthDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Keeping existing birth date.");
            }
        }

        if (contactDAO.update(contact)) {
            System.out.println("\nContact updated successfully!");
        } else {
            System.out.println("\nFailed to update contact.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Add one or more new contacts.
     */
    private void addContacts() {
        System.out.println("\n--- Add New Contact(s) ---");
        System.out.println("=======================================");

        boolean addMore = true;
        while (addMore) {
            addSingleContact();

            System.out.print("\nDo you want to add another contact? (y/n): ");
            String again = scanner.nextLine().trim().toLowerCase();
            addMore = "y".equals(again) || "yes".equals(again);
        }
    }

    /**
     * Helper to add a single contact.
     */
    private void addSingleContact() {
        Contact contact = new Contact();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("First Name (required): ");
        String firstName = scanner.nextLine().trim();
        if (firstName.isEmpty()) {
            System.out.println("First Name cannot be empty. Contact creation cancelled.");
            return;
        }
        contact.setFirstName(firstName);

        System.out.print("Middle Name (optional): ");
        String middleName = scanner.nextLine().trim();
        contact.setMiddleName(middleName.isEmpty() ? null : middleName);

        System.out.print("Last Name (required): ");
        String lastName = scanner.nextLine().trim();
        if (lastName.isEmpty()) {
            System.out.println("Last Name cannot be empty. Contact creation cancelled.");
            return;
        }
        contact.setLastName(lastName);

        System.out.print("Nickname (optional): ");
        String nickname = scanner.nextLine().trim();
        contact.setNickname(nickname.isEmpty() ? null : nickname);

        System.out.print("Phone 1 (required): ");
        String phone1 = scanner.nextLine().trim();
        if (phone1.isEmpty()) {
            System.out.println("Primary phone cannot be empty. Contact creation cancelled.");
            return;
        }
        contact.setPhonePrimary(phone1);

        System.out.print("Phone 2 (optional): ");
        String phone2 = scanner.nextLine().trim();
        contact.setPhoneSecondary(phone2.isEmpty() ? null : phone2);

        System.out.print("Email (required): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            System.out.println("Email cannot be empty. Contact creation cancelled.");
            return;
        }
        contact.setEmail(email);

        System.out.print("LinkedIn URL (optional): ");
        String linkedin = scanner.nextLine().trim();
        contact.setLinkedinUrl(linkedin.isEmpty() ? null : linkedin);

        System.out.print("Birth Date (optional, yyyy-MM-dd): ");
        String birthDateInput = scanner.nextLine().trim();
        if (!birthDateInput.isEmpty()) {
            try {
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                contact.setBirthDate(birthDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Birth date will be left empty.");
                contact.setBirthDate(null);
            }
        }

        if (contactDAO.create(contact)) {
            System.out.println("Contact created successfully! New Contact ID: " + contact.getContactId());
        } else {
            System.out.println("Failed to create contact.");
        }
    }

    /**
     * Delete one or more contacts by ID.
     */
    private void deleteContacts() {
        System.out.println("\n--- Delete Contact(s) ---");
        System.out.println("=======================================");

        System.out.print("Enter Contact ID or IDs (comma-separated) to delete: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("No IDs provided.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        String[] tokens = input.split(",");
        Map<Integer, Contact> toDelete = new HashMap<>();

        for (String token : tokens) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) continue;

            Integer id = BaseMenu.safeParseInt(trimmed);
            if (id == null) {
                System.out.println("Skipping invalid ID: " + trimmed);
                continue;
            }

            Contact contact = contactDAO.findById(id);
            if (contact == null) {
                System.out.println("Contact with ID " + id + " not found. Skipping.");
            } else {
                toDelete.put(id, contact);
            }
        }

        if (toDelete.isEmpty()) {
            System.out.println("No valid contacts found to delete.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("\nThe following contact(s) will be deleted:");
        displayContactsTable(List.copyOf(toDelete.values()));

        System.out.print("\nAre you sure you want to delete these contact(s)? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!"yes".equals(confirm) && !"y".equals(confirm)) {
            System.out.println("Deletion cancelled.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        int deletedCount = 0;
        for (Integer id : toDelete.keySet()) {
            if (contactDAO.delete(id)) {
                deletedCount++;
            } else {
                System.out.println("Failed to delete contact with ID: " + id);
            }
        }

        System.out.println("Deleted " + deletedCount + " contact(s).");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Change password for the currently logged-in Senior Developer.
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

        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        currentUser.setPasswordHash(hashedPassword);

        System.out.println("Password changed successfully!");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Helper to display contacts in a formatted table.
     */
    private void displayContactsTable(List<Contact> contacts) {
        System.out.printf("%-6s %-15s %-15s %-15s %-15s %-15s %-15s %-25s %-25s %-12s%n",
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
            String birthDate = contact.getBirthDate() != null
                    ? contact.getBirthDate().format(dateFormatter)
                    : "N/A";

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

    /**
     * Small helper to avoid "null" when printing defaults.
     */
    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}


