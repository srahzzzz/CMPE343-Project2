package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;
import util.ColorUtils;

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
 * @author sarah nauman
 */
public class TesterMenu extends BaseMenu {

    private final ContactDAO contactDAO;

    /**
     * Constructor for TesterMenu.
     *
     * @param user the logged-in tester user
     */
    public TesterMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println(ColorUtils.testerMenuOption("1. List all contacts"));
        System.out.println(ColorUtils.testerMenuOption("2. Search by field"));
        System.out.println(ColorUtils.testerMenuOption("3. Search by multiple fields"));
        System.out.println(ColorUtils.testerMenuOption("4. Sort contacts by selected field"));
        System.out.println(ColorUtils.testerMenuOption("5. Change password"));
        System.out.println(ColorUtils.testerMenuOption("0. Logout"));
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
                System.out.println("\n" + ColorUtils.error("Invalid option! Please try again."));
        }
    }

    /**
     * Lists all contacts in the database.
     * Displays all contact records in a formatted table.
     */
    private void listAllContacts() {
        System.out.println("\n" + ColorUtils.header("--- List All Contacts ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        List<Contact> contacts = contactDAO.findAll();
        
        if (contacts.isEmpty()) {
            System.out.println(ColorUtils.warning("No contacts found in the database."));
        } else {
            displayContactsTable(contacts);
            System.out.println(ColorUtils.info("Total contacts: " + contacts.size()));
        }
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Searches for contacts by a single field.
     * Allows searching by first name, last name, or phone number.
     */
    private void searchByField() {
        System.out.println("\n" + ColorUtils.header("--- Search by Field ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.testerMenuOption("1. Search by First Name"));
        System.out.println(ColorUtils.testerMenuOption("2. Search by Last Name"));
        System.out.println(ColorUtils.testerMenuOption("3. Search by Phone Number"));
        System.out.println(ColorUtils.testerMenuOption("4. Search by Email"));
        System.out.print("\n" + ColorUtils.testerPrompt("Select search type: "));
        
        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);
        
        if (choice == null || choice < 1 || choice > 4) {
            System.out.println(ColorUtils.error("Invalid choice. Please select 1, 2, 3, or 4."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        List<Contact> results;
        
        switch (choice) {
            case 1:
                System.out.print(ColorUtils.testerPrompt("Enter first name (or partial): "));
                String firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    System.out.println(ColorUtils.error("First name cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByFirstName(firstName);
                break;
                
            case 2:
                System.out.print(ColorUtils.testerPrompt("Enter last name (or partial): "));
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    System.out.println(ColorUtils.error("Last name cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByLastName(lastName);
                break;
                
            case 3:
                System.out.print(ColorUtils.testerPrompt("Enter phone number (or partial): "));
                String phone = scanner.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println(ColorUtils.error("Phone number cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByPhone(phone);
                break;

            case 4:
                System.out.print(ColorUtils.testerPrompt("Enter email (or partial): "));
                String email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println(ColorUtils.error("Email cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                // Re-use multi-field search for email-only searches.
                Map<String, String> singleEmailCriteria = new HashMap<>();
                singleEmailCriteria.put("email", email);
                results = contactDAO.searchByMultipleFields(singleEmailCriteria);
                break;
                
            default:
                results = List.of();
        }
        
        if (results.isEmpty()) {
            System.out.println("\n" + ColorUtils.warning("No contacts found matching your search criteria."));
        } else {
            System.out.println("\n" + ColorUtils.header("Search Results:"));
            displayContactsTable(results);
            System.out.println(ColorUtils.success("Found " + results.size() + " contact(s)."));
        }
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Searches for contacts using multiple field criteria.
     * Allows combining multiple search conditions (e.g., name + birth month, phone + email).
     */
    private void searchByMultipleFields() {
        System.out.println("\n" + ColorUtils.header("--- Search by Multiple Fields ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.info("Enter search criteria (press Enter to skip a field):\n"));
        
        Map<String, String> criteria = new HashMap<>();
        
        System.out.print(ColorUtils.testerPrompt("First Name: "));
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            criteria.put("first_name", firstName);
        }
        
        System.out.print(ColorUtils.testerPrompt("Last Name: "));
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            criteria.put("last_name", lastName);
        }
        
        System.out.print(ColorUtils.testerPrompt("Phone Number: "));
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            criteria.put("phone", phone);
        }
        
        System.out.print(ColorUtils.testerPrompt("Email (partial): "));
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            criteria.put("email", email);
        }
        
        System.out.print(ColorUtils.testerPrompt("Birth Month (1-12): "));
        String birthMonth = scanner.nextLine().trim();
        if (!birthMonth.isEmpty()) {
            Integer month = BaseMenu.safeParseInt(birthMonth);
            if (month != null && month >= 1 && month <= 12) {
                criteria.put("birth_month", month.toString());
            } else {
                System.out.println(ColorUtils.warning("Invalid month. Skipping birth month filter."));
            }
        }
        
    

        if (criteria.isEmpty()) {
            System.out.println("\n" + ColorUtils.error("No search criteria provided. Please enter at least one field."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        List<Contact> results = contactDAO.searchByMultipleFields(criteria);
        
        if (results.isEmpty()) {
            System.out.println("\n" + ColorUtils.warning("No contacts found matching all your search criteria."));
        } else {
            System.out.println("\n" + ColorUtils.header("Search Results:"));
            displayContactsTable(results);
            System.out.println(ColorUtils.success("Found " + results.size() + " contact(s) matching all criteria."));
        }
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Sorts contacts by a selected field in ascending or descending order.
     * Allows sorting by any contact field (name, email, birth date, etc.).
     */
    private void sortContacts() {
        System.out.println("\n" + ColorUtils.header("--- Sort Contacts ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.info("Select field to sort by:"));
        System.out.println(ColorUtils.testerMenuOption("1. First Name"));
        System.out.println(ColorUtils.testerMenuOption("2. Last Name"));
        System.out.println(ColorUtils.testerMenuOption("3. Email"));
        System.out.println(ColorUtils.testerMenuOption("4. Birth Date (oldest / youngest)"));
        System.out.println(ColorUtils.testerMenuOption("5. Phone Primary"));
        System.out.print("\n" + ColorUtils.testerPrompt("Enter your choice: "));
        
        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);
        
        if (choice == null || choice < 1 || choice > 5) {
            System.out.println(ColorUtils.error("Invalid choice."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
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
        
        System.out.println("\n" + ColorUtils.info("Sort order:"));
        if ("birth_date".equals(field)) {
            System.out.println(ColorUtils.testerMenuOption("1. Ascending (oldest to youngest)"));
            System.out.println(ColorUtils.testerMenuOption("2. Descending (youngest to oldest)"));
        } else {
            System.out.println(ColorUtils.testerMenuOption("1. Ascending (A-Z, 1-9)"));
            System.out.println(ColorUtils.testerMenuOption("2. Descending (Z-A, 9-1)"));
        }
        System.out.print(ColorUtils.testerPrompt("Enter your choice: "));
        
        String orderInput = scanner.nextLine().trim();
        Integer orderChoice = BaseMenu.safeParseInt(orderInput);
        
        boolean ascending = true;
        if (orderChoice != null && orderChoice == 2) {
            ascending = false;
        }
        
        List<Contact> contacts = contactDAO.findAllSorted(field, ascending);
        
        if (contacts.isEmpty()) {
            System.out.println(ColorUtils.warning("No contacts found."));
        } else {
            System.out.println("\n" + ColorUtils.header("Sorted Contacts:"));
            displayContactsTable(contacts);
            System.out.println(ColorUtils.info("Total contacts: " + contacts.size()));
        }
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Changes the password for the currently logged-in user.
     * Verifies current password before allowing change.
     */
    private void changePassword() {
        System.out.println("\n" + ColorUtils.header("--- Change Password ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        UserDAO userDAO = new UserDAO();
        
        System.out.print(ColorUtils.testerPrompt("Enter current password: "));
        String currentPassword = scanner.nextLine().trim();
        
        // Verify current password
        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println(ColorUtils.error("Error: Could not retrieve user information."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
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
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Get new password
        System.out.print(ColorUtils.testerPrompt("Enter new password: "));
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ColorUtils.error("Password cannot be empty."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        System.out.print(ColorUtils.testerPrompt("Confirm new password: "));
        String confirmPassword = scanner.nextLine().trim();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ColorUtils.error("Passwords do not match. Password not changed."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Update password
        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        System.out.println(ColorUtils.success("Password changed successfully!"));
        // Update current user object
        currentUser.setPasswordHash(hashedPassword);
        
        System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Helper method to display contacts in a formatted table.
     * Shows all contact information including middle name, nickname, secondary phone, and LinkedIn URL.
     *
     * @param contacts the list of contacts to display
     */
    private void displayContactsTable(List<Contact> contacts) {
        // Display header with all fields
        System.out.printf(ColorUtils.header("%-6s %-15s %-15s %-15s %-15s %-15s %-15s %-25s %-25s %-12s%n"),
            "ID", "First Name", "Middle Name", "Last Name", "Nickname", 
            "Phone 1", "Phone 2", "Email", "LinkedIn", "Birth Date");
        System.out.println(ColorUtils.colorize("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", ColorUtils.CYAN));
        
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
            
            // Truncate long strings for display to fit column widths
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
        
        System.out.println(ColorUtils.colorize("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", ColorUtils.CYAN));
    }
}

