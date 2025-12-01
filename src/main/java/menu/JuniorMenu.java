package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;
import service.ANSI;
import service.ValidationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * </p>
 * author sarah nauman
 */
public class JuniorMenu extends BaseMenu {

    private final ContactDAO contactDAO;
    private final UserDAO userDAO;

    public JuniorMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println(ANSI.CYAN + "1. List all contacts" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "2. Search by field" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "3. Search by multiple fields" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "4. Sort contacts" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "5. Update contact" + ANSI.RESET);
        System.out.println(ANSI.CYAN + "6. Change password" + ANSI.RESET);
        System.out.println(ANSI.YELLOW + "0. Logout" + ANSI.RESET);
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
            default -> System.out.println(ANSI.RED + "\nInvalid option! Please try again." + ANSI.RESET);
        }
    }

    private void listAllContacts() {
        System.out.println(ANSI.BLUE + "\n--- List All Contacts ---" + ANSI.RESET);
        System.out.println("=======================================");

        List<Contact> contacts = contactDAO.findAll();

        if (contacts.isEmpty()) {
            System.out.println(ANSI.YELLOW + "No contacts found in the database." + ANSI.RESET);
        } else {
            displayContactsTable(contacts);
            System.out.println(ANSI.GREEN + "Total contacts: " + contacts.size() + ANSI.RESET);
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void searchByField() {
        System.out.println(ANSI.BLUE + "\n--- Search by Field ---" + ANSI.RESET);
        System.out.println("=======================================");
        System.out.println("1. Search by First Name");
        System.out.println("2. Search by Last Name");
        System.out.println("3. Search by Phone Number");
        System.out.println("4. Search by Email");
        System.out.print(ANSI.CYAN + "\nSelect search type: " + ANSI.RESET);

        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);

        if (choice == null || choice < 1 || choice > 4) {
            System.out.println(ANSI.RED + "Invalid choice. Please select 1, 2, 3, or 4." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        List<Contact> results;

        switch (choice) {
            case 1 -> {
                System.out.print(ANSI.CYAN + "Enter first name (or partial): " + ANSI.RESET);
                String firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    System.out.println(ANSI.RED + "First name cannot be empty." + ANSI.RESET);
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByFirstName(firstName);
            }
            case 2 -> {
                System.out.print(ANSI.CYAN + "Enter last name (or partial): " + ANSI.RESET);
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    System.out.println(ANSI.RED + "Last name cannot be empty." + ANSI.RESET);
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByLastName(lastName);
            }
            case 3 -> {
                System.out.print(ANSI.CYAN + "Enter phone number (or partial): " + ANSI.RESET);
                String phone = scanner.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println(ANSI.RED + "Phone number cannot be empty." + ANSI.RESET);
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByPhone(phone);
            }
            case 4 -> {
                System.out.print(ANSI.CYAN + "Enter email (or partial): " + ANSI.RESET);
                String email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println(ANSI.RED + "Email cannot be empty." + ANSI.RESET);
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    return;
                }
                Map<String, String> emailCriteria = new HashMap<>();
                emailCriteria.put("email", email);
                results = contactDAO.searchByMultipleFields(emailCriteria);
            }
            default -> results = List.of();
        }

        if (results.isEmpty()) {
            System.out.println(ANSI.YELLOW + "\nNo contacts found matching your search criteria." + ANSI.RESET);
        } else {
            System.out.println(ANSI.BLUE + "\nSearch Results:" + ANSI.RESET);
            displayContactsTable(results);
            System.out.println(ANSI.GREEN + "Found " + results.size() + " contact(s)." + ANSI.RESET);
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void searchByMultipleFields() {
        System.out.println(ANSI.BLUE + "\n--- Search by Multiple Fields ---" + ANSI.RESET);
        System.out.println("=======================================");
        System.out.println("Enter search criteria (press Enter to skip a field):\n");

        Map<String, String> criteria = new HashMap<>();

        System.out.print(ANSI.CYAN + "First Name: " + ANSI.RESET);
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) criteria.put("first_name", firstName);

        System.out.print(ANSI.CYAN + "Last Name: " + ANSI.RESET);
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) criteria.put("last_name", lastName);

        System.out.print(ANSI.CYAN + "Phone Number: " + ANSI.RESET);
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) criteria.put("phone", phone);

        System.out.print(ANSI.CYAN + "Email (partial): " + ANSI.RESET);
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) criteria.put("email", email);

        System.out.print(ANSI.CYAN + "Birth Month (1-12): " + ANSI.RESET);
        String birthMonth = scanner.nextLine().trim();
        if (!birthMonth.isEmpty()) {
            Integer month = BaseMenu.safeParseInt(birthMonth);
            if (month != null && month >= 1 && month <= 12) {
                criteria.put("birth_month", month.toString());
            } else {
                System.out.println(ANSI.YELLOW + "Invalid month. Skipping birth month filter." + ANSI.RESET);
            }
        }

        if (criteria.isEmpty()) {
            System.out.println(ANSI.RED + "\nNo search criteria provided. Please enter at least one field." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        List<Contact> results = contactDAO.searchByMultipleFields(criteria);

        if (results.isEmpty()) {
            System.out.println(ANSI.YELLOW + "\nNo contacts found matching all your search criteria." + ANSI.RESET);
        } else {
            System.out.println(ANSI.BLUE + "\nSearch Results:" + ANSI.RESET);
            displayContactsTable(results);
            System.out.println(ANSI.GREEN + "Found " + results.size() + " contact(s) matching all criteria." + ANSI.RESET);
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void sortContacts() {
        System.out.println(ANSI.BLUE + "\n--- Sort Contacts ---" + ANSI.RESET);
        System.out.println("=======================================");
        System.out.println("Select field to sort by:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Email");
        System.out.println("4. Birth Date");
        System.out.println("5. Phone Primary");
        System.out.print(ANSI.CYAN + "\nEnter your choice: " + ANSI.RESET);

        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);

        if (choice == null || choice < 1 || choice > 5) {
            System.out.println(ANSI.RED + "Invalid choice." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        String field = switch (choice) {
            case 1 -> "first_name";
            case 2 -> "last_name";
            case 3 -> "email";
            case 4 -> "birth_date";
            case 5 -> "phone_primary";
            default -> "contact_id";
        };

        System.out.println("\nSort order:");
        if ("birth_date".equals(field)) {
            System.out.println("1. Ascending (oldest to youngest)");
            System.out.println("2. Descending (youngest to oldest)");
        } else {
            System.out.println("1. Ascending (A-Z, 1-9)");
            System.out.println("2. Descending (Z-A, 9-1)");
        }
        System.out.print(ANSI.CYAN + "Enter your choice: " + ANSI.RESET);

        String orderInput = scanner.nextLine().trim();
        Integer orderChoice = BaseMenu.safeParseInt(orderInput);

        boolean ascending = orderChoice == null || orderChoice != 2;

        List<Contact> contacts = contactDAO.findAllSorted(field, ascending);

        if (contacts.isEmpty()) {
            System.out.println(ANSI.YELLOW + "No contacts found." + ANSI.RESET);
        } else {
            System.out.println(ANSI.BLUE + "\nSorted Contacts:" + ANSI.RESET);
            displayContactsTable(contacts);
            System.out.println(ANSI.GREEN + "Total contacts: " + contacts.size() + ANSI.RESET);
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void updateContact() {
        System.out.println(ANSI.BLUE + "\n--- Update Contact ---" + ANSI.RESET);
        System.out.println("=======================================");

        System.out.print(ANSI.CYAN + "Enter Contact ID to update: " + ANSI.RESET);
        String idInput = scanner.nextLine().trim();
        Integer contactId = BaseMenu.safeParseInt(idInput);

        if (contactId == null) {
            System.out.println(ANSI.RED + "Invalid contact ID. Please enter a valid number." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        Contact contact = contactDAO.findById(contactId);
        if (contact == null) {
            System.out.println(ANSI.RED + "Contact with ID " + contactId + " not found." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nCurrent Contact Information:");
        displayContactsTable(List.of(contact));
        System.out.println("\nEnter new values (press Enter to keep current value):");

        // First name
        while (true) {
            System.out.print(ANSI.CYAN + "First Name [" + nullToEmpty(contact.getFirstName()) + "] (letters only): " + ANSI.RESET);
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) break;
            if (!ValidationUtils.isValidName(firstName)) {
                System.out.println(ANSI.RED + "Invalid first name. Use letters only." + ANSI.RESET);
                continue;
            }
            contact.setFirstName(firstName);
            break;
        }

        // Middle name
        while (true) {
            System.out.print(ANSI.CYAN + "Middle Name [" + nullToEmpty(contact.getMiddleName()) + "] (optional, letters only or Enter to keep): " + ANSI.RESET);
            String middleName = scanner.nextLine().trim();
            if (middleName.isEmpty()) break;
            if (!ValidationUtils.isValidName(middleName)) {
                System.out.println(ANSI.RED + "Invalid middle name." + ANSI.RESET);
                continue;
            }
            contact.setMiddleName(middleName);
            break;
        }

        // Last name
        while (true) {
            System.out.print(ANSI.CYAN + "Last Name [" + nullToEmpty(contact.getLastName()) + "] (letters only): " + ANSI.RESET);
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) break;
            if (!ValidationUtils.isValidName(lastName)) {
                System.out.println(ANSI.RED + "Invalid last name." + ANSI.RESET);
                continue;
            }
            contact.setLastName(lastName);
            break;
        }

        // Nickname
        while (true) {
            System.out.print(ANSI.CYAN + "Nickname [" + nullToEmpty(contact.getNickname()) + "] (optional, letters/digits/-/_ or Enter to keep): " + ANSI.RESET);
            String nickname = scanner.nextLine().trim();
            if (nickname.isEmpty()) break;
            if (!ValidationUtils.isValidNickname(nickname)) {
                System.out.println(ANSI.RED + "Invalid nickname. Allowed: letters, digits, '-', '_'." + ANSI.RESET);
                continue;
            }
            contact.setNickname(nickname);
            break;
        }

        // Phone 1
        while (true) {
            System.out.print(ANSI.CYAN + "Phone 1 [" + nullToEmpty(contact.getPhonePrimary()) + "] (digits only, min 10): " + ANSI.RESET);
            String phone1 = scanner.nextLine().trim();
            if (phone1.isEmpty()) break;
            if (!ValidationUtils.isValidPhone(phone1)) {
                System.out.println(ANSI.RED + "Invalid phone number. Use digits only, min 10 digits." + ANSI.RESET);
                continue;
            }
            contact.setPhonePrimary(phone1);
            break;
        }

        // Phone 2
        while (true) {
            System.out.print(ANSI.CYAN + "Phone 2 [" + nullToEmpty(contact.getPhoneSecondary()) + "] (optional, digits only, min 10 or Enter to keep): " + ANSI.RESET);
            String phone2 = scanner.nextLine().trim();
            if (phone2.isEmpty()) break;
            if (!ValidationUtils.isValidPhone(phone2)) {
                System.out.println(ANSI.RED + "Invalid phone number. Use digits only, min 10 digits." + ANSI.RESET);
                continue;
            }
            contact.setPhoneSecondary(phone2);
            break;
        }

        // Email
        while (true) {
            System.out.print(ANSI.CYAN + "Email [" + nullToEmpty(contact.getEmail()) + "]: " + ANSI.RESET);
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) break;
            if (!ValidationUtils.isValidEmail(email)) {
                System.out.println(ANSI.RED + "Invalid email. Must contain '@' and domain." + ANSI.RESET);
                continue;
            }
            contact.setEmail(email);
            break;
        }

        System.out.print(ANSI.CYAN + "LinkedIn URL [" + nullToEmpty(contact.getLinkedinUrl()) + "]: " + ANSI.RESET);
        String linkedin = scanner.nextLine().trim();
        if (!linkedin.isEmpty()) contact.setLinkedinUrl(linkedin);

        String currentBirthDate = contact.getBirthDate() != null ? contact.getBirthDate().format(dateFormatter) : "N/A";

        while (true) {
            System.out.print(ANSI.CYAN + "Birth Date (" + currentBirthDate + ") [yyyy-MM-dd or Enter to keep]: " + ANSI.RESET);
            String birthDateInput = scanner.nextLine().trim();
            if (birthDateInput.isEmpty()) break;
            if (!birthDateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(ANSI.RED + "Invalid format. Use yyyy-MM-dd." + ANSI.RESET);
                continue;
            }
            try {
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                contact.setBirthDate(birthDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(ANSI.RED + "Invalid date value." + ANSI.RESET);
            }
        }

        if (contactDAO.update(contact)) {
            System.out.println(ANSI.GREEN + "\nContact updated successfully!" + ANSI.RESET);
        } else {
            System.out.println(ANSI.RED + "\nFailed to update contact." + ANSI.RESET);
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private void changePassword() {
        System.out.println(ANSI.BLUE + "\n--- Change Password ---" + ANSI.RESET);
        System.out.println("=======================================");

        System.out.print(ANSI.CYAN + "Enter current password: " + ANSI.RESET);
        String currentPassword = scanner.nextLine().trim();

        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println(ANSI.RED + "Error: Could not retrieve user information." + ANSI.RESET);
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
            System.out.println(ANSI.RED + "Current password is incorrect." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.print(ANSI.CYAN + "Enter new password: " + ANSI.RESET);
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ANSI.RED + "Password cannot be empty." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.print(ANSI.CYAN + "Confirm new password: " + ANSI.RESET);
        String confirmPassword = scanner.nextLine().trim();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ANSI.RED + "Passwords do not match. Password not changed." + ANSI.RESET);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        currentUser.setPasswordHash(hashedPassword);

        System.out.println(ANSI.GREEN + "Password changed successfully!" + ANSI.RESET);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

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

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
